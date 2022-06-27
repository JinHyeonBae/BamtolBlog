package com.example.back.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.repository.UserInformationRepository;
import com.example.back.response.ResponseDto.LoginResponseDto;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.response.ResponseDto.LoginResponseDto.Auth;
import com.example.back.security.JwtProvider;
import com.example.back.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
////@Api("/auth")
//@Tag(name = "user", description = "사용자 API")
public class AuthController {
    
    @Autowired
    private AuthService auth;

    @Autowired 
    private UserInformationRepository userInformationRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtTokenProvider;

    int cookieExpiration = 60*60*24; //1일

    @PostMapping("/auth/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestHeader HttpHeaders headers, @RequestBody SignUpDto signUpDto){
        
        SignUpResponseDto signUpResult = auth.SignUp(signUpDto);

        return ResponseEntity.ok().body(signUpResult);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginData, 
                                                   HttpServletRequest request, HttpServletResponse response){     
        

        System.out.println(loginData.getEmail());
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword()));
		
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> loginResponseDto = auth.login(loginData.getEmail());
        String nickname = (String) loginResponseDto.get("nickname");
        Integer userId   = (Integer) loginResponseDto.get("userId"); 

		String jwt = jwtTokenProvider.generateToken(authentication, userId);
        //nickname, userId;
        
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, makeResponseCookie(jwt).toString())
                                .body(new LoginResponseDto(200, "정상적으로 로그인 되었습니다.", new Auth(nickname, userId)));     
        
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
