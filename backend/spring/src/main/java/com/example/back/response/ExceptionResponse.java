package com.example.back.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
////@ApiModel(value="에러 처리 클래스")
public class ExceptionResponse {

	//@ApiModelProperty(value="에러 상태코드")
	private Integer status;

	//@ApiModelProperty(value="에러 설명")
	private String message;

	//@ApiModelProperty(value="에러별 커스텀 코드", 
					  //notes = "에러를 종류에 따라 나누기 위해 만든 코드. C -> Common, M -> Member, A -> Admin")
	private String code;

	public ExceptionResponse(String message, String code, Integer status) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message == null ? null : this.message;
	}

	public final void setMessage(String message) {
		if (message == null) {
			this.message = null;
		} else {
			this.message = message;
		}
	}

	public ExceptionResponse(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
	
	public static ExceptionResponse of(ErrorCode errorCode) {
		return new ExceptionResponse(errorCode);
	}

	// 가변인자
	public static List<ExceptionResponse> more(ErrorCode...errorCode){
		List<ExceptionResponse> exList = new ArrayList<>();
		
		for(int i=0; i<errorCode.length; i++){
			exList.add(new ExceptionResponse(errorCode[i]));
		}

		return exList;
	} 


}
