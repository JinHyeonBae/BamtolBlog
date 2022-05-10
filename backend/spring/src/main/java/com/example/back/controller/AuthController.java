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

    // value 
    // @Operation(summary = "회원가입", description = "회원가입하는 규격입니다.", responses = {
    //     //@ApiResponse(responseCode = "201", description = "회원가입 성공"),
    //     //@ApiResponse(responseCode = "40901", description = "이미 존재하는 이메일"),
    //     //@ApiResponse(responseCode = "40902", description = "이미 존재하는 닉네임"),
    //     //@ApiResponse(responseCode = "500", description = "서버 에러")
    // })
    // @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/auth/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestHeader HttpHeaders headers, @RequestBody SignUpDto signUpDto){
        
        SignUpResponseDto signUpResult = auth.SignUp(signUpDto);

        return ResponseEntity.ok().body(signUpResult);
    }


    // 쿠키에 토큰을 넣어서 보냄, 파싱
    // //@ApiOperation(value="로그인", notes = "사용자 회원가입")
    // //@ApiResponses({
    //     //@ApiResponse(code = 200, message = "로그인 성공"),
    //     ////@ApiResponse(code = 400, message = "유효하지 않은 토큰"),
    //     //@ApiResponse(code = 40101, message = "존재하지 않는 아이디"),
    //     //@ApiResponse(code = 40102, message = "비밀번호 오류"), // status가 겹치는 경우에는 어떤 경우가 있는 지를 봐야한다. ex) 40401, 40402
    //     //@ApiResponse(code = 500, message = "서버 에러")
    // })
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginData, 
                                                   HttpServletRequest request, HttpServletResponse response){     
        
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword()));
		
        SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtTokenProvider.generateToken(authentication);
        //nickname, userId;
        
        Map<String, Object> loginResponseDto = auth.login(loginData.getEmail());
        String nickname = (String) loginResponseDto.get("nickname");
        Integer userId   = (Integer) loginResponseDto.get("userId"); 
        
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
