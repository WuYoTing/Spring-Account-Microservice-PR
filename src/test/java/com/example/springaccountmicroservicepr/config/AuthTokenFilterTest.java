package com.example.springaccountmicroservicepr.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.springaccountmicroservicepr.services.impl.UserDetailsServiceImpl;
import com.example.springaccountmicroservicepr.util.JwtUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AuthTokenFilterTest")
@ExtendWith(MockitoExtension.class)
class AuthTokenFilterTest {

	private static final String token = "fff5ea1e-8f2e-11ec-b909-0242ac120002";
	private static final String testUri = "/api/test/user";

	@Mock
	JwtUtils jwtUtils;

	@Mock
	private UserDetailsServiceImpl userDetailsService;

	@InjectMocks
	private AuthTokenFilter authTokenFilter;

	@Nested
	@DisplayName("test doFilterInternal")
	class test_doFilterInternal {

		@Test
		@DisplayName("test doFilterInternal should filter")
		public void test_doFilterInternal_should_OK() throws ServletException, IOException {
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.addHeader("TOKEN", token);
			request.setRequestURI(testUri);
			MockHttpServletResponse response = new MockHttpServletResponse();
			MockFilterChain filterChain = new MockFilterChain();
			authTokenFilter.doFilterInternal(request, response, filterChain);
			assertEquals(response.getStatus(), HttpStatus.OK.value());
		}
	}
}
