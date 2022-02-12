package com.example.springaccountmicroservicepr.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {

	private String accessToken;
	private String refreshToken;
}
