package com.example.back.config;

import java.util.Arrays;

import com.example.back.repository.UserInformationRepository;
import com.example.back.security.AuthProvider;
import com.example.back.security.CustomUserDetailService;
import com.example.back.security.JwtAuthenticationEntryPoint;
import com.example.back.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@ComponentScan(basePackages = {"com.example.back.security"})
public class AuthConfig extends WebSecurityConfigurerAdapter{

    @Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private AuthProvider authProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
	public AuthConfig(UserInformationRepository userInfoRepository, CustomUserDetailService customUserDetailService,
		JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationFilter jwtAuthenticationFilter, AuthProvider authProvider) {
		this.customUserDetailService = customUserDetailService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authProvider = authProvider;
	}

    @Bean(name=BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    //cors setting, 인증, 허가 에러 시 공통적으로 처리하는 부분
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.addExposedHeader("Set-Cookie");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    
    @Override
    // http 관련 보안 설정 (중요!)
    protected void configure(HttpSecurity http) throws Exception{
        System.out.println("http Configure");

        http
            .cors()
            .configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated();
            //.and()
            // formSignIn을 하니까 authProvider가 작동함
            // form signIn을 하면 UsernamePasswordAuthenticationFilter 활성화
         


        // http
        // .cors()
        // .and()
        // .csrf()
        // .and()
        //     .authorizeRequests()
        // //무료 포스트에서는 무조건 허용
        //     .antMatchers("/api/posts/free").permitAll()
        //     //유료 포스트는 게스트를 제외한 모두를 허용
        //     .antMatchers("/api/posts/charge").access("hasRole('ADMIN') or hasRole('SUBSCRIBER') or hasRole('PUBLISHER')")
        //     //어드민 페이지는 어드민만
        //     .antMatchers("/admin/**").hasRole("ADMIN")
        //     //인증된 사용자는 어떤 요청도 ok
        //     .anyRequest().authenticated()
        // .and()
        // .formSignIn()
        //     .signInProcessingUrl("/signUp")
        //     .permitAll();
        // // .and()
        // //     .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
       
        // http.httpBasic();

    }

    @Override
    @Autowired
    //실제 인증을 진행할 부분
    //모든 인증을 관ㅣ하는 Authentication Manager를 생성해줌. UserDetailService를 통해 유저의 정보를 customUserDetailSercie에 담음
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        System.out.println("configure");
        //auth.authenticationProvider(authProvider);
        try{

            auth.authenticationProvider(this.authProvider);
            auth.userDetailsService(this.customUserDetailService);
        }
        catch(Exception e){
            System.out.println("EROOOOR :" + e.getMessage());
        }
    }


    @Override
    // 이미지, 자바스크립트, css 디렉토리 보안 설정
    public void configure(WebSecurity web){
        System.out.println("WebSec Configure");
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/font/**", "/html/**",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html"
        
        );
    }

}
