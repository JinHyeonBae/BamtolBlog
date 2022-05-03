package com.example.back.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.response.ResponseDto.LoginResponseDto;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.response.ResponseDto.TokenDto;
import com.example.back.security.JwtProvider;
import com.example.back.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtTokenProvider;


    int cookieExpiration = 60*60*24; //1일

    // value
    @ApiOperation(value="회원가입", notes = "사용자 회원가입")
    @ApiResponses({
        @ApiResponse(code = 201, message = "회원가입 성공"),
        @ApiResponse(code = 40901, message = "이미 존재하는 이메일"),
        @ApiResponse(code = 40902, message = "이미 존재하는 닉네임"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/auth/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestHeader HttpHeaders headers, @RequestBody SignUpDto signUpDto){
        
        SignUpResponseDto signUpResult = auth.SignUp(signUpDto);
        HttpStatus signStatus = signUpResult.getStatus();

        return ResponseEntity.status(signStatus).body(signUpResult);
    }


    // 쿠키에 토큰을 넣어서 보냄, 파싱
    @ApiOperation(value="로그인", notes = "사용자 회원가입")
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그인 성공"),
        @ApiResponse(code = 400, message = "유효하지 않은 토큰"),
        @ApiResponse(code = 40101, message = "존재하지 않는 아이디"),
        @ApiResponse(code = 40102, message = "비밀번호 오류"), // status가 겹치는 경우에는 어떤 경우가 있는 지를 봐야한다. ex) 40401, 40402
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginData, 
                                                   HttpServletRequest request, HttpServletResponse response){     
        
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword()));
		
        SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtTokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, makeResponseCookie(jwt).toString())
                                .body(new LoginResponseDto(HttpStatus.OK, "정상적으로 로그인 되었습니다."));     
        
    }

    private ResponseCookie makeResponseCookie(String jwt){
        return ResponseCookie.from("access_Token", jwt)
                            .httpOnly(true)
                            .sameSite("None")
                            .secure(true)
                            .maxAge(cookieExpiration) //1일
                            .path("/")
                            .build();
    }




}
