package com.example.back.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;


@Entity
@Getter
@Table(name="subscribe_user")
//user가 구독한 블로그 유저들
public class SubscribeUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="subscriber",columnDefinition = "TEXT")
    private String subscriber;
    
    @Column(name="publisher",columnDefinition = "TEXT")
    private String publisher;

    @Column(name="subscribed_date",columnDefinition = "datetime")
    private String subscribeDate;

    @Column(name="expiration_date",columnDefinition = "datetime")
    private String expirationDate;


}
