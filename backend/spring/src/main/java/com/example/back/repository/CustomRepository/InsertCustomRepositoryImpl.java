package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.text.DateFormat;
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


    public String getCurrentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //겹치는 아이디가 있으면 그것도 에러 처리를 해야한다.
    @Override
    @Modifying
    @Transactional
    public void saveSignUpUserInfo(UserInformation userInfo){

        String nickname = userInfo.getNickname();

        Users new_user = new Users(nickname);
        new_user.setUserInfo(null);
        new_user.setUserAuth(null);
        entityManager.persist(new_user); 

        //UserInformation new_userInfo = userInfo;
        userInfo.setUserId(new_user.getId());
        entityManager.persist(userInfo);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void saveSignUpUserInfo(Users user) throws SQLException{

        String currentTime = this.getCurrentTime();
        entityManager.createNativeQuery("Insert into users(created_at, nickname) values(?, ?)")
                    .setParameter(1, currentTime)
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

        String sql = "INSERT INTO post_permission(permission_id, post_id, user_id) VALUES(?,?,(SELECT id from posts WHERE user_id=?))";
        entityManager.createNativeQuery(sql)
                    .setParameter(1, permission.getPermissionId())
                    .setParameter(2, permission.getUserId())
                    .setParameter(3, permission.getUserId())
                    .executeUpdate();

    }

    @Override
    @Modifying
    @Transactional
    public void savePostInformation(PostInformation postInfo) throws SQLException{
        //entityManager.getTransaction().begin(); //Update

        //
    }

    @Override
    @Modifying
    @Transactional
    public void savePosts(Posts postInfo) throws SQLException{
        //posts -> id, post_id
        entityManager.createNativeQuery("insert into posts(user_id) values(?)")
                    .setParameter(1, postInfo.getUserId())
                    .executeUpdate();

        // 1. 

        // String postInformation_sql = "INSERT INTO post_information(title, contents, display_level, price, user_id, post_id) VALUES (?,?,?,?,?,?)";
        // try{
        //     entityManager.createNativeQuery(postInformation_sql)
        //                 .setParameter(1, postInfo.getTitle())
        //                 .setParameter(2, postInfo.getContents())
        //                 .setParameter(3, postInfo.getDisplayLevel())
        //                 .setParameter(4, postInfo.getPrice())
        //                 .setParameter(5, postInfo.getUserId())
        //                 .setParameter(6, postInfo.getPostId())
        //                 .executeUpdate();
        //     entityManager.flush();
        // }
        // catch(Exception e){
        //     System.out.println(e.getMessage());
        // }
        //entityManager.flush();
        

    }

    
}
