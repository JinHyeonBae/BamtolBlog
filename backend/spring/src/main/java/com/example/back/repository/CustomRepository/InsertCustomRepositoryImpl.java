package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class InsertCustomRepositoryImpl implements InsertCustomRepository {
    
    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    //겹치는 아이디가 있으면 그것도 에러 처리를 해야한다.
    @Override
    @Modifying
    @Transactional
    public void saveSignUpUserInfo(UserInformation userInfo){
        //entityManager.flush();
        String sql = "Insert into user_information(email, password, nickname, name, user_id) VALUES(?,?,?,?, (SELECT id from users where nickname=?))";
        entityManager
        .createNativeQuery(sql)
                    .setParameter(1, userInfo.getEmail())
                    .setParameter(2, userInfo.getPassword())
                    .setParameter(3, userInfo.getNickname())
                    .setParameter(4, userInfo.getName())
                    .setParameter(5, userInfo.getNickname())
                    .executeUpdate();
        
    }

    @Override
    @Transactional
    public void saveSignUpUserInfo(Users user) throws SQLException{
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("this");
        
        entityManager.createNativeQuery("Insert into users(created_at, nickname) values(?, ?)")
                    .setParameter(1, formatedNow)
                    .setParameter(2, user.getNickname())
                    .executeUpdate();
    

    }

    @Override
    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUserToken(UserAuth token){
        entityManager.createNativeQuery("Insert into user_auth(token, user_id) values(?,?)")
                    .setParameter(1, token.getToken())
                    .setParameter(2, token.getUserId())
                    .executeUpdate();
    }

    @Override
    @Modifying
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
        //entityManager.getTransaction().begin(); //Update

        try{
            entityManager.createNativeQuery("Update post_information set contents=?, display_level=?, title=?, price=? where user_id = ?")
                        .setParameter(1, postInfo.getContents())
                        .setParameter(2, postInfo.getDisplayLevel())
                        .setParameter(3, postInfo.getTitle())
                        .setParameter(4, postInfo.getPrice())
                        .setParameter(5, postInfo.getUserId())
                        .executeUpdate();
            entityManager.flush();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        //entityManager.flush();

    }

    @Override
    @Modifying
    @Transactional
    public void savePosts(Posts post) throws SQLException{
        entityManager.createNativeQuery("insert into posts(user_id) values(?)")
                    .setParameter(1, post.getUserId())
                    .executeUpdate();

    }

    
}
