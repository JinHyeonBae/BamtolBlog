package com.example.back.repository;

import com.example.back.model.SubscribePost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscribePostRepository extends JpaRepository<SubscribePost, Integer> {

    public SubscribePost findByUserIdAndPostId(int userId, int postId);    

}


