package com.example.back.config;

import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.example.back.repository")
public class JPAConfiguration{
    


    @Primary  
    @Bean("mainDataSource")  
    @ConfigurationProperties(prefix = "spring.datasource")  
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                                .url("jdbc:mysql://localhost:3307/blogTest?useSSL=false&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul")
                                
                                .password("ㅔㅁㄴㄴ")
                                .username("ㄴㄴㄹㅇ")
                                .build();  
    }  


    @Primary  
    @Bean(name = "entityManagerFactory")  
    public LocalContainerEntityManagerFactoryBean entityManager() {  
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();  
        em.setDataSource(dataSource());  
        em.setPackagesToScan(new String[]{"com.example.back.model"});  

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        //hibernateJpaVendorAdapter.setDatabase(MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);  

        return em;  
    }  

    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

}
