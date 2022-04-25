package com.example.back.response;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// 동일한 건 통일성 있도록 묶는다.
public class ResponseDto {

    HttpStatus status;
    String message;
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    @ApiModel(value="포스트 생성 응답 DTO")
    public static class CreateResponseDto{

        @ApiModelProperty(value="요청에 따른 상태코드")
        HttpStatus status;

        @ApiModelProperty(value="상태코드 설명")
        String message;

        @ApiModelProperty(value="포스트 고유 넘버")
        int postId;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value="포스트 읽기 응답 DTO")
    public static class ReadResponseDto{
        
        @ApiModelProperty(value="요청에 따른 상태코드")
        HttpStatus status;
        
        @ApiModelProperty(value="상태코드 설명")
        String message;
        
        String contents;
        String titles;

        public void readErrorDto(HttpStatus status, String message){
            this.status = status;
            this.message = message;
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value="로그인 응답 DTO")
    public static class LoginResponseDto{

        @ApiModelProperty(value="요청에 따른 상태코드")
        HttpStatus status;

        @ApiModelProperty(value="상태코드 설명")
        String message;

        @ApiModelProperty(value="사용자 고유 넘버")
        int userId;

        @ApiModelProperty(value="액세스 토큰")
        String accesstoken;

    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value="회원가입 응답 DTO")
    public static class SignUpResponseDto{

        @ApiModelProperty(value="요청에 따른 상태코드")
        HttpStatus status;

        @ApiModelProperty(value="상태코드 설명")
        String message;
    
    }

}
