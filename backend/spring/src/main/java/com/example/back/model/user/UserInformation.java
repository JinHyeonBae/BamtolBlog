package com.example.back.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_information")
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
    private String updatedAt;

    @Column(name="last_login", columnDefinition = "datetime")
    private String last_login;

    @Builder
    public UserInformation(String email, String password, String name, String nickname, int userId){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.userId = userId;
    }

}
