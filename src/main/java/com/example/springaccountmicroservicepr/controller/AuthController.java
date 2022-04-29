package com.example.springaccountmicroservicepr.controller;

import com.example.springaccountmicroservicepr.exception.TokenRefreshException;
import com.example.springaccountmicroservicepr.pojo.dao.PasswordResetToken;
import com.example.springaccountmicroservicepr.pojo.dao.RefreshToken;
import com.example.springaccountmicroservicepr.pojo.dto.UserDetailsImpl;
import com.example.springaccountmicroservicepr.pojo.request.LoginRequest;
import com.example.springaccountmicroservicepr.pojo.request.SignupRequest;
import com.example.springaccountmicroservicepr.pojo.request.TokenRefreshRequest;
import com.example.springaccountmicroservicepr.pojo.response.JwtResponse;
import com.example.springaccountmicroservicepr.pojo.response.MessageResponse;
import com.example.springaccountmicroservicepr.pojo.response.TokenRefreshResponse;
import com.example.springaccountmicroservicepr.pojo.vo.ProgressStatus;
import com.example.springaccountmicroservicepr.services.AuthenticateService;
import com.example.springaccountmicroservicepr.services.MailService;
import com.example.springaccountmicroservicepr.services.TokenService;
import com.example.springaccountmicroservicepr.share.RegexPatterns;
import com.example.springaccountmicroservicepr.util.JwtUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

	private JwtUtil jwtUtil;
	private AuthenticateService authenticateService;
	private TokenService tokenService;

	private MailService mailService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> authenticateUser(
		@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticateService.getUserAuthentication(
			loginRequest.getUsername(), loginRequest.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtil.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
			.collect(Collectors.toList());
		RefreshToken refreshToken = tokenService.createRefreshToken(userDetails.getId());
		return new ResponseEntity<>(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
			userDetails.getUsername(), userDetails.getEmail(), roles), HttpStatus.OK);
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
	public ResponseEntity<TokenRefreshResponse> refreshtoken(
		@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		return tokenService.findByToken(requestRefreshToken)
			.map(tokenService::verifyExpiration)
			.map(RefreshToken::getUser)
			.map(user -> {
				String token = jwtUtil.generateTokenFromUsername(user.getUsername());
				return new ResponseEntity<>(new TokenRefreshResponse(token, requestRefreshToken),
					HttpStatus.OK);
			})
			.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
				"Refresh token is not in database!"));
	}

	@GetMapping("/password/forget")
	public ResponseEntity<MessageResponse> passwordForget(
		@RequestParam @NotBlank(message = "email is required")
		@Size(max = 50, message = "invalid email size")
		@Pattern(regexp = RegexPatterns.Email_Pattern, message = "invalid email")
		String email
	) {
		PasswordResetToken passwordResetToken = tokenService.passwordForget(email);
		mailService.sendResetTokenEmail(email, passwordResetToken);
		return new ResponseEntity<>(
			new MessageResponse(ProgressStatus.Success, "User registered successfully!"),
			HttpStatus.OK);
	}

	// Todo reset password with password/forget uuid,old password,new password
}

