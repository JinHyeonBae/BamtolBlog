package com.example.back.repository;

import com.example.back.model.post.PostInformation;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostInformationRepository extends JpaRepository<PostInformation, Integer>, InsertCustomRepository {
    
    public PostInformation findByPostId(int postId);


    public void savePostInformation(PostInformation postInfo);

}
