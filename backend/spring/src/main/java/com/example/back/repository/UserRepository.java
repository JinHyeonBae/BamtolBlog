package com.example.back.repository;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

import com.example.back.model.user.Users;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.*;

// 실제 db랑 연결되는 영역

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, InsertCustomRepository{

    //update
    public List<Users> findAll();
    
    public Optional<Users> findById(int id);

    default Users getUser(int id){
        return findById(id).orElseThrow(
            () -> new NotFoundException("요청한 유저 정보를 찾을 수 없습니다.")
        );
    }

    // public void saveLoginUserInfo(Users dto);
    // public void saveSignUpUserInfo(Users dto);
}




 
