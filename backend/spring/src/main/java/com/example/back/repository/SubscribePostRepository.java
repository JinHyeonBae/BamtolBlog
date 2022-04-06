package com.example.back.repository;

import com.example.back.model.SubscribePost;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscribePostRepository extends JpaRepository<SubscribePost, Integer> {

    @Query("select * from subscribe_post where user_id =? AND post_id=?")
    public SubscribePost findByUserIdAndPostId(int userId, int postId);    

}


