package com.example.springaccountmicroservicepr.config;

import com.example.springaccountmicroservicepr.pojo.dao.RolesType;
import com.example.springaccountmicroservicepr.pojo.dao.User;
import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import com.example.springaccountmicroservicepr.services.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@TestConfiguration
public class SpringSecurityWebAuthTestConfig {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Bean
	@Primary
	public void userDetailsService() {
		log.info("SpringSecurityWebAuthTestConfig start");
		// Basic User
		User basicUser = new User("test", "Test@gmail.com", encoder.encode("test"));
		List<RolesType> rolesTypes = new ArrayList<>();
		rolesTypes.add(new RolesType(1, ERole.ROLE_USER));
		basicUser.setRolesTypes(rolesTypes);
		userRepository.save(basicUser);
		log.info("SpringSecurityWebAuthTestConfig end");
	}
}

