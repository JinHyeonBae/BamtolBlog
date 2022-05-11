package com.example.back.service;

import java.util.Optional;

import javax.naming.NoPermissionException;

import com.example.back.config.CustomModelMapper;
import com.example.back.dto.PostDto.CreatePostDto;
import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.model.SubscribePost;
import com.example.back.model.SubscribeUser;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserInformation;
import com.example.back.repository.PermissionRepository;
import com.example.back.repository.PostInformationRepository;
import com.example.back.repository.PostPermissionRepository;
import com.example.back.repository.PostsRepository;
import com.example.back.repository.SubscribePostRepository;
import com.example.back.repository.SubscribeUserRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.response.ResponseDto.CreateResponseDto;
import com.example.back.response.ResponseDto.ReadResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.bytebuddy.implementation.bytecode.Throw;


@Service
public class PostService {

    @Autowired  
    PostInformationRepository postInformationRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UserRepository urRepo;

    @Autowired
    UserInformationRepository urInfoRepo;

    @Autowired
    UserPermissionReposotiry urPermitRepo;

    @Autowired
    PostPermissionRepository postPermissionRepo;

    @Autowired
    SubscribeUserRepository subscribeUserRepository;

    @Autowired
    SubscribePostRepository subscribePostRepository;

    @Autowired
    CustomModelMapper customModelMapper;

    @Autowired
    PermissionRepository permissionRepository;

    public CreateResponseDto createPost(CreatePostDto createPostInfo){

        System.out.println("nickname :" + createPostInfo.getNickname());
        UserInformation usersInfo = urInfoRepo.findByNickname(createPostInfo.getNickname()).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        );

        int publisherUserId = usersInfo.getUserId();

        CreateResponseDto createDto = new CreateResponseDto();
        
        if(publisherUserId == createPostInfo.getUserId()){
            System.out.println("권한을 충족하였습니다.");
            try{
                PostInformation cpPostInformation = createPostInfo.PostInfoToEntity();
                
                postsRepository.savePosts(new Posts(publisherUserId));
                postInformationRepository.savePostInformation(cpPostInformation);
                
                // postPermissionRepo.savePostPermission(PostPermission.builder()
                //                                 .permissionId(13)
                //                                 .userId(publisherUserId)
                //                                 .postId()
                //                                 .build()
                //                                 );

                createDto.setStatus(HttpStatus.CREATED);
                createDto.setMessage("포스트가 성공적으로 생성되었습니다.");
            }
            catch(Exception e){
                createDto.setStatus(HttpStatus.valueOf(501));
                createDto.setMessage(e.getMessage());
            }
        }
        else{
            System.out.println("권한이 없습니다.");

            createDto.setStatus(HttpStatus.FORBIDDEN);
            createDto.setMessage("접근 권한이 없습니다.");
        }

