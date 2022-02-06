package com.example.springaccountmicroservicepr.pojo.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

}
