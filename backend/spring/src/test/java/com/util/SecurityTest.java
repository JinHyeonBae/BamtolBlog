package com.util;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.back.MainApplication;
import com.example.back.security.JwtProvider;
import com.example.back.service.PostService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


@SpringBootTest
@ContextConfiguration(classes = MainApplication.class)
public class SecurityTest {
    

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @MockBean
    WithMockCustomUser withUsers;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private String token;
    private int testUserId;

    @Autowired
    private JwtProvider jwtProvider;


    @BeforeEach
    public void setUp(){

        Authentication authentication = new UsernamePasswordAuthenticationToken("1234@naver.com", "1234");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        // 필드 값을 내가 정할 수 있다.
        //ReflectionTestUtils.setField(postService, "headers", new HttpHeaders());
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
            
            // interface로 어떻게 할 방법이 없을까?!
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
    void extractUserIdFromJwt() throws Exception{
        this.LoginSecurityTest(); // token이 저장됨.

        boolean isValidated = jwtProvider.validateToken(this.token);

        if(isValidated){
            LOGGER.info("VALIDATE!");

            Integer userId = jwtProvider.getUserIdFromJWT(this.token);
            this.testUserId = userId;
        }
        else{
            LOGGER.info("not validated token");
        }

        // userId를 추출하고 readPost 읽기 요청

    }

    // subuser 테이블 publisher_id를 찾을 수 없어서 에러가 난다 -> flyway가 적용이 안됨...
    @Test
    void readPostTest() throws Exception{
        this.extractUserIdFromJwt();

        HttpHeaders httpHeaders = new HttpHeaders();
        
        httpHeaders.add(HttpHeaders.SET_COOKIE, "access_Token=" + this.token);
        LOGGER.info("token 확인 : "+ this.token);
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders
                                            .get("/posts/3")
                                            .headers(httpHeaders)
                                            );

        result
            .andDo(MockMvcResultHandlers.print())
            .andReturn().getResponse().getContentAsString();

    }

    @Test
    void loginTest() throws Exception{

        JSONObject loginInfoJson = new JSONObject();

        loginInfoJson.put("email", "1234@naver.com");
        loginInfoJson.put("password", "1234");

        Jsonify tojson = new Jsonify("1234@naver.com", "1234");

        Gson gson = new Gson();
        String json =  gson.toJson(tojson);
        
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json));

        result
            .andDo(MockMvcResultHandlers.print());
            
    }


}


