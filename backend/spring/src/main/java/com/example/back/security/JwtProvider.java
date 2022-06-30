package com.example.back.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.response.ErrorCode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
    
    @Value(value = "${jwt.secret-key}")
    private String secretKey;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    private final long accessExpireTime = 1000 * 60L * 60L * 48L; //2일,  ms 단위인가?
    private final long refreshExpireTime = 1000 * 60 * 60 * 96L; // 4일.
    // 60초 : 60000, # 60분 : 3600000, # 1일 : 86400000, # 7일 : 604800000 ms 단위

    private CustomUserDetailService customUserDetailService;


    public String generateToken(Authentication authentication, Integer userId) {

        Map<String, Object> headers = new HashMap<>();
        Map<String, Object> payloads = new HashMap<>();
        
        headers.put("type", "Bearer");
        payloads.put("iss", "admin");
        payloads.put("aud", authentication.getPrincipal()); //email
        payloads.put("id", userId);

    
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessExpireTime);

            return Jwts
                    .builder()
                    .setHeader(headers)
                    .setClaims(payloads)
                    .setSubject("userPrincipal")
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

	}

    // 토큰에서 사용자 정보 조회
    public String getUserInfo(String token){
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("aud");
    }

    //Request의 Header에서 token 값 추출
    public List<String> resolveToken(HttpHeaders request){
        // httpHeaders에서 쿠키를 추출하는 방법
        System.out.println(request);
        System.out.println(request.get(HttpHeaders.SET_COOKIE).indexOf("="));
        int idx = request.get(HttpHeaders.SET_COOKIE).indexOf("=");

        

        return request.getValuesAsList(HttpHeaders.SET_COOKIE);
    }

    public Integer getUserIdFromJWT(String token) { //userId 가져오기
		Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
        System.out.print("claims : " + claims.get("id"));
		return (Integer) claims.get("id");
	}

    
    public String getUserEmailFromJWT(String token) { //Email 가져오기
		Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
        System.out.println("AUDIENCE :" + claims.getAudience());

        System.out.println("AUDIENCE :" + claims.get("aud"));
		return (String) claims.get("aud");
	}

    public String parseJwtInsideCookie(String tokenWithKey){

        String[] parse = tokenWithKey.split("=");
        return parse[1];
    }


    // 토큰의 유효성 검사
    public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			LOGGER.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			LOGGER.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			LOGGER.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT claims string is empty");
		}
        
		return false;
	}
    
}
