package com.example.back.repository;


import com.example.back.model.user.UserPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPermissionReposotiry extends JpaRepository<UserPermission, Integer>{

    public UserPermission findById(int id);    
}
