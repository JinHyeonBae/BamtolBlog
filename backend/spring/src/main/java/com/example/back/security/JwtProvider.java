package com.example.back.security;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.example.back.dto.AuthDto;
import com.example.back.dto.UserDto;
import com.example.back.model.user.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
    
    @Value("spring.jwt.secret")
    private final String secretKey = "c8234au-3njkj2!-sd341=1224?&bsqqow9kl-fw*dazvzewrqw8";
    private final long accessExpireTime = 60 * 60 * 30000000; 
    private final long refreshExpireTime = 60 * 60 * 3000L;

    //private CustomUserDetailService customUserDetailService;

    //토큰을 생성하는 곳
    public String createAccessToken(AuthDto.LoginDto loginDto, int userId){
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "jwt");

        Map<String, Object> payloads = new HashMap<>();
        //발행자
        payloads.put("iss", "admin");
        // 토큰의 대상자
        payloads.put("aud", loginDto.getEmail());
        payloads.put("id", userId);

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpireTime);

        String jwt = Jwts
                    .builder()
                    .setHeader(headers)
                    .setClaims(payloads)
                    .setSubject("user")
                    .setExpiration(expiration)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

        return jwt;
    }

    //토큰에서 인증정보 조회
    // public Authentication getAuthontication(String token){
    //     System.out.println("getAuthentication function");
    //     //UserDetails userDailes = customUserDetailService.loadUserByUsername(this.getUserInfo(token));
    //     //return new UsernamePasswordAuthenticationToken(userDailes, "", userDailes.getAuthorities());
    // }

    // 토큰에서 사용자 정보 조회
    public String getUserInfo(String token){
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("aud");
    }

    // //Request의 Header에서 token 값 추출
    // public Object resolveToken(HttpHeaders request){
    //     return request.get("token");
    // }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }
    
}
