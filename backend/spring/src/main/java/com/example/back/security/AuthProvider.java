// package com.example.back.security;

// import java.util.ArrayList;


// import com.example.back.dto.UserDto;
// import com.example.back.repository.UserRepository;
// import com.example.back.service.CustomUserDetailService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;



// //계정 잠금여부, 활성화 
// @Component
// public class AuthProvider implements AuthenticationProvider{

//     @Autowired
//     private CustomUserDetailService customUserDetailService;

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        
        
//         //사용자가 화면에서 입력한 아디
//         String userId = (String)authentication.getPrincipal();
//         // 패스워드
//         String userPassword = (String)authentication.getCredentials();   


//         //userDetail 객체를 받아온다. 이를 provider에게 전달
//         UserDetails user = customUserDetailService.loadUserByUsername(userId);

//         System.out.println("반환된 유저의 값 " + user);

//         // if(!matchPassword(userPassword, user.getPassword())){
//         //     throw new BadCredentialsException(userId);
//         // }

//         //계정 활성화 여부를 확인

//         if(!user.isEnabled()){
//             System.out.println("heyyy");
//             throw new BadCredentialsException(userId);
//         }
        
//         return new UsernamePasswordAuthenticationToken(user.getUsername(), userPassword, user.getAuthorities());
//     }

//     @Override
//     public boolean supports(Class<?> authentication){
//         return true;
//     }

//     private boolean matchPassword(String loginPwd, String pw){
//         return loginPwd.equals(pw);
//     }

// }