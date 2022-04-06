package com.example.back.repository.CustomRepository;

import java.sql.SQLException;

import com.example.back.dto.AuthDto;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

public interface InsertCustomRepository {

    void saveSignUpUserInfo(UserInformation userInfo);
    void saveSignUpUserInfo(Users user);
    
    //void saveLoginUserInfo(UserInformation dto);
    
    void saveUserToken(UserAuth token);
    
    void savePostPermission(PostPermission permission);

    void savePostInformation(PostInformation postInfo) throws SQLException;
    void savePosts(Posts post) throws SQLException;

    
}
