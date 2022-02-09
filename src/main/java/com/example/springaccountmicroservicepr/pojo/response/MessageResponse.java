package com.example.springaccountmicroservicepr.pojo.response;

import com.example.springaccountmicroservicepr.pojo.vo.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
	private ProgressStatus progressStatus;
	private String message;
}
