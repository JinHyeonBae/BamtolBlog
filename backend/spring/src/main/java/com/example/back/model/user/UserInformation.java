package com.example.back.model.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name="user_information")
@EntityListeners(AuditingEntityListener.class) 
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name="email", columnDefinition = "TEXT")
    private String email;

    @Column(name="password", columnDefinition = "TEXT")
    private String password;

    @Column(name="name", columnDefinition = "TEXT")
    private String name;
    
    @Column(name = "nickname", columnDefinition = "TEXT")
    private String nickname;

    @Column(name = "updated_at", columnDefinition = "datetime")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name="last_login", columnDefinition = "datetime")
    @LastModifiedDate
    private LocalDateTime lastLogin;

    @Builder
    public UserInformation(String email, String password, String name, String nickname, int userId){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.userId = userId;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;

    // 연관관계

}
