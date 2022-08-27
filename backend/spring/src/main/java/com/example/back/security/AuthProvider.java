package com.example.back.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;



//계정 잠금여부, 활성화 
@Component
public class AuthProvider implements AuthenticationProvider{

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthProvider.class);


    //로그인
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
       
        String userEmail = "";
        String userPassword= "";
        userEmail = (String)authentication.getPrincipal();
        userPassword = (String)authentication.getCredentials();   

        LOGGER.info("USER EMAIL :" + userEmail);
        LOGGER.info("USER PASSWORD :" + userPassword);

        // userDetail 객체를 받아온다. 이를 provider에게 전달
        // It is typically called by an AuthenticationProvider instance in order to authenticate a user. 
        // when a username and password is submitted, a UserdetailsService is called to find the password for that user to see if it is correct. 
        
        JSONObject json = new JSONObject();
        try {
            json.put("email", userEmail);
            json.put("password", userPassword);

        } catch (JSONException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        
        UserPrincipal user = customUserDetailService.loadUserByUsername(json); //user.getPassword() 출력 가능
        
        if(!user.isEnabled()){
            throw new BadCredentialsException(userEmail);
        }

        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication){
        return true;
    }

    private boolean matchPassword(String signInPwd, String pw){
        return signInPwd.equals(pw);
    }

}