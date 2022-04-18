package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.example.back.dto.AuthDto;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.back.config.JPAConfiguration;

@Repository
@Transactional
public class InsertCustomRepositoryImpl implements InsertCustomRepository {
    
    @Autowired
    @Qualifier("entityManagerBean")
    EntityManager entityManager;

    @Override
    @Transactional
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
    @Transactional
    public void saveSignUpUserInfo(Users user) throws SQLException{
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //System.out.println("this");
        
            entityManager.createNativeQuery("Insert into users(created_at, nickname) values(?, ?)")
                        .setParameter(1, formatedNow)
                        .setParameter(2, user.getNickname())
                        .executeUpdate();

        

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUserToken(UserAuth token){
        entityManager.createNativeQuery("Insert into user_auth(token, user_id) values(?,?)")
                    .setParameter(1, token.getToken())
                    .setParameter(2, token.getUserId())
                    .executeUpdate();
    }

    @Override
    @Transactional
    public void savePostPermission(PostPermission permission){
        entityManager.createNativeQuery("Insert into post_permission(permission_id, post_id, user_id) values(?,?,?)")
                    .setParameter(1, permission.getPermissionId())
                    .setParameter(2, permission.getPostId())
                    .setParameter(3, permission.getUserId())
                    .executeUpdate();
    }

    @Override
    @Transactional
    public void savePostInformation(PostInformation postInfo) throws SQLException{
        entityManager.createNativeQuery("Update post_information set contents=?, display_level=?, title=? where user_id = ?")
                    .setParameter(1, postInfo.getContents())
                    .setParameter(2, postInfo.getDisplayLevel())
                    .setParameter(3, postInfo.getTitle())
                    .setParameter(4, postInfo.getUserId())
                    .executeUpdate();
        
    }

    @Override
    @Transactional
    public void savePosts(Posts post) throws SQLException{
        entityManager.createNativeQuery("insert into posts(user_id) values(?)")
                    .setParameter(1, post.getUserId())
                    .executeUpdate();
    }

    
}
