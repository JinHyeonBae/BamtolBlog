package com.example.back.repository;

import org.springframework.stereotype.Repository;

import com.example.back.model.post.Posts;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
// interface는 static인가?!
public interface PostsRepository extends JpaRepository<Posts, Integer>, InsertCustomRepository{

    public void savePosts(Posts post);
    public Posts findById(int id);
    public Posts findByUserId(int userId);

}
