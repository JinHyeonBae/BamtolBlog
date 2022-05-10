package com.example.back.model;

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

import com.example.back.model.post.Posts;
import com.example.back.model.user.Users;

import lombok.Getter;

@Entity
@Getter
@Table(name="subscribe_post")
public class SubscribePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name="post_id")
    private int postId;

    @Column(name="subscribed_date", columnDefinition = "datetime")
    private String subscribedDate;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Posts post;


}
