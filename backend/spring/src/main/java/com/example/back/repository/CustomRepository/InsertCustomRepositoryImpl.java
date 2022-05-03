package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
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

        //영속성 컨텍스트는 데이터베이스와 애플리케이션 사이에서 객체를 저장하는 기법
        String nickname = userInfo.getNickname();
        // 비영속
        Users new_user = new Users(nickname);
        new_user.setUserAuth(null);
        new_user.setUserInfo(null);

        // 영속
        entityManager.persist(new_user); 

        //UserInformation new_userInfo = userInfo;
        userInfo.setUserId(new_user.getId());
        entityManager.persist(userInfo);
        entityManager.flush();
    }

    // @Override
    // @Modifying
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // public void saveUserToken(UserAuth token){
    //     entityManager.createNativeQuery("Insert into user_auth(token, user_id) values(?,?)")
    //                 .setParameter(1, token.getToken())
    //                 .setParameter(2, token.getUserId())
    //                 .executeUpdate();
    // }

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
        Posts post = new Posts();
        post.setPostInfo(null);
        post.setPostPermit(null);
        entityManager.persist(post);
        //여기서 user의 아이디는 어떻게 가져오는 거임
        int postId = post.getId();
        postInfo.setPostId(postId);
        entityManager.persist(postInfo);   
    }

    @Override
    @Modifying
    @Transactional
    public void savePosts(Posts postInfo) throws SQLException{
        //posts -> id, post_id


    }

    
}
