package com.example.back.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.back.service.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "permission")

// 포스트, 현재 유저의 상태

public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="role_comment", columnDefinition = "TEXT")
    private String roleComment;

    @Builder
    public Permission(Role role){
        this.id = role.getValue();
        this.roleComment = role.name();
    }
}
