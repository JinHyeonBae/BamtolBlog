package com.example.back.repository;

import java.util.Optional;

import com.example.back.model.SubscribeUser;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeUserRepository extends JpaRepository<SubscribeUser, Integer> {
    
    //id는 문자열로 유지해야한다..?
    @Query("select * from subscribe_user where subscribe_id = :paramUserId")
    // FK로 찾는 명명법 : subscriber_id
    public Optional<SubscribeUser> findBySubscriber_Id(@Param(value = "paramUserId") int SubscriberId);
}
