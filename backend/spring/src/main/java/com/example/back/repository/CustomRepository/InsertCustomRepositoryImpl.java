package com.example.back.repository.CustomRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.example.back.model.SubscribeUser;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;
import com.example.back.repository.UserRepository;

import org.hibernate.boot.spi.InFlightMetadataCollector.EntityTableXref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
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
        
        // 비영속
        Users new_user = new Users();
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
    public void savePostPermission(PostPermission permission, int userId){

    
    }

    @Override
    @Modifying
    @Transactional
    public void savePostInformation(PostInformation postInfo) throws SQLException{
       
        Posts post = new Posts();
        post.setPostInfo(null); //이걸 해줘야하는 이유가 뭘까? 이걸 안 해주면 아직 저장이 되지 않은 걸 사용했다고 뜬다.
        int userId = postInfo.getUserId();
        post.setUserId(userId);
        // 처음 에러는, postInfo의 id가 설정되어있었다. id가 존재하는 것은 곧 이미 영속화되었다고 간주하여, detach 에러가 난다.

        entityManager.persist(post);
        entityManager.flush();

        postInfo.setUserId(post.getUserId());
        postInfo.setPostId(post.getId());
        entityManager.persist(postInfo); // detached entity passed to persist
        
        entityManager.flush();
    }

    @Override
    @Modifying
    @Transactional
    public void savePosts(Posts postInfo) throws SQLException{
        //posts -> id, post_id


    }

    public Optional<SubscribeUser> findWithSubscriberObject(@Param(value = "paramUser") Users Subscriber){
        return null;
    }

    
}
