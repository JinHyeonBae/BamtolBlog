package com.example.back.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_permission")

public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="user_id")
    private int userId;

    @Column(name="permission_id")
    private int permissionId;

    @Column(name="post_permission_id")
    private int postPermissionId;

    @Builder
    public UserPermission(int uid, int permissionId, int postPermissionId){
        this.userId = uid;
        this.permissionId = permissionId;
        this.postPermissionId = postPermissionId;
    }
}
