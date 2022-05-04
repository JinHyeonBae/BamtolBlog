package com.example.back.service;

import java.util.HashMap;
import java.util.Map;

import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.response.ErrorCode;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    ErrorCode errorcode;

    public SignUpResponseDto SignUp(SignUpDto signUpDto){

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        
        // User 테이블에서 nickname 빼자
        urRepo.findByNickname(signUpDto.getNickname())
            .ifPresent(users->{
                throw new IllegalStateException("DUPLICATE_NICKNAME");
            });
        
        urInfoRepo.findByEmail(signUpDto.getEmail())
            .ifPresent((users)->{
                throw new IllegalStateException("DUPLICATE_EMAIL");
            });
        
 
        signUpDto.setPassword(signUpDto.getPassword());
        urInfoRepo.saveSignUpUserInfo(signUpDto.toEntity());

        signUpResponseDto.setStatus(201);
        signUpResponseDto.setMessage("회원가입 되셨습니다.");


        return signUpResponseDto;
    }

    public Map<String,Object> login(String email){
        
        Map<String,Object> map = new HashMap<String , Object>();

        urInfoRepo.findByEmail(email).ifPresent(users->{
            map.put("nickname", users.getNickname());
            map.put("userId", users.getUserId());
        });
        
        return map;

    }

}
