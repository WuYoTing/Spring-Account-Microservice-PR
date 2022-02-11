package com.example.springaccountmicroservicepr.pojo.request;

import lombok.Data;

@Data
public class TokenRefreshRequest {
	private String refreshToken;
}
