
package com.example.back.repository;

import com.example.back.model.user.UserAuth;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer>, InsertCustomRepository{


    // @Transactional
    // public void saveUserToken(UserAuth token);

    @Query("select * from user_auth where user_id =?")
    public UserAuth findByUserId(int id);

}