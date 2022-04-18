package com.example.back.model.user;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//DAO
@Entity
@Getter
@NoArgsConstructor
@Table(name = "Users")
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // 사용자의 고유 넘버, 구독자 고유 넘버, 사용자 구독 레벨, 

    @Column(name="created_at", columnDefinition = "datetime")
    private String created_at;

    @Column(name="nickname")
    private String nickname;

    @Builder
    // entity -> dto
    public Users(String nickname){
        this.nickname = nickname;
    }

    public Users(int id, String nickname){
        this.id = id;
        this.nickname = nickname;
    }

}
