package com.example.back.model.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name="post_public")
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
    private String createdAt;

    @Column(name="comments", columnDefinition = "text")
    private String comments;

}


