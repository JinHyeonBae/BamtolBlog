package com.example.back.repository;

import java.util.Optional;

import com.example.back.model.user.UserInformation;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer>, InsertCustomRepository {
    
    void saveSignUpUserInfo(UserInformation dto);
    Optional<UserInformation> findByEmail(String email);
    Optional<UserInformation> findByEmailAndPassword(String email, String username);
    UserInformation findByNickname(String nickname);

}
