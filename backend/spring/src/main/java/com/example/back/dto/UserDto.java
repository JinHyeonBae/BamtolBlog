package com.example.back.dto;

import com.example.back.model.user.Users;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
//wrapping된 순수 데이터 객체
@ApiModel(value = "회원 정보", description = "아이디, 비밀번호, 이메일, 가입날짜를 가진 Domain")

public class UserDto {
    
    private int id;
    private String nickname;

    //빌더 패턴
    public Users toEntity() {
        return Users.builder()
                .nickname(this.nickname)
                .build();
    }

}
