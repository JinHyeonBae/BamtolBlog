package com.example.back.repository;

import java.util.Optional;

import com.example.back.model.post.PostPermission;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPermissionRepository extends JpaRepository<PostPermission, Integer>, InsertCustomRepository{
    
    public Optional<PostPermission> findByUserIdAndPostId(int userId, int postId);

    public void savePostPermission(PostPermission permission, int userId);
    public PostPermission findById(int id);
}
