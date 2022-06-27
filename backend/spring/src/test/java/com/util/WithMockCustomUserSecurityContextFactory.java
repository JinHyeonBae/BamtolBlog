package com.util;

import com.example.back.security.CustomUserDetailService;
import com.example.back.security.UserPrincipal;
import com.example.back.service.Role;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.util.Assert;


public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private UserDetailsService userDetailsService;

    @Autowired
    public WithMockCustomUserSecurityContextFactory(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserPrincipal userContext = new UserPrincipal(1, "test@naver.com", "aabb");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userContext.getEmail(), userContext.getPassword());

        context.setAuthentication(token);

        return context;
    }

    public SecurityContext creatSecurityContext(WithUserDetails withUsers){
        String username = withUsers.value();
        Assert.hasLength(username, "value() must be non-empty String");

		UserDetails principal = userDetailsService.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		context.setAuthentication(authentication);
        return context;
    }

}