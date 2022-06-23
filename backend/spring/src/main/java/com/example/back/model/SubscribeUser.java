package com.example.back.model;

import java.sql.Date;

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

import lombok.Getter;


@Entity
@Getter
@Table(name="subscribe_user")
//user가 구독한 블로그 유저들
public class SubscribeUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", name="subscriber_id", insertable = false, updatable = false)
    private Users subscriber;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", name="publisher_id", insertable = false, updatable = false)
    private Users publisher;

    @Column(name="subscribed_date",columnDefinition = "datetime")
    private Date subscribeDate;

    @Column(name="expiration_date",columnDefinition = "datetime")
    private Date expirationDate;
    
}
