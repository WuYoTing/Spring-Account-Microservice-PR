package com.example.springaccountmicroservicepr.config;

import com.example.springaccountmicroservicepr.pojo.dao.RolesType;
import com.example.springaccountmicroservicepr.pojo.dao.User;
import com.example.springaccountmicroservicepr.pojo.dto.UserDetailsImpl;
import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@TestConfiguration
public class SpringSecurityWebAuthTestConfig {

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {

		// Basic User
		User basicUser = new User("test", "Test@gmail.com", "test");
		List<RolesType> rolesTypes = new ArrayList<>();
		rolesTypes.add(new RolesType(1, ERole.ROLE_USER));
		return new InMemoryUserDetailsManager(UserDetailsImpl.build(basicUser));
	}
}

