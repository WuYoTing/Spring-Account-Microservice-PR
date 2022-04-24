package com.example.springaccountmicroservicepr.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springaccountmicroservicepr.config.SpringSecurityWebAuthTestConfig;
import com.example.springaccountmicroservicepr.pojo.request.LoginRequest;
import com.example.springaccountmicroservicepr.pojo.request.SignupRequest;
import com.example.springaccountmicroservicepr.services.AuthenticateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@SpringBootTest
@Import(SpringSecurityWebAuthTestConfig.class)
@DisplayName("AuthControllerTest")
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	AuthenticateService authenticateService;

	@Nested
	@DisplayName("registerUser")
	class test_registerUser {

		@Test
		@WithAnonymousUser
		@DisplayName("sentMSG_should_be_ok")
		public void registerUser_should_be_ok() throws Exception {
			// Arrange
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

			SignupRequest signupRequest = new SignupRequest();
			signupRequest.setUsername("TestCase");
			signupRequest.setEmail("TestCase@gma.co");
			signupRequest.setPassword("TestCase");
			signupRequest.setRole(Collections.singleton("mod"));

			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signup")
				.headers(httpHeaders).content(objectMapper.writeValueAsString(signupRequest));
			// Act
			ResultActions resultActions = mockMvc.perform(requestBuilder);
			// Assert
			resultActions.andExpect(status().isOk());
		}
	}


	@Nested
	@DisplayName("login")
	class test_login {

		@Test
		@WithAnonymousUser
		@DisplayName("login_should_be_ok")
		public void login_should_be_ok() throws Exception {
			// Arrange
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setUsername("test");
			loginRequest.setPassword("test");

			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
				.headers(httpHeaders).content(objectMapper.writeValueAsString(loginRequest));
			// Act
			ResultActions resultActions = mockMvc.perform(requestBuilder);
			// Assert
			resultActions.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		}
	}
}
