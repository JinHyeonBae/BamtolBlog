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

    //Model(value="포스트 생성 응답 DTO")
    public static class CreateResponseDto{

        //ModelProperty(value="요청에 따른 상태코드")
        HttpStatus status;

        //ModelProperty(value="상태코드 설명")
        String message;

        //ModelProperty(value="포스트 고유 넘버")
        int postId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    //Model(value="포스트 읽기 응답 DTO")
    public static class ReadResponseDto{
        
       // ModelProperty(value="요청에 따른 상태코드")
        HttpStatus status;
        
        //ModelProperty(value="상태코드 설명")
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
    //Model(value="로그인 응답 DTO")
    public static class LoginResponseDto{

        //ModelProperty(value="요청에 따른 상태코드")
        Integer status;

        //ModelProperty(value="상태코드 설명")
        String message;

        // TODO: nickname 클라이언트에게 보내줘야함(링크 제작할 때 사용) 
        //ModelProperty(value="유저")
        Auth user;

        @Setter
        @Getter
        @AllArgsConstructor
        public static class Auth{        
            //ModelProperty(value="유저 닉네임")
            String nickname;    
            
            //ModelProperty(value="유저 고유 넘버")
            Integer userId;
        }

    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    //Model(value="회원가입 응답 DTO")
    public static class SignUpResponseDto{

        //ModelProperty(value="요청에 따른 상태코드")
        Integer status;

        //ModelProperty(value="상태코드 설명")
        String message;

        //ModelProperty(value="이메일 중복 여부")
        boolean emailDuplicated;

        //ModelProperty(value="닉네임 중복 여부")
        boolean nicknameDuplicated;
        
    }

    @Getter
    @Setter
    public static class TokenDto{
        String accessToken;
        String refreshToken;
        
    }

}
