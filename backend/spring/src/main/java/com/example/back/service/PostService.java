package com.example.back.service;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.server.ServerErrorException;

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

    @Autowired
    ManageAllAboutRole roleProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    public CreateResponseDto createPost(CreatePostDto createPostInfo){

        //이건 데이터가 제대로 매핑이 안되었거나 안 온 경우니까 
        UserInformation usersInfo = urInfoRepo.findByNickname(createPostInfo.getNickname()).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        );  

        // Role이 상시적으로 바뀌는데..음..

        int publisherUserId = usersInfo.getUserId();

        CreateResponseDto createDto = new CreateResponseDto();
        
        if(publisherUserId == createPostInfo.getUserId()){
            LOGGER.info("권한을 충족하였습니다.");
            PostInformation cpPostInformation = createPostInfo.PostInfoToEntity();
            
            postsRepository.savePosts(new Posts(publisherUserId));
            postInformationRepository.savePostInformation(cpPostInformation);

            createDto.setStatus(201);
            createDto.setMessage("포스트가 성공적으로 생성되었습니다.");
        }
        else{
            new NoPermissionException("PERMISSION DENIED");
        }

        return createDto;
    }


    public ReadResponseDto readPost(ReadPostDto body) throws NoPermissionException, InternalServerError, AccessDeniedException{

        //userId, postId로 
        Integer userId = body.getUserId();
        Integer postId = body.getPostId();

        // 1차로 getAuthority 확인      
        System.out.println("userId :" + userId);
        System.out.println("postId :" + postId);
        HashMap<String,String> roleMap = roleProcessor.readRole(body);

        if(roleMap.get("userPermissionLevel") == null){
            String userRole = roleProcessor.findUserRole(userId, postId);
            roleMap.put("userPermissionLevel", userRole);
        }
        
        boolean isProperAccess = roleProcessor.isProperAccessRequest(roleMap.get("userPermissionLevel"), roleMap.get("postPermissionLevel"));
        PostInformation postInfo = postInformationRepository.findByPostId(postId);

        if(isProperAccess){
            return new ReadResponseDto(200, "읽기 요청이 완료되었습니다.", postInfo.getContents(), postInfo.getTitle());
        }
        else{
            throw new InternalAuthenticationServiceException("내부 서버 에러");
        }
    }
    
}
