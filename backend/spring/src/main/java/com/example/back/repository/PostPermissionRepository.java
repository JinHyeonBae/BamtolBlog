package com.example.back.repository;

import com.example.back.model.post.PostPermission;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPermissionRepository extends JpaRepository<PostPermission, Integer>, InsertCustomRepository{
    
    public void savePostPermission(PostPermission permission);
    public PostPermission findById(int id);
}
