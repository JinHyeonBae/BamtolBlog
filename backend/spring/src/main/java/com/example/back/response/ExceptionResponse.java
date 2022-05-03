package com.example.back.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="에러 처리 클래스")
public class ExceptionResponse {


	@ApiModelProperty(value="에러 상태코드")
	private Integer status;

	@ApiModelProperty(value="에러 설명")
	private String messages;

	@ApiModelProperty(value="에러별 커스텀 코드", 
					  notes = "에러를 종류에 따라 나누기 위해 만든 코드. C -> Common, M -> Member, A -> Admin")
	private String code;

	public ExceptionResponse(String messages, String code, Integer status) {
		this.status = status;
		this.messages = messages;
		this.code = code;

	}

	public String getMessages() {
		return messages == null ? null : this.messages;
	}

	public final void setMessages(String messages) {

		if (messages == null) {
			this.messages = null;
		} else {
			this.messages = messages;
		}
	}

	public ExceptionResponse(ErrorCode errorCode) {
		this.messages = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	  }
	
	  public static ExceptionResponse of(ErrorCode errorCode) {
		return new ExceptionResponse(errorCode);
	  }

}
