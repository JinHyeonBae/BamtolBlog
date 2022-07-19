package com.example.back.model.post;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.back.model.user.Users;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Entity
@Getter
@Table(name="post_public")
@EntityListeners(AuditingEntityListener.class) 
public class PostPublic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="type", columnDefinition = "TEXT")
    private String type;

    @Column(name="title", columnDefinition = "TEXT")
    private String title;

    @Column(name="user_id")
    private int userId;
    
    @Column(name="contents", columnDefinition = "TEXT")
    private String contents;

    @Column(name="created_at",columnDefinition = "datetime")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="comments", columnDefinition = "text")
    private String comments;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;

}


