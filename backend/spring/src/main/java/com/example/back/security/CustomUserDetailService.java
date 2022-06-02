package com.example.back.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.example.back.model.user.UserInformation;
import com.example.back.repository.UserInformationRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 사용자별 데이터를 로드하는 인터페이스
@Service
public class CustomUserDetailService implements UserDetailsService{
    
    // db로 아이디 값을 체크하면 끝

    @Autowired
    UserInformationRepository userInfoRepo;

    private int id;
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
    


  
    @Transactional
    public UserDetails loadUserByUsername(Map<String, String> Data) 
                throws UsernameNotFoundException, DataAccessException, BadCredentialsException{
        
        // email로 db 조회

        String email = Data.get("email");
        String password = Data.get("password");

        UserInformation userAuths = userInfoRepo.findByEmail(email).orElseThrow(() -> 
                                    new InternalAuthenticationServiceException("EMAIL_ERROR"));  
                

        if(!userAuths.getPassword().equals(password)){
            throw new BadCredentialsException("PASSWORD_ERROR");
        }
 
        //엔티티 아님!
        return UserPrincipal.create(userAuths);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        UserInformation userAuths = userInfoRepo.findByEmail(email).orElseThrow(() -> 
        new UsernameNotFoundException("User not found with email : " + email)); 

        return UserPrincipal.create(userAuths);
    }



}
