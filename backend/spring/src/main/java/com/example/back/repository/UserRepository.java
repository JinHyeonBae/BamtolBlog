package com.example.back.repository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import com.example.back.dto.AuthDto;
import com.example.back.dto.UserDto;
import com.example.back.model.Permission;
import com.example.back.model.user.UserPermission;
import com.example.back.model.user.Users;
import com.example.back.repository.CustomRepository.InsertCustomRepository;
import com.example.back.service.Role;

import org.springframework.data.jpa.repository.*;

// 실제 db랑 연결되는 영역

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, InsertCustomRepository{

    //update
    public List<Users> findAll();
    
    public void saveSignUpUserInfo(Users user);
    public Users findById(int id);
    public Users findByEmail(String email);

    // public void saveLoginUserInfo(Users dto);
    // public void saveSignUpUserInfo(Users dto);
}




 
