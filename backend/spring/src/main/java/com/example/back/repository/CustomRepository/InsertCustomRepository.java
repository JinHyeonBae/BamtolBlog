package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.util.Optional;

import com.example.back.model.SubscribeUser;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InsertCustomRepository {

    void saveSignUpUserInfo(UserInformation userInfo);
    
    //void saveLoginUserInfo(UserInformation dto);
    //void saveUserToken(UserAuth token);
    
    void savePostPermission(PostPermission permission, int userId);

    int savePostInformationAndReturnPostId(PostInformation postInfo) throws SQLException;
    void savePosts(Posts postInfo) throws SQLException;


    Optional<SubscribeUser> findWithSubscriberObject(Users Subscriber);
}
