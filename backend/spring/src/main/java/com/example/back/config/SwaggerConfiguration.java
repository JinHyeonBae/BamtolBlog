package com.example.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration // 설정 명시 어노테이션
@EnableSwagger2
public class SwaggerConfiguration {
        

        public ApiInfo makeApiInfo(String title, String desc){
            return new ApiInfoBuilder()
                .title(title)
                .description(desc)
                .version("2.0")
                .build();
        }
    
        @Bean
        public Docket apiV1(){
            return new Docket(DocumentationType.SWAGGER_2)
                // 스웨거에서 제공하는 기본 응답 코드
                .useDefaultResponseMessages(false)
                .groupName("모든 API 설정")
                // ApiSelectorBuilder를 생성
                .select()
                // api 스펙이 작성되어 있는 컨트롤러를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                // apis에 있는 api 중 특정 경로 지정
                .paths(PathSelectors.ant("/**")).build()
                .apiInfo(makeApiInfo("BamTol Blog", "BamTol Blog에 관한 모든 API 규격을 볼 수 있습니다."));
        }

        @Bean
        public Docket authApi(){
            return new Docket(DocumentationType.SWAGGER_2)
                // 스웨거에서 제공하는 기본 응답 코드
                .useDefaultResponseMessages(false)
                .groupName("Authentication")
                // ApiSelectorBuilder를 생성
                .select()
                // api 스펙이 작성되어 있는 컨트롤러를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                // apis에 있는 api 중 특정 경로 지정
                .paths(PathSelectors.ant("/auth/*")).build()
                .apiInfo(makeApiInfo("Auth API", "로그인, 회원가입 등의 인증에 관한 API 규격이 나와있습니다."));
        }


        @Bean
        public Docket postApi(){
            return new Docket(DocumentationType.SWAGGER_2)
                // 스웨거에서 제공하는 기본 응답 코드
                .useDefaultResponseMessages(false)
                .groupName("Posts")
                .select()
                // api 스펙이 작성되어 있는 컨트롤러를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                // apis에 있는 api 중 특정 경로 지정
                .paths(PathSelectors.ant("/posts/*")).build()
                .apiInfo(makeApiInfo("Post API", "Post에 관한 API 규격들을 볼 수 있습니다"));
        }

}

