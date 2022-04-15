package com.example.back.controller;

import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.example.back.dto.AuthDto;
import com.example.back.dto.UserDto;
import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.model.user.Users;
import com.example.back.response.ResponseDto.LoginResponseDto;
import com.example.back.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;

@RestController
@Api("/auth")
public class AuthController {
    
    @Autowired
    private AuthService auth;

    @ApiOperation(value="회원가입", notes = "사용자 회원가입")
    @ApiResponses({
        @ApiResponse(code = 201, message = "회원가입 성공"),
        @ApiResponse(code = 409, message = "이미 존재하는 아이디"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/auth/signup")
    // @ResponseBody Error : 
    public HttpStatus signUp(AuthDto.SignUpDto signUpDto){
        HttpStatus status = auth.SignUp(signUpDto);
        System.out.println(status);
        return status;
    }


    // 쿠키에 토큰을 넣어서 보냄, 파싱
    @ApiOperation(value="로그인", notes = "사용자 회원가입")
    @ApiResponses({
        @ApiResponse(code = 201, message = "로그인 성공"),
        @ApiResponse(code = 400, message = "유효하지 않은 토큰"),
        @ApiResponse(code = 403, message = "비밀번호 오류"),
        @ApiResponse(code = 404, message = "존재하지 않는 아이디"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/auth/signin")
    public String login(LoginDto loginRequest){     
        
        // LoginResponseDto loginResponseDto;

        // try {
        //     loginResponseDto = auth.login(loginRequest);
        // } catch (AuthenticationException e) {
        //     System.out.println("Authentication Error:" + e.getMessage());
        //     e.printStackTrace();
        // }

        // if(token.get("accessToken") == null)
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST);
        
        // HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.add("accessToken", token.get("accessToken"));


        // return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders);
        return "hello";
    }
}
