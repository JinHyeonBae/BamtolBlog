package com.example.back.model.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class PostPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="post_id")
    private int postId;
    
    @Column(name="price")
    private int price;

    @Builder
    public PostPrice(int price){
        this.price = price;
    }
}
