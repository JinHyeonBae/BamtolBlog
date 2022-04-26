package com.example.back.service;

import java.util.HashMap;

import javax.naming.AuthenticationException;

import com.example.back.dto.AuthDto;
import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.response.ResponseDto.LoginResponseDto;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired  
    private JwtProvider jwtProvider;
  
    @Autowired
    UserRepository urRepo;

    @Autowired
    UserInformationRepository urInfoRepo;

    @Autowired
    UserPermissionReposotiry urPermitRepo;

    @Autowired
    UserAuthRepository urAuthRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public SignUpResponseDto SignUp(SignUpDto signUpDto){

        System.out.println("사용자 닉네임 :" + signUpDto.getNickname());
        
        // 고유한 값들을 모아놓은 건 어떨까? 생각해보자
        Users isExistNickname = urRepo.findByNickname(signUpDto.getNickname());
        UserInformation isExistUserEmail = urInfoRepo.findByEmail(signUpDto.getEmail());

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        
        //이메일, 닉네임 고유함
        if(isExistNickname != null){
            System.out.println("데이터베이스에서 똑같은 닉네임 발견! :"+ isExistNickname.getNickname());
            signUpResponseDto.setStatus(HttpStatus.CONFLICT);
            signUpResponseDto.setMessage("이미 있는 닉네임입니다.");

            return signUpResponseDto;
        }

        if(isExistUserEmail != null){
            System.out.println("데이터베이스에서 똑같은 이메일 발견! :"+ isExistUserEmail.getNickname());
            signUpResponseDto.setStatus(HttpStatus.CONFLICT);
            signUpResponseDto.setMessage("이미 있는 이메일입니다.");

            return signUpResponseDto;
        }

        try{    
            //urRepo.saveSignUpUserInfo(new Users(signUpDto.getNickname()));

            //이미 클라이언트에서 암호화된 데이터
            signUpDto.setPassword(signUpDto.getPassword());
            
            //users, user_information에 데이터 삽입
            urInfoRepo.saveSignUpUserInfo(signUpDto.toEntity());

            // user_permission 테이블에 refresh token save
        }
        catch(Exception e){
            System.out.println("exception 내용은:" + e.getMessage());
            signUpResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            signUpResponseDto.setMessage("내부 서버 에러입니다.");

            return signUpResponseDto;
        }

        signUpResponseDto.setStatus(HttpStatus.CREATED);
        signUpResponseDto.setMessage("회원가입 되셨습니다.");

        return signUpResponseDto;

    }


    public LoginResponseDto login(LoginDto loginDto) throws AuthenticationException{
        
        LoginResponseDto loginResponseDto = new LoginResponseDto();

        try{
            UserInformation userInfo = urInfoRepo.findByEmail(loginDto.getEmail());
            System.out.println("login Email :" + loginDto.getEmail());
            if(!loginDto.getPassword().equals(userInfo.getPassword())){
                loginResponseDto.setStatus(HttpStatus.FORBIDDEN);
                loginResponseDto.setMessage("비밀번호가 일치하지 않습니다.");

                return loginResponseDto;
            }

            HashMap<String, String> createToken = createTokenReturn(loginDto, userInfo.getUserId());
            loginResponseDto.setAccesstoken(createToken.get("accessToken"));
            loginResponseDto.setStatus(HttpStatus.OK);
            loginResponseDto.setMessage("로그인 되었습니다.");
            loginResponseDto.setUserId(userInfo.getUserId());
            // 추후에 security 추가하면 없어질 코드

            System.out.println("로그 확인");
            //saveToken(createToken.get("accessToken"), userInfo.getUserId());

        }
        // 유저가 없는 경우
        catch(NullPointerException e){
            System.out.println("NULL ERROR");
            loginResponseDto.setStatus(HttpStatus.NOT_FOUND);
            loginResponseDto.setMessage("존재하지 않는 아이디입니다.");
            
            return loginResponseDto;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            loginResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            loginResponseDto.setMessage("서버 에러");
        }

        return loginResponseDto;
    }

    private HashMap<String, String> createTokenReturn(AuthDto.LoginDto loginDto, int userId){
        HashMap<String, String> result = new HashMap<>();

        String accessToken = jwtProvider.createAccessToken(loginDto, userId);
        System.out.println("Token createToken");
        // String refreshToken = jwtProvider.createAccessToken(loginDto).get("refreshToken");
        // String refreshTokenExpirationAt = jwtProvider.createRepreshToken(loginDto).get("refreshExpireationAt");

        result.put("accessToken", accessToken);
        return result;

    }

    // 보안적인 면에서 String 자체를 넘기는 게 괜찮을까
    // TODO: 함수 이름 변경, 토큰 유효성 검사 로직 수정
    public boolean isValidToken(String cookie){

        String Cookies[] = cookie.split("=");
        String token[] = Cookies[1].split(";");

        if(jwtProvider.validateJwtToken(token[0])){
            System.out.println("validation 통과!");

            String email = jwtProvider.getUserInfo(token[0]);
            System.out.println("Email :" + email);
            UserInformation userAuths = urInfoRepo.findByEmail(email);  
            
            
            if(userAuths == null){
                throw new UsernameNotFoundException("User "+ email + " Not Found");
            }
        }
        else{
            return false;
        }

        return true;
    }

}
