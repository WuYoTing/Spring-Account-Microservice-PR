package com.example.springaccountmicroservicepr.config;

import com.example.springaccountmicroservicepr.pojo.response.MessageResponse;
import com.example.springaccountmicroservicepr.pojo.vo.ProgressStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<MessageResponse> handleAccessDeniedException(Exception ex) {
		log.error("Access Denied : {}", ex.getMessage());
		MessageResponse messageResp = new MessageResponse(ProgressStatus.Fail, ex.getMessage());
		return new ResponseEntity<>(messageResp, HttpStatus.FORBIDDEN);
	}
}
