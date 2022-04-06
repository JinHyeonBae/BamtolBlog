package com.example.back.repository;

import com.example.back.dto.AuthDto;
import com.example.back.model.user.UserInformation;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer>, InsertCustomRepository {
    
    public void saveSignUpUserInfo(UserInformation dto);
    public UserInformation findByEmail(String email);
    public UserInformation findByNickname(String nickname);

}
