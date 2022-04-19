package com.example.back.repository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.back.model.user.Users;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.*;

// 실제 db랑 연결되는 영역

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, InsertCustomRepository{

    //update
    public List<Users> findAll();
    
    public void saveSignUpUserInfo(Users user);
    public Users findById(int id);
    public Users findByNickname(String nickname);

    // public void saveLoginUserInfo(Users dto);
    // public void saveSignUpUserInfo(Users dto);
}




 
