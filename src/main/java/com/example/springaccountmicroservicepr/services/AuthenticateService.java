package com.example.springaccountmicroservicepr.services;


import com.example.springaccountmicroservicepr.exception.NotFoundException;
import com.example.springaccountmicroservicepr.pojo.dao.PasswordResetToken;
import com.example.springaccountmicroservicepr.pojo.dao.RolesType;
import com.example.springaccountmicroservicepr.pojo.dao.User;
import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import com.example.springaccountmicroservicepr.services.repository.PasswordResetTokenRepository;
import com.example.springaccountmicroservicepr.services.repository.RoleRepository;
import com.example.springaccountmicroservicepr.services.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticateService {

	AuthenticationManager authenticationManager;
	PasswordEncoder encoder;
	UserRepository userRepository;
	RoleRepository roleRepository;
	PasswordResetTokenRepository passwordResetTokenRepository;

	public Authentication getUserAuthentication(String username, String password) {
		return authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password)
		);
	}

	public User signup(String username, String email, Set<String> strRoles, String password) {
		if (userRepository.existsByUsername(username)) {
			log.error("Error: Username is already taken! ");
			throw new NotFoundException("Username is already taken!");
		}

		if (userRepository.existsByEmail(email)) {
			log.error("Error: Email is already in use!");
			throw new NotFoundException("Email is already in use!");
		}

		// Create new user's account
		List<RolesType> rolesTypes = new ArrayList<>();

		if (strRoles == null) {
			RolesType userRolesType = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new NotFoundException("Role is not found."));
			rolesTypes.add(userRolesType);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						RolesType adminRolesType = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
						rolesTypes.add(adminRolesType);
						break;
					case "mod":
						RolesType modRolesType = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
						rolesTypes.add(modRolesType);
						break;
					default:
						RolesType userRolesType = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
						rolesTypes.add(userRolesType);
				}
			});
		}

		User user = new User(username, email, encoder.encode(password), rolesTypes);
		return userRepository.save(user);
	}


	public Boolean passwordReset(String uuid, String newPassword) {
		PasswordResetToken passwordResetTokenOptional = passwordResetTokenRepository.findByToken(uuid)
			.orElseThrow(() -> new NotFoundException("Reset Record Not Exist!"));
		User user = passwordResetTokenOptional.getUser();
		// Todo Maybe need to check if is same as last time
		user.updatePassword(encoder.encode(newPassword));
		userRepository.save(user);
		return true;
	}
}
