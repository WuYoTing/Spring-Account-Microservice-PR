package com.example.springaccountmicroservicepr.controller;

import com.example.springaccountmicroservicepr.pojo.dto.UserDetailsImpl;
import com.example.springaccountmicroservicepr.pojo.request.LoginRequest;
import com.example.springaccountmicroservicepr.pojo.request.SignupRequest;
import com.example.springaccountmicroservicepr.pojo.request.TokenRefreshRequest;
import com.example.springaccountmicroservicepr.pojo.response.JwtResponse;
import com.example.springaccountmicroservicepr.pojo.response.MessageResponse;
import com.example.springaccountmicroservicepr.pojo.vo.ProgressStatus;
import com.example.springaccountmicroservicepr.services.AuthenticateService;
import com.example.springaccountmicroservicepr.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

	private JwtUtils jwtUtils;
	private AuthenticateService authenticateService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> authenticateUser(
		@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticateService.getUserAuthentication(
			loginRequest.getUsername(), loginRequest.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
			.collect(Collectors.toList());
		return new ResponseEntity<>(
			new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(
		@Valid @RequestBody SignupRequest signUpRequest) {
		authenticateService.signup(signUpRequest.getUsername(), signUpRequest.getEmail(),
			signUpRequest.getRole(), signUpRequest.getPassword());
		return new ResponseEntity<>(
			new MessageResponse(ProgressStatus.Success, "User registered successfully!"),
			HttpStatus.OK);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		return authenticateService.findByToken(requestRefreshToken)
			.map(authenticateService::verifyExpiration)
			.map(RefreshToken::getUser)
			.map(user -> {
				String token = jwtUtils.generateTokenFromUsername(user.getUsername());
				return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
			})
			.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
				"Refresh token is not in database!"));
	}
}