        return createDto;
    
    }


    public ReadResponseDto readPost(ReadPostDto body) throws NoPermissionException{

        //userId, postId로 
        Integer userId = body.getUserId();
        Integer postId = body.getPostId();

        PostPermission postPermission = postPermissionRepo.findByUserIdAndPostId(userId, postId);
        //보내려는 정보
        PostInformation postInfo = null;
        ReadResponseDto readDto = null;
                    
        if(postPermission == null){
            postInfo = postInformationRepository.findByPostId(postId);

            try{
                
                boolean Qualification = assignUserRole(userId, postId);
                if(Qualification){
                    // 여기서 그럼 또 userRepo에 접근해서 닉네임을 얻어오나
                    readDto = new ReadResponseDto(HttpStatus.OK, "읽기 요청이 완료되었습니다.", postInfo.getContents(), postInfo.getTitle());

                }
                else{
                    readDto = new ReadResponseDto();
                    readDto.readErrorDto(HttpStatus.FORBIDDEN, "해당 포스트를 볼 권한이 없습니다.");
                }
            }
            catch(Exception e){
                System.out.println("에러 발생.");
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
                
                //throw new NoPermissionException("해당 포스트를 볼 권한이 없습니다.");
                readDto = new ReadResponseDto();
                readDto.readErrorDto(HttpStatus.valueOf(500), e.getMessage());
                
            }
        }
        else{
            postInfo = postInformationRepository.findByPostId(postId);

            if(Role.valueOf(postPermission.getPermissionId()).equals(Role.DOMAIN_SUBSCRIBER)){
                System.out.println("당신의 권한은 도메인 구독자 권한입니다.");
            }
            else if(Role.valueOf(postPermission.getPermissionId()).equals(Role.POST_SUBSCRIBER))
                System.out.println("당신의 권한은 포스트 구독자 권한입니다.");
            else{
                System.out.println("당신의 권한은 퍼블리셔 권한입니다.");
            }
            
            readDto = new ReadResponseDto(HttpStatus.OK, "읽기 요청이 완료되었습니다.", postInfo.getContents(), postInfo.getTitle());
        }

        return readDto;
    }


    public boolean assignUserRole(int userId, int postId){    
        //도메인 구독자인지 확인한다.

        //권한 정보 들고 있다.
        Role userRole = null;

        SubscribeUser subscribeUser = subscribeUserRepository.findById(userId);

        if(subscribeUser !=null){
            userRole = Role.DOMAIN_SUBSCRIBER;
            System.out.println("당신의 권한은 도메인 구독자(블로그 구독자)입니다.");

            postPermissionRepo.savePostPermission(PostPermission.builder()
                                                .permissionId(14)
                                                .userId(userId)
                                                .postId(postId)
                                                .build()
                                                );
            //insert to post_permission 14 
            //return true;
        }
        
        SubscribePost subscribePost = subscribePostRepository.findByUserIdAndPostId(userId, postId); 
        
        if(subscribePost != null){
            userRole = Role.POST_SUBSCRIBER;
            System.out.println("당신의 권한은 포스트 구독자입니다.");
            
            //insert to post_permission 13
            //동일한 데이터가 postPermission에 들어가면 안된다.
            postPermissionRepo.savePostPermission(PostPermission.builder()
                                                .permissionId(13)
                                                .userId(userId)
                                                .postId(postId)
                                                .build()
                                                );
            //return true;
        }

        return checkPostPermission(userRole, postId);
    }

    public boolean checkPostPermission(Role userRole, int postId){

        PostInformation postRole = postInformationRepository.findByPostId(postId);
        System.out.println("PostRole :" + postRole);
        String postLevel = postRole.getDisplayLevel();

        Role postLevelRole = Role.valueOf(postLevel);
       //System.out.println("현재 사용자의 권한은 " + userRole.name());
        //System.out.println(postLevelRole.name());
        // 포스트가 비공개인 것은 어차피 다른 사용자가 누를 수 없도록 만들어야 됨.
        if(postLevelRole.equals(Role.PRIVATE)){
            System.out.println("현재 포스트의 권한 레벨은 private입니다.");
            if(userRole.equals(Role.PUBLISHER)){
                System.out.println("해당 포스트를 볼 자격이 있습니다.");
                return true;
            }
            System.out.println("해당 포스트를 볼 자격이 없습니다.");
            return false;
        }
        else if(postLevelRole.equals(Role.PROTECT)){
            System.out.println("현재 포스트의 권한 레벨은 protect입니다.");
            if(userRole.equals(Role.DOMAIN_SUBSCRIBER) || userRole.equals(Role.POST_SUBSCRIBER) || userRole.equals(Role.PUBLISHER)){
                System.out.println("해당 포스트를 볼 자격이 있습니다.");
                return true;
            }
            System.out.println("해당 포스트를 볼 자격이 없습니다.");
            return false;
        }
        else{
            System.out.println("해당 포스트의 권한 레벨은 public입니다.");
            System.out.println("해당 포스트를 볼 자격이 있습니다.");
            return true;
        }
    }

    
}
