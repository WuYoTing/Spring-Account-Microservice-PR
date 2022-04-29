package com.example.springaccountmicroservicepr.pojo.request;

import com.example.springaccountmicroservicepr.share.RegexPatterns;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {


	@NotBlank(message = "user name is required")
	@Size(min = 3, max = 20, message = "invalid user name size")
	private String username;

	// Todo Make Email Check more complex
	@NotBlank(message = "email is required")
	@Size(max = 50, message = "invalid email size")
	@Pattern(regexp = RegexPatterns.Email_Pattern, message = "invalid email")
	private String email;

	@NotEmpty(message = "role should not by empty")
	private Set<String> role;

	@NotBlank(message = "password is required")
	@Size(min = 6, max = 40, message = "invalid password size")
	private String password;

}
