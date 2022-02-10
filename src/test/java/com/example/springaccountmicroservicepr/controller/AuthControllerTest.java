package com.example.springaccountmicroservicepr.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("AuthControllerTest")
class AuthControllerTest {

	@Nested
	@DisplayName("sentMSG")
	class test_registerUser {
		@Test
		@DisplayName("sentMSG_should_send_message_and_is_ok")
		public void registerUser_should_filter_request_body() {
			// Arrange
			// Act & Assert
		}
	}
}
