package com.example.back.service;

import java.util.HashMap;
import java.util.Map;

import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.exception.ErrorCode;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.response.ResponseDto.SignUpResponseDto;
import com.example.back.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private JwtProvider jwtProvider;
  
    UserRepository urRepo;

    UserInformationRepository urInfoRepo;

    UserPermissionReposotiry urPermitRepo;

    UserAuthRepository urAuthRepo;

    PasswordEncoder passwordEncoder;

    ErrorCode errorcode;

    public AuthService(JwtProvider jwtProvider, UserRepository urRepo, UserInformationRepository urInfoRepo, UserAuthRepository urAuthRepo, PasswordEncoder passwordEncoder){
        this.jwtProvider = jwtProvider;
        this.urRepo = urRepo;
        this.urInfoRepo = urInfoRepo;
        this.urAuthRepo = urAuthRepo;
        this.passwordEncoder = passwordEncoder;

    }


    public SignUpResponseDto SignUp(SignUpDto signUpDto){

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        
        urInfoRepo.findByNickname(signUpDto.getNickname())
            .ifPresent(users->{
                if(users.getEmail().equals(signUpDto.getEmail())) throw new IllegalStateException("DUPLICATE_NICKNAME_AND_EMAIL");
                else throw new IllegalStateException("DUPLICATE_NICKNAME");
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
