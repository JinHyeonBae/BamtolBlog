
package com.example.back.repository;

import com.example.back.dto.AuthDto;
import com.example.back.model.user.UserAuth;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer>, InsertCustomRepository{

    public void saveUserToken(UserAuth token);
    public UserAuth findByUserId(int id);

}