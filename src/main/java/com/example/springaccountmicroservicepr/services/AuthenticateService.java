package com.example.springaccountmicroservicepr.services;


import com.example.springaccountmicroservicepr.pojo.dao.Role;
import com.example.springaccountmicroservicepr.pojo.dao.User;
import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import com.example.springaccountmicroservicepr.repository.RoleRepository;
import com.example.springaccountmicroservicepr.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticateService {
	UserRepository userRepository;
	RoleRepository roleRepository;
	AuthenticationManager authenticationManager;
	PasswordEncoder encoder;

	public Authentication getUserAuthentication(String username, String password) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	public void signup(String username, String email, Set<String> strRoles, String password) {
		if (userRepository.existsByUsername(username)) {
			log.error("Error: Username is already taken! ");
			throw new RuntimeException("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(email)) {
			log.error("Error: Email is already in use!");
			throw new RuntimeException("Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(username, email, encoder.encode(password));
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					case "mod":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
	}


}
