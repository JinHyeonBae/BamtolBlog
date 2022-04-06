package com.example.back.service;

import java.nio.channels.AlreadyBoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import com.example.back.dto.AuthDto;
import com.example.back.dto.UserDto;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.UserPermission;
import com.example.back.model.user.Users;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.swagger.annotations.ApiResponse;
import javassist.NotFoundException;

@Service
public class AuthService {
    @Autowired  
    private JwtProvider jwtProvider;
  
    // db로 아이디 값을 체크하면 끝
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

    public HttpStatus SignUp(AuthDto.SignUpDto signUpDto){

        System.out.println("Test email is :" + signUpDto.getEmail());
        // users 테이블에서 email을 가져옴
        Users isUser = urRepo.findByEmail(signUpDto.getEmail());
    
        if(isUser != null){
            System.out.println("get user Email at DB ! :"+ isUser.getEmail());
            return HttpStatus.CONFLICT;
        }


        try{    
            urRepo.saveSignUpUserInfo(new Users(signUpDto.getEmail()));
            
            Users users = urRepo.findByEmail(signUpDto.getEmail());
            String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

            signUpDto.setPassword(encodedPassword);
            signUpDto.setUserId(users.getId());
            
            // users, user_information에 데이터 삽입
            //System.out.println("last idx :" + lastIdx);
            urInfoRepo.saveSignUpUserInfo(signUpDto.toEntity());

            // user_permission 테이블에 refresh token save
        }
        catch(Exception e){
            System.out.println("exception :" + e.getMessage());
        }

        return HttpStatus.CREATED;
    }

    
    // 유저를 조회하고, 토큰과 리프레쉬 토큰을 생성하는 서비스

    private void saveToken(String token){ 
        urAuthRepo.saveUserToken(UserAuth.builder().token(token).build());
    }

    public HashMap<String, String> login(AuthDto.LoginDto loginDto) throws AuthenticationException{
        HashMap<String, String> result = new HashMap<String, String>();

        System.out.println("loginDto :"+ loginDto);

        System.out.println("loginDto password :"+ loginDto.getPassword());
        
        try{
            // authenticationManager.authenticate(
            //     new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            System.out.println("여기");
            UserInformation users = urInfoRepo.findByEmail(loginDto.getEmail());

            System.out.println("저기");
            if(users == null){
                result.put("404", "존재하지 않는 아이디입니다.");
                return result;
            }

            if(!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
                System.out.println("비밀번호 에러 :"+loginDto.getEmail());
                result.put("401", "비밀번호가 일치하지 않습니다.");
                return result;
            }

            HashMap<String, String> createToken = createTokenReturn(loginDto, users.getUserId());
            result.put("accessToken", createToken.get("accessToken"));
            saveToken(createToken.get("accessToken"));

        }

        catch(Exception e){
            e.printStackTrace();
            throw new AuthenticationException();
        }

        return result;
    }

    private HashMap<String, String> createTokenReturn(AuthDto.LoginDto loginDto, int userId){
        HashMap<String, String> result = new HashMap<>();

        String accessToken = jwtProvider.createAccessToken(loginDto, userId);
        System.out.println("createToken ! :" + accessToken);
        // String refreshToken = jwtProvider.createAccessToken(loginDto).get("refreshToken");
        // String refreshTokenExpirationAt = jwtProvider.createRepreshToken(loginDto).get("refreshExpireationAt");

        result.put("accessToken", accessToken);
        return result;

    }

    // 보안적인 면에서 String 자체를 넘기는 게 괜찮을까
    public boolean isValidToken(String token){
        
        if(jwtProvider.validateJwtToken(token)){
            
            String email = jwtProvider.getUserInfo(token);
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
