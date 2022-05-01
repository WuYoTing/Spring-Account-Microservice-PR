package com.example.springaccountmicroservicepr.pojo.request;

import com.example.springaccountmicroservicepr.share.RegexPatterns;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordForgetRequest {

	@NotBlank(message = "email is required")
	@Size(max = 50, message = "invalid email size")
	@Pattern(regexp = RegexPatterns.Email_Pattern, message = "invalid email")
	String email;
}
