package com.example.springaccountmicroservicepr.config;

import com.example.springaccountmicroservicepr.exception.NotFoundException;
import com.example.springaccountmicroservicepr.exception.TokenRefreshException;
import com.example.springaccountmicroservicepr.pojo.response.MessageResponse;
import com.example.springaccountmicroservicepr.pojo.vo.ProgressStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

	/**
	 * Handle Access Deny
	 */
	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<MessageResponse> handleAccessDeniedException(Exception ex) {
		log.error("Access Denied : {}", ex.getMessage());
		MessageResponse messageResp = new MessageResponse(ProgressStatus.Fail, ex.getMessage());
		return new ResponseEntity<>(messageResp, HttpStatus.FORBIDDEN);
	}

	/**
	 * Handle Validation Fail
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<MessageResponse> paramExceptionHandler(MethodArgumentNotValidException e) {
		BindingResult exceptions = e.getBindingResult();
		if (exceptions.hasErrors()) {
			List<ObjectError> errors = exceptions.getAllErrors();
			if (!errors.isEmpty()) {
				FieldError fieldError = (FieldError) errors.get(0);
				return new ResponseEntity<>(
					new MessageResponse(ProgressStatus.Fail, fieldError.getDefaultMessage()),
					HttpStatus.FORBIDDEN);
			}
		}
		return new ResponseEntity<>(
			new MessageResponse(ProgressStatus.Fail, "Request Parameter Error"),
			HttpStatus.FORBIDDEN);
	}

	/**
	 * Handle Token Expired
	 */
	@ExceptionHandler(value = TokenRefreshException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<MessageResponse> handleTokenRefreshException(TokenRefreshException ex,
		WebRequest request) {
		return new ResponseEntity<>(new MessageResponse(ProgressStatus.Fail, ex.getMessage()),
			HttpStatus.FORBIDDEN);
	}

	/**
	 * Handle Resourses Not Found
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ResponseEntity<MessageResponse> handleNofFound(RuntimeException e) {
		return new ResponseEntity<>(
			new MessageResponse(ProgressStatus.Error, e.getMessage()),
			HttpStatus.NOT_FOUND);
	}

}
