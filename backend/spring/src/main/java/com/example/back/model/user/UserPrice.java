package com.example.back.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name="user_price")
public class UserPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id", insertable=false, updatable=false)
    private int userId;
    
    @Column(name="price")
    private int price;

    
    // 연관관계
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;
   


}
