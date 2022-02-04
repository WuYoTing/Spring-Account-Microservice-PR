package com.example.springaccountmicroservicepr.services.impl;

import com.example.springaccountmicroservicepr.pojo.dao.User;
import com.example.springaccountmicroservicepr.pojo.dto.UserDetailsImpl;
import com.example.springaccountmicroservicepr.repository.UserRepository;
import com.example.springaccountmicroservicepr.services.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(
			() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
}
