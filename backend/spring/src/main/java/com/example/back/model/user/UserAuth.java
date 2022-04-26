package com.example.back.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user_auth")
public class UserAuth {


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name="access_token", columnDefinition = "varchar")
    private String accessToken;
    
    @Column(name="refresh_token", columnDefinition = "varchar")
    private String refreshToken;
    

    @Builder
    public UserAuth(int userId, String accessToken, String refreshToken){
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // 연관관계
}
