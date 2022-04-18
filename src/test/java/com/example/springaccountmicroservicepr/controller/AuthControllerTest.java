package com.example.springaccountmicroservicepr.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springaccountmicroservicepr.pojo.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@DisplayName("AuthControllerTest")
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Nested
	@DisplayName("sentMSG")
	class test_registerUser {

		@Test
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

			RequestBuilder requestBuilder =
				MockMvcRequestBuilders
					.post("/api/auth/signup")
					.headers(httpHeaders)
					.content(objectMapper.writeValueAsString(signupRequest));
			// Act
			ResultActions resultActions = mockMvc.perform(requestBuilder);
			// Assert
			resultActions
				.andExpect(status().isOk());
		}
	}
}
