package com.example.back.controller;


import javax.naming.AuthenticationException;
import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.response.ResponseDto.LoginResponseDto;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api("/auth")
public class AuthController {
    
    @Autowired
    private AuthService auth;

    @ApiOperation(value="회원가입", notes = "사용자 회원가입")
    @ApiResponses({
        @ApiResponse(code = 201, message = "회원가입 성공"),
        @ApiResponse(code = 409, message = "이미 존재하는 아이디, 혹은 닉네임"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/auth/signup")
    // @ResponseBody Error 
    public ResponseEntity<SignUpResponseDto> signUp(SignUpDto signUpDto){
        
        SignUpResponseDto signUpResult = auth.SignUp(signUpDto);
        HttpStatus signStatus = signUpResult.getStatus();
        ResponseEntity<SignUpResponseDto> responseEntity = ResponseEntity.status(signStatus).body(signUpResult);
        
        return responseEntity;
    }


    // 쿠키에 토큰을 넣어서 보냄, 파싱
    @ApiOperation(value="로그인", notes = "사용자 회원가입")
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그인 성공"),
        @ApiResponse(code = 400, message = "유효하지 않은 토큰"),
        @ApiResponse(code = 403, message = "비밀번호 오류"),
        @ApiResponse(code = 404, message = "존재하지 않는 아이디"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestHeader HttpHeaders headers, @RequestBody LoginDto loginRequest){     
        
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        
        try {
            loginResponseDto = auth.login(loginRequest);
        } catch (AuthenticationException e) {
            System.out.println("Authentication Error:" + e.getMessage());
            e.printStackTrace();
        }
        
        ResponseCookie responseCookie = ResponseCookie.from("access_Token", loginResponseDto.getAccesstoken())
                .httpOnly(true)
                .secure(true)
                .maxAge(60*60*24) // 1일
                .build();

        System.out.println(responseCookie.getName());
        
        loginResponseDto.setAccesstoken("");
        return ResponseEntity.status(loginResponseDto.getStatus()).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(loginResponseDto);
    }

    
}
