package com.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.example.back.service.Role;

import lombok.NoArgsConstructor;

// custom Security Context
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    

    String username() default "martin";

    String getEmail() default "test@naver.com";
    
    String getPassword() default "aabb";

    Role role() default Role.MEMBER;
}

