package com.example.back.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.models.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration // 설정 명시 어노테이션
@EnableSwagger2
public class SwaggerConfiguration {
        
        private String version = "V2";
        private String title = "Cristoval GuestBook API " + version;



        @Bean
        public Docket apiV1(){
            return new Docket(DocumentationType.SWAGGER_2)
                // 스웨거에서 제공하는 기본 응답 코드
                .useDefaultResponseMessages(false)
                .groupName("AllContoller")
                // ApiSelectorBuilder를 생성
                .select()
                // api 스펙이 작성되어 있는 컨트롤러를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                // apis에 있는 api 중 특정 경로 지정
                .paths(PathSelectors.ant("/**")).build()
                .apiInfo(apiInfoV1());
        }

        @Bean
        public Docket apiV3(){
            return new Docket(DocumentationType.SWAGGER_2)
                // 스웨거에서 제공하는 기본 응답 코드
                .useDefaultResponseMessages(false)
                .groupName("Auth")
                // ApiSelectorBuilder를 생성
                .select()
                // api 스펙이 작성되어 있는 컨트롤러를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                // apis에 있는 api 중 특정 경로 지정
                .paths(PathSelectors.ant("/auth")).build()
                .apiInfo(apiInfoV3());
        }

        @Bean
        public Docket apiV2(){
            return new Docket(DocumentationType.SWAGGER_2)
                // 스웨거에서 제공하는 기본 응답 코드
                .useDefaultResponseMessages(true)
                .groupName("Post")
                .select()
                // api 스펙이 작성되어 있는 컨트롤러를 지정
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                // apis에 있는 api 중 특정 경로 지정
                .paths(PathSelectors.ant("/post")).build()
                .apiInfo(apiInfoV2());
        }

        // apiInfo : Swagger UI로 노출할 정보
        private ApiInfo apiInfoV1(){
            return new ApiInfoBuilder()
                .title("BamTol")
                .description("BamTol Blog")
                .version("2.0")
                .build();
        }
        

        // apiInfo : Swagger UI로 노출할 정보
        private ApiInfo apiInfoV3(){
            return new ApiInfoBuilder()
                .title("BamTol")
                .description("BamTol Blog")
                .version("2.0")
                .build();
        }

        // apiInfo : Swagger UI로 노출할 정보
        private ApiInfo apiInfoV2(){
            return new ApiInfoBuilder()
                .title("Test")
                .description("Test Blog")
                .version("2.0")
                .build();
        }
        
}

