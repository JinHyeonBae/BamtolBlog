package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.example.back.dto.AuthDto;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class InsertCustomRepositoryImpl implements InsertCustomRepository {
    
    @Autowired
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void saveSignUpUserInfo(UserInformation userInfo){
        //entityManager.flush();
        entityManager.createNativeQuery("Update user_information set email=?, password=?, nickname=?, name=? WHERE user_id = ?")
                    .setParameter(1, userInfo.getEmail())
                    .setParameter(2, userInfo.getPassword())
                    .setParameter(3, userInfo.getNickname())
                    .setParameter(4, userInfo.getName())
                    .setParameter(5, userInfo.getUserId())
                    .executeUpdate();
    }

    @Override
    public void saveSignUpUserInfo(Users user){
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //System.out.println("this");
        try{
            entityManager.createNativeQuery("Insert into users(created_at, email) values(?, ?)")
                        .setParameter(1, formatedNow)
                        .setParameter(2, user.getEmail())
                        .executeUpdate();

        }
        catch(Exception e){
            System.out.println("saveSignUp에서의 에러 :" + e.getMessage());
        }

    }

    @Override
    public void saveUserToken(UserAuth token){
        entityManager.createNativeQuery("Insert into user_auth(token) values(?)")
                    .setParameter(1, token.getToken())
                    .executeUpdate();
    }

    @Override
    public void savePostPermission(PostPermission permission){
        entityManager.createNativeQuery("Insert into post_permission(permission_id, post_id, user_id) values(?,?,?)")
                    .setParameter(1, permission.getPermissionId())
                    .setParameter(2, permission.getPostId())
                    .setParameter(3, permission.getUserId())
                    .executeUpdate();
    }

    @Override
    public void savePostInformation(PostInformation postInfo) throws SQLException{
        entityManager.createNativeQuery("Update post_information set contents=?, display_level=?, title=? where user_id = ?")
                    .setParameter(1, postInfo.getContents())
                    .setParameter(2, postInfo.getDisplayLevel())
                    .setParameter(3, postInfo.getTitle())
                    .setParameter(4, postInfo.getUserId())
                    .executeUpdate();
        
    }

    @Override
    public void savePosts(Posts post) throws SQLException{
        entityManager.createNativeQuery("insert into posts(user_id) values(?)")
                    .setParameter(1, post.getUserId())
                    .executeUpdate();
    }

    
}
