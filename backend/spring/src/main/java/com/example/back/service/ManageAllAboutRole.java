package com.example.back.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.model.SubscribePost;
import com.example.back.model.SubscribeUser;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.Users;
import com.example.back.repository.PostInformationRepository;
import com.example.back.repository.PostPermissionRepository;
import com.example.back.repository.PostsRepository;
import com.example.back.repository.SubscribePostRepository;
import com.example.back.repository.SubscribeUserRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Component
public class ManageAllAboutRole{


    private static final Logger LOGGER = LoggerFactory.getLogger(ManageAllAboutRole.class);
    //@Autowired
    SecurityContext securityContext;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UserInformationRepository uInformationRepository;

    @Autowired
    PostInformationRepository postInformationRepository;

    @Autowired
    PostPermissionRepository postPermissionRepository;

    @Autowired
    SubscribeUserRepository subscribeUserRepository;

    @Autowired
    SubscribePostRepository subscribePostRepository;

    String userRole;

    public void reset(){
        this.userRole = null;
    }
    
    //@Override
    public void addRole() {
        
        // create 시 role 추가
        

    }

    // 포스트에 읽기 요청이 들어왔을 때 호출하는 함수
    // 읽기 요청이 들어왔을 경우, 포스트가 어떤 레벨인지를 알아야 한다.
    // unAuth여도 가능
    //@Override
    public HashMap<String, String> readRole(int postId, int userId) {
        
        LOGGER.info("ENTER IN READROLE!");
        Map<String, String> postAndUsersPermissions = new HashMap<String, String>();
        
        // postInfo가 null인 경우 서버 에러로 처리하자
        PostInformation postInfo = postInformationRepository.findByPostId(postId); //optional로 날려줘야겠네
        
        LOGGER.info("postInfo role입니다 :" + postInfo.getDisplayLevel());

        postAndUsersPermissions.put("postPermissionLevel", postInfo.getDisplayLevel());
        postAndUsersPermissions.put("userPermissionLevel" , readUserRoleRegardingPost(postId, userId));
        
        return (HashMap<String, String>) postAndUsersPermissions;
    }


    //@Override
    public void updateRole(Role role) {
        //Javac는 "capture of ?"라고 하는 익명 유형 변수에 와일드카드 값을 캡처하여 내부적으로 와일드카드 값을 나타냅니다

    }

    //@Override
    public void deleteRole() {
        // TODO Auto-generated method stub
        
    }
    // public일 수도 있고
    // userPermission이 null이 가능
    public boolean isProperAccessRequest(String userPermission, String postPermission){

        System.out.println("UP :" +userPermission);
        System.out.println("PP :" + postPermission);

        postPermission = postPermission.toUpperCase();

        if(postPermission.equals(Role.PUBLIC.getKey()))
            return true;

        // public이 아닌데 userPermission이 null (= 로그인을 하지 않은 사용자)
        if(userPermission == null)
            return false;
        
        // post가 private 이고 user가 publisher가 아닐 때
        if(postPermission.equals(Role.PRIVATE.getKey()) && !userPermission.equals(Role.PUBLISHER.getKey()))
            return false;

        // public이 아닌데도 member라면
        if(userPermission.equals(Role.MEMBER.getKey()))
            return false;

        return true;
    }

    // post에 대한 권한을 확인하는 함수
    // join으로 findUserRoles랑 합칠 수 있을 것 같다.
    private String readUserRoleRegardingPost(int postId, int userId){
        // 근데 권한이 없을수도 있는게 public이면 없을 수 있음

        // post 권한
        Optional<PostPermission> Permission = postPermissionRepository.findByUserIdAndPostId(userId, postId);
        System.out.println(Permission.get().getPermissionId());

        if(Permission.isPresent())
            return Role.valueOf(Permission.get().getPermissionId()).name();
        else
            return null;
    }

    // post 권한을 확인할 수 없을 경우에 subscribe 관련 테이블을 찾는 함수
    @Transactional
    public String findUserRole(int userId, int postId){
        
        Optional<SubscribeUser> subUser = subscribeUserRepository.findBySubscriber_Id(userId);

        //domain subscriber인 경우
    
        subUser.ifPresent((action)->{   
            //userRole은 subscriberUser이다.
            this.saveUserRole("DOMAIN_SUBSCRIBER", userId, postId);
            this.userRole = Role.DOMAIN_SUBSCRIBER.getKey();
        });

        //post subscriber인 경우
        Optional<SubscribePost> subPost =  subscribePostRepository.findById(userId);

        subPost.ifPresent((action)->{
            saveUserRole("POST_SUBSCRIBER", userId, postId);
            this.userRole = Role.POST_SUBSCRIBER.getKey();
        });
        
        // post나 domain 구독자인 경우
        if(this.userRole != null)
            return this.userRole;
        
        Optional<Users> users = userRepository.findById(userId);
        System.out.println("users status:" + users);
        if(!users.isEmpty()){
            this.userRole = Role.MEMBER.name();
            return this.userRole;
        }

        return null;
    }

    
    // foreign key
    public void saveUserRole(String userPermission, int userId, int postId){

        // Posts post = postsRepository.findById(postId);
        // Users user = userRepository.findById(userId).get();
        // postPermission.setUser(user); -> save 는 위반성 에러 남   
        System.out.println("------------"+userPermission+"--------------"); 
        PostPermission postPermission = new PostPermission();
        
        postPermission.setPostId(postId);
        postPermission.setUserId(userId);
        
        postPermission.setPermissionId(Role.valueOf(userPermission).getValue());
        postPermissionRepository.saveAndFlush(postPermission);

    }

}
