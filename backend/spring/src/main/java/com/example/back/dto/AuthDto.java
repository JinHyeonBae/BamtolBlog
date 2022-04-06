package com.example.back.dto;

import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// 로그인, 회원가입과 같은 보안적인 시스템이 필요한 dto
public class AuthDto{

    @Getter
    @Setter
    @NoArgsConstructor
    // 이메일과 패스워드가 맞는 지를 확인하는 용도
    public static class LoginDto{
        String email;
        String password;

        public LoginDto(String email, String password){
            this.email = email;
            this.password = password;
        }

        public UserInformation toEntity(){
            return UserInformation.builder()
                .email(this.email)
                .password(this.password)
                .build();
        }
    }
    
    @Getter
    @Setter
    public static class SignUpDto{
        String email;
        String password;
        String name;
        String nickname;
        int userId;

        //dto -> entity
        public UserInformation toEntity() {
            return UserInformation.builder()
                    .email(this.email)
                    .password(this.password)
                    .name(this.name)
                    .nickname(this.nickname)
                    .userId(this.userId)
                    .build();
        }
    }

    public static class TokenDto{
        String token;

        public UserAuth toEntity(){
            return UserAuth.builder()
                .token(this.token)
                .build();
        }        
    }


}
