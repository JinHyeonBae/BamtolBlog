package com.example.back.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.back.response.ErrorCode;

import io.jsonwebtoken.JwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private CustomUserDetailService customUserDetailsService;

	// jwt만을 검증하는 시스템
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//System.out.println("request header : " + request.getCookies());
			String jwt = getJwtFromRequest(request); //리퀘스트 헤더에서 토큰 분리
			
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { //null 검사 & valid 토큰 검사
				String email = tokenProvider.getUserEmailFromJWT(jwt);

				UserPrincipal userDetails = customUserDetailsService.loadUserByUsername(email);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword(), userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception ex) {
			LOGGER.error("Could not set user authentication in security context", ex);
			LOGGER.info("exception 에러 내용 :"+ ex.getMessage());
			throw new JwtException(ErrorCode.INVALID_TOKEN.name());
		}

		filterChain.doFilter(request, response); // invoke the rest of the application
	}

	private String getJwtFromRequest(HttpServletRequest request) { //request에서 토큰 분리
		
		if(request.getCookies() != null){
			String bearerToken = request.getCookies()[0].getValue();
			
			System.out.println("Bearer token :" + bearerToken);

			if (StringUtils.hasText(bearerToken.toString())) {
				return bearerToken.toString();
			}
			return null;
		}
		return null;
	}
}
