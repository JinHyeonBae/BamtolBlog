package com.example.back.service;

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

        signUpResponseDto.setStatus(HttpStatus.CREATED);
        signUpResponseDto.setMessage("회원가입 되셨습니다.");


        return signUpResponseDto;

    }

}
