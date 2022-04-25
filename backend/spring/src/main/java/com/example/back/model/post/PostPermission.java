package com.example.back.model.post;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.back.model.user.Users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="post_permission")
public class PostPermission {
    
    // ID & GeneratedValue => 자동생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId;
    
    @Column(name="post_id")
    private int postId;

    @Column(name="permission_id")
    private int permissionId;

    @Builder
    public PostPermission(int userId, int postId, int permissionId){
        this.userId = userId;
        this.postId = postId;
        this.permissionId = permissionId;
    }


   @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;

   @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Posts post;


}
