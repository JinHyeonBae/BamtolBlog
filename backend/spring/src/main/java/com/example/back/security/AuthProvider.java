package com.example.back.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


//계정 잠금여부, 활성화 
@Component
public class AuthProvider implements AuthenticationProvider{

    @Autowired
    private CustomUserDetailService customUserDetailService;


    //로그인
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
       
        String userEmail = "";
        String userPassword= "";

        userEmail = (String)authentication.getPrincipal();
        userPassword = (String)authentication.getCredentials();   

        // userDetail 객체를 받아온다. 이를 provider에게 전달
        // It is typically called by an AuthenticationProvider instance in order to authenticate a user. 
        // when a username and password is submitted, a UserdetailsService is called to find the password for that user to see if it is correct. 
        Map<String, String> authenticationList = new HashMap<String,String>();

        authenticationList.put("email", userEmail);
        authenticationList.put("password", userPassword);

        UserDetails user = customUserDetailService.loadUserByUsername(authenticationList);

        if(!user.isEnabled()){
            throw new BadCredentialsException(userEmail);
        }

        return new UsernamePasswordAuthenticationToken(user.getUsername(), userPassword, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication){
        return true;
    }

    private boolean matchPassword(String loginPwd, String pw){
        return loginPwd.equals(pw);
    }

}