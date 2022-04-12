package com.example.back.config;

import java.util.Arrays;

import com.example.back.security.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AuthConfig extends WebSecurityConfigurerAdapter{

    @Autowired
//    private CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(name=BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    //cors setting, 인증, 허가 에러 시 공통적으로 처리하는 부분
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("/*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    
    @Override
    // http 관련 보안 설정 (중요!)
    protected void configure(HttpSecurity http) throws Exception{
        System.out.println("http Configure");

        http
            .cors()
            .and()
            .csrf()
            .disable(); //이걸 추가 안하면 forbidden

            // .and()
            // .authorizeRequests()
            // .antMatchers("/user.login").permitAll()
            // .anyRequest().authenticated()
            // .and()
            // .formLogin()
            // .loginProcessingUrl("/user/login")
            // .permitAll();
        // .and()
        // .formLogin()
        // .loginProcessingUrl("/user/login")
        //     .permitAll()
        //     .defaultSuccessUrl("/")
        //     .failureUrl("/user/login")
        //     .and()
        // .logout();


        // http
        // .cors()
        // .and()
        // .csrf()
        // .and()
        //     .authorizeRequests()
        // //무료 포스트에서는 무조건 허용
        //     .antMatchers("/posts/free").permitAll()
        //     //유료 포스트는 게스트를 제외한 모두를 허용
        //     .antMatchers("/posts/charge").access("hasRole('ADMIN') or hasRole('SUBSCRIBER') or hasRole('PUBLISHER')")
        //     //어드민 페이지는 어드민만
        //     .antMatchers("/admin/**").hasRole("ADMIN")
        //     //인증된 사용자는 어떤 요청도 ok
        //     .anyRequest().authenticated()
        // .and()
        // .formLogin()
        //     .loginProcessingUrl("/login")
        //     .permitAll();
        // // .and()
        // //     .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
       
        // http.httpBasic();

    }

    @Override
    //실제 인증을 진행할 부분
    //모든 인증을 관ㅣ하는 Authentication Manager를 생성해줌. UserDetailService를 통해 유저의 정보를 customUserDetailSercie에 담음
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        System.out.println("configure");
        //auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }


    @Override
    // 이미지, 자바스크립트, css 디렉토리 보안 설정
    public void configure(WebSecurity web){
        System.out.println("WebSec Configure");
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/font/**", "/html/**");
    }

}
