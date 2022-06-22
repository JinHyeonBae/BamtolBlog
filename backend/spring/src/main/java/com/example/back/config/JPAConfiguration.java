package com.example.back.config;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.example.back.model.user.UserInformation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories("com.example.back.repository")
public class JPAConfiguration{


    @Primary  
    @Bean("mainDataSource")  
    @ConfigurationProperties(prefix = "spring.datasource")  
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                                .url("jdbc:mysql://125.134.138.184:3306/bamtolBlog?useSSL=false&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul")
                                .username("bamtol")
                                .password("Bamtol1234!")
                                .build();

                                // .url("jdbc:mysql://localhost:3307/blogTest?useSSL=false&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul")
                                // .username("root")
                                // .password("1234abcd")
                                // .build(); 
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

// 현재 작업 대상을 가져오는 함수
@Component
class Auditor implements AuditorAware<Integer>{

    @Override
	public Optional<Integer> getCurrentAuditor() { // 이건 무슨 함수일까
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}

		UserInformation userPrincipal = (UserInformation) authentication.getPrincipal();

		return Optional.ofNullable(userPrincipal.getId());
	}
}
