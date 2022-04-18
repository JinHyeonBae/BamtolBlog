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
public class ResponseDto {

    HttpStatus status;
    String message;
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponseDto{
        HttpStatus status;
        String message;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadResponseDto{
        HttpStatus status;
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
    public static class LoginResponseDto{
        HttpStatus status;
        String message;

        String accessToken;
    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpResponseDto{
        HttpStatus status;
        String message;

    
    }
    

}
