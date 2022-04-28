package com.example.back.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.back.dto.AuthDto.LoginDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
    
    @Value("spring.jwt.secret")
    private final String secretKey = "c8234au-3njkj2!-sd341=1224?&bsqqow9kl-fw*dazvzewrqw8";
    // 60초 : 60000, # 60분 : 3600000, # 1일 : 86400000, # 7일 : 604800000 ms 단위
    private final long accessExpireTime = 1000 * 60L * 60L * 48L; //2일,  ms 단위인가?
    private final long refreshExpireTime = 1000 * 60 * 60 * 24L; // 하루.

    //private CustomUserDetailService customUserDetailService;

    //토큰을 생성하는 곳
    public String createAccessToken(LoginDto loginDto, int userId){
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "jwt");

        Map<String, Object> payloads = new HashMap<>();
        //발행자
        payloads.put("iss", "admin");
        // 토큰의 대상자
        payloads.put("aud", loginDto.getEmail());
        payloads.put("id", userId);

        Date expiration = new Date();
        System.out.println("현재 시간 :" + expiration.getTime());
        System.out.println("토큰 유효 시간 :" + expiration.getTime() + accessExpireTime);
        
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
        // Claims tokenInfo = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        // System.out.println("토큰 남은 시간 :" + tokenInfo.getExpiration());
        System.out.println(token);
        System.out.println((String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("aud"));
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("aud");
    }

    //Request의 Header에서 token 값 추출
    public List<String> resolveToken(HttpHeaders request){
        return request.get("token");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateJwtToken(String token){

        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            System.out.println("Invalid Jwt Token :" + e.getMessage());
        }

        return false;
    }
    
}
