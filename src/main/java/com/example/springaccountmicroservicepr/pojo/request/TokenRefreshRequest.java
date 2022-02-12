package com.example.springaccountmicroservicepr.pojo.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class TokenRefreshRequest {

	@NotBlank
	private String refreshToken;
}
