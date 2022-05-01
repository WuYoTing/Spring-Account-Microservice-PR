package com.example.springaccountmicroservicepr.pojo.request;

import com.example.springaccountmicroservicepr.share.RegexPatterns;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {

	@NotBlank(message = "uuid is required")
	@Pattern(regexp = RegexPatterns.UUID_Pattern, message = "invalid uuid")
	private String uuid;

	@NotBlank(message = "password is required")
	@Size(min = 6, max = 40, message = "invalid password size")
	private String newPassword;

}
