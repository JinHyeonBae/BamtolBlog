package com.example.back.repository;

import java.util.List;

import com.example.back.model.Permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>{
    
    public Permission findById(int pid);
}
