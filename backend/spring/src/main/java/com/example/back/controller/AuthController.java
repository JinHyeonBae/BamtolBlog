package com.example.back.controller;

import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.example.back.dto.AuthDto;
import com.example.back.dto.UserDto;
import com.example.back.model.user.Users;
import com.example.back.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.models.Response;

@RestController
public class AuthController {
    
    @Autowired
    private AuthService auth;


    @PostMapping("/auth/signup")
    // @ResponseBody Error : 
    public HttpStatus signUp(AuthDto.SignUpDto signUpDto){
        HttpStatus status = auth.SignUp(signUpDto);
        System.out.println(status);
        return status;
    }


    @PostMapping("/auth/test1")
    // @ResponseBody Error : 
    public void requestAnnotationTest(@RequestHeader HttpHeaders headers, @RequestBody String bodys)
    {
        System.out.println("헤더에 들어있는 값  :" + headers);
        System.out.println("바디에 들어있는 값  :" + bodys);
        
    }

    @PostMapping("/auth/login")
    public BodyBuilder login(AuthDto.LoginDto loginRequest){     
       
        HashMap<String, String> token = new HashMap<>();
        try {
            token = auth.login(loginRequest);
        } catch (AuthenticationException e) {
            System.out.println("Authentication Error:" + e.getMessage());
            e.printStackTrace();
        }

        if(token.get("accessToken") == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST);
       
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accessToken", token.get("accessToken"));


        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders);
    }
}
