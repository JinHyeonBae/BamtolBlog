package com.util;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.back.MainApplication;
import com.google.gson.Gson;


@SpringBootTest
@ContextConfiguration(classes = MainApplication.class)
public class SecurityTest {
    

    @MockBean
    WithMockCustomUser withUsers;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private String token;

    @BeforeEach
    public void setUp(){

        Authentication authentication = new UsernamePasswordAuthenticationToken("1234@naver.com", "1234");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    @Test
    @WithAnonymousUser
    // 인증되지 않는 상태를 설정하는 어노테이션
    // 인증 상태를 초기화 시키는 역할
    void unAuthorizeTest(){

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    }


    @Test
    @WithUserDetails
    void securityContextTest(){


    }

    @Test
    @WithMockCustomUser
    public void LoginSecurityTest() throws Exception{   
            
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String password = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            
            Jsonify tojson = new Jsonify(email, password);

            Gson gson = new Gson();
            String json =  gson.toJson(tojson);

            System.out.println("json :"+ json);
            
            ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json));

            result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
                
            Cookie token = result.andReturn().getResponse().getCookie("access_Token");

            this.token = token.getValue();
        
        }

    @Test
    void checkTokenStored() throws Exception{
        this.LoginSecurityTest();

        System.out.println("token :" + this.token);

    }


}


