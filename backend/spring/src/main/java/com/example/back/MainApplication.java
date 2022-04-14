package com.example.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties

@EntityScan(basePackages = {"com.example.back.model"})
@ComponentScan(basePackages = {"com.example.back"})
@ConfigurationPropertiesScan(basePackages = {"com.example.back.config"})
@EnableJpaRepositories(basePackages = {"com.example.back.repository"})
public class MainApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(MainApplication.class, args);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	

}
