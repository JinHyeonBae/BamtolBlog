package com.example.back.service;

import java.nio.channels.AlreadyBoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import com.example.back.dto.AuthDto;
import com.example.back.dto.UserDto;
import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.UserPermission;
import com.example.back.model.user.Users;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.response.ResponseDto.LoginResponseDto;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        System.out.println("Test nickname is :" + signUpDto.getNickname());
        // users 테이블에서 nickname 가져옴
        Users isExistNickname = urRepo.findByNickname(signUpDto.getNickname());
        UserInformation isExistUserEmail = urInfoRepo.findByEmail(signUpDto.getEmail());

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        
        //이메일, 닉네임 고유함
        if(isExistNickname != null){
            System.out.println("get user nickname at DB ! :"+ isExistNickname.getNickname());
            signUpResponseDto.setStatus(HttpStatus.CONFLICT);
            signUpResponseDto.setMessage("이미 있는 닉네임입니다.");

            return signUpResponseDto;
        }

        if(isExistUserEmail != null){
            System.out.println("get user email at DB ! :"+ isExistUserEmail.getNickname());
            signUpResponseDto.setStatus(HttpStatus.CONFLICT);
            signUpResponseDto.setMessage("이미 있는 이메일입니다.");

            return signUpResponseDto;
        }

        try{    
            urRepo.saveSignUpUserInfo(new Users(signUpDto.getNickname()));
            Users users = urRepo.findByNickname(signUpDto.getNickname());
            
            //이미 클라이언트에서 암호화된 데이터
            signUpDto.setPassword(signUpDto.getPassword());
            signUpDto.setUserId(users.getId());
            
            // users, user_information에 데이터 삽입
            //System.out.println("last idx :" + lastIdx);
            urInfoRepo.saveSignUpUserInfo(signUpDto.toEntity());

            // user_permission 테이블에 refresh token save
        }
        catch(Exception e){
            System.out.println("exception 내용은:" + e.getMessage());
            signUpResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            signUpResponseDto.setMessage("내부 서버 에러입니다.");
        }

        signUpResponseDto.setStatus(HttpStatus.CREATED);
        signUpResponseDto.setMessage("회원가입 되셨습니다.");

        return signUpResponseDto;

    }

    
    // 유저를 조회하고, 토큰과 리프레쉬 토큰을 생성하는 서비스

    private void saveToken(String token, int userId){ 
        urAuthRepo.saveUserToken(UserAuth.builder().token(token).userId(userId).build());
    }


    public LoginResponseDto login(LoginDto loginDto) throws AuthenticationException{
        
        LoginResponseDto loginResponseDto = new LoginResponseDto();

        try{
            UserInformation userInfo = urInfoRepo.findByEmail(loginDto.getEmail());

            if(!loginDto.getPassword().equals(userInfo.getPassword())){
                loginResponseDto.setStatus(HttpStatus.FORBIDDEN);
                loginResponseDto.setMessage("비밀번호가 일치하지 않습니다.");

                return loginResponseDto;
            }

            HashMap<String, String> createToken = createTokenReturn(loginDto, userInfo.getUserId());
            loginResponseDto.setAccessToken(createToken.get("accessToken"));
            loginResponseDto.setStatus(HttpStatus.OK);
            loginResponseDto.setMessage("로그인 되었습니다.");

            System.out.println("로그 확인");
            //saveToken(createToken.get("accessToken"), userInfo.getUserId());

        }
        // 유저가 없는 경우
        catch(NullPointerException e){
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
        System.out.println("createToken !");
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
