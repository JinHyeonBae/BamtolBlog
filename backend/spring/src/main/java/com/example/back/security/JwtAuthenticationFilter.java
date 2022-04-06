// package com.example.back.security;

// import java.io.IOException;

// import javax.servlet.FilterChain;
// import javax.servlet.Servlet;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.GenericFilterBean;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends GenericFilterBean{
   
//     private JwtProvider jwtProvider;

//     public JwtAuthenticationFilter(JwtProvider jwtTokenProvider) {
//         this.jwtProvider = jwtTokenProvider;
//     }

//     // jwt 토큰의 인증 정보를 현재 실행중인 security context에 저장하는 역할을 수행
//     // 토큰이 유효하다면, 유효성 체크 후 context에 인증정보 저장한다.
//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        
//         String token = jwtProvider.resolveToken((HttpServletRequest) request);
//         System.out.println("to " + token);
//         if(token != null && jwtProvider.validateJwtToken(request, token)){
//             System.out.println("provider Token");
//             Authentication authentication = jwtProvider.getAuthontication(token);
//             //  저장
//             SecurityContextHolder.getContext().setAuthentication(authentication);
//         }    

//         System.out.println("Valid Token :" + token);

//         filterChain.doFilter(request, response);
        
//     }




// }
