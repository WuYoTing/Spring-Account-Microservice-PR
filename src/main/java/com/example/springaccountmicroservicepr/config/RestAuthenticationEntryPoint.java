package com.example.springaccountmicroservicepr.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springaccountmicroservicepr.pojo.response.MessageResponse;
import com.example.springaccountmicroservicepr.pojo.vo.ProgressStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		log.error("Unauthorized error: {}", authException.getMessage());
		String messageResp = objectMapper.writeValueAsString(
			new MessageResponse(ProgressStatus.Error, authException.getMessage()));
		response.setContentType("application/json");
		response.setStatus(response.SC_FORBIDDEN);
		response.getWriter().write(messageResp);
	}
}
