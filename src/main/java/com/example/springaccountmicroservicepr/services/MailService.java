package com.example.springaccountmicroservicepr.services;

import com.example.springaccountmicroservicepr.pojo.dao.PasswordResetToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MailService {

	public void sendResetTokenEmail(String email, PasswordResetToken passwordResetToken) {
		// Todo call mail service to sent mail
	}
}
