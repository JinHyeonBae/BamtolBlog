package com.example.back.controller;

import java.util.Map;

import com.example.back.dto.AuthDto.SignInDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.repository.UserInformationRepository;
import com.example.back.response.ResponseDto.SignInResponseDto;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.response.ResponseDto.SignInResponseDto.Auth;
import com.example.back.security.JwtProvider;
import com.example.back.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    
    private AuthService auth;

	private AuthenticationManager authenticationManager;

	private JwtProvider jwtTokenProvider;

    public AuthController(AuthService auth, AuthenticationManager authenticationManager, JwtProvider jwtTokenProvider){
        this.auth = auth;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    int cookieExpiration = 60*60*24; //1일

    @GetMapping("/api")
    public String sslTest(){
        return "Hello To DOCKER SPRING!";
    }

    @PostMapping("/api/auth/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestHeader HttpHeaders headers, @RequestBody SignUpDto signUpDto){
        
        SignUpResponseDto signUpResult = auth.SignUp(signUpDto);

        return ResponseEntity.ok().body(signUpResult);
    }


    @PostMapping("/api/auth/signIn")
    public ResponseEntity<SignInResponseDto> SignIn(@RequestHeader HttpHeaders request, @RequestBody SignInDto signInData){     
        

        //인증 주체의 정보를 담는 목적
        LOGGER.info("HEADER :" + request);
        LOGGER.info("password :" + signInData.getPassword());

        UsernamePasswordAuthenticationToken userDetailsToken = new UsernamePasswordAuthenticationToken(signInData.getEmail(), signInData.getPassword());
        Authentication authentication = authenticationManager.authenticate(userDetailsToken);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> signInResponseDto = auth.SignIn(signInData.getEmail());

    
        String nickname = (String) signInResponseDto.get("nickname");
        Integer userId   = (Integer) signInResponseDto.get("userId"); 
        LOGGER.info("userId :" + userId);

		String jwt = jwtTokenProvider.generateToken(authentication, userId);
        //nickname, userId;

        return ResponseEntity.ok().body(new SignInResponseDto(200, "정상적으로 로그인 되었습니다.", new Auth(jwt, null, nickname, userId)));     
    }

    private ResponseCookie makeResponseCookie(String jwt){
        return ResponseCookie.from("access_Token", jwt)
                            .maxAge(cookieExpiration) //1일
                            .sameSite("None")
                            .path("/")
                            .build();
    }

    private ResponseCookie makeResponseSetCookie(String jwt){
        
        return ResponseCookie.from("access_Token", jwt)
                            .httpOnly(true)
                            .maxAge(cookieExpiration) //1일
                            .sameSite("None")
                            .secure(true)
                            .path("/")
                            .build();
    }




}
