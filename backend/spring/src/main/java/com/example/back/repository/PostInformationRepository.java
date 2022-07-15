package com.example.back.repository;

import java.util.List;
import java.util.Optional;

import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.user.Users;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostInformationRepository extends JpaRepository<PostInformation, Integer>, InsertCustomRepository {
    
    public Optional<PostInformation> findByPostId(int postId);
    public Optional<PostPermission> findByPostIdAndUserId(int postId, int userId);

    public int savePostInformationAndReturnPostId(PostInformation postInfo);


}
