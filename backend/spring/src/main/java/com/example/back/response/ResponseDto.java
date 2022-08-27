package com.example.back.response;

import org.springframework.http.HttpStatus;

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

    ////@ApiModel(value="포스트 생성 응답 DTO")
    public static class CreateResponseDto{

        ////@ApiModelProperty(value="요청에 따른 상태코드")
        Integer status;

        ////@ApiModelProperty(value="상태코드 설명")
        String message;

        ////@ApiModelProperty(value="포스트 고유 넘버")
        int postId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    ////@ApiModel(value="포스트 읽기 응답 DTO")
    public static class ReadResponseDto{
        
        Integer status;
        
        String message;
        
        String contents;
        String title;

        public void readErrorDto(Integer status, String message){
            this.status = status;
            this.message = message;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    ////@ApiModel(value="로그인 응답 DTO")
    public static class SignInResponseDto{
        
        // TODO: nickname 클라이언트에게 보내줘야함(링크 제작할 때 사용) 

        Integer status;
        String message;
        Auth user;

        @Setter
        @Getter
        @AllArgsConstructor
        public static class Auth{        
            String accessToken;
            String refreshToken;

            String nickname;    
            Integer userId;

        }

    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    ////@ApiModel(value="회원가입 응답 DTO")
    public static class SignUpResponseDto{

        ////@ApiModelProperty(value="요청에 따른 상태코드")
        Integer status;

        ////@ApiModelProperty(value="상태코드 설명")
        String message;
    }


    @Getter
    @Setter
    public static class UpdateResponseDto{

        Integer status;
        String message;

    }

    @Getter
    @Setter
    public static class DeleteResponseDto{

        Integer status;
        String message;

    }


    

}
