package com.example.back.repository;

import com.example.back.model.SubscribeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeUserRepository extends JpaRepository<SubscribeUser, Integer> {
    
    public SubscribeUser findById(int userId);
}
