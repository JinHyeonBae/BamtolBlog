package com.example.back.dto;

import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// 로그인, 회원가입과 같은 보안적인 시스템이 필요한 dto
public class AuthDto{

    @Getter
    @Setter
    @NoArgsConstructor
    // 이메일과 패스워드가 맞는 지를 확인하는 용도

    @ApiModel
    public static class LoginDto{

        @ApiModelProperty(value="이메일")
        String email;
        @ApiModelProperty(value="비밀번호")
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
    @ApiModel
    public static class SignUpDto{

        @ApiModelProperty(value="이메일")
        String email;
        @ApiModelProperty(value="패스워드")
        String password;
        @ApiModelProperty(value="이름")
        String name;
        @ApiModelProperty(value="닉네임")
        String nickname;

        //dto -> entity
        public UserInformation toEntity() {
            return UserInformation.builder()
                    .email(this.email)
                    .password(this.password)
                    .name(this.name)
                    .nickname(this.nickname)
                    .build();
        }
    }

    public static class TokenDto{
        String token;
        int userId;

        public UserAuth toEntity(){
            return UserAuth.builder()
                .userId(this.userId)
                .token(this.token)
                .build();
        }        
    }


}
