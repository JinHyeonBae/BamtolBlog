package com.example.back.service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.naming.NoPermissionException;

import com.example.back.config.CustomModelMapper;
import com.example.back.config.GenericMapper;
import com.example.back.config.PostInfoMapper;
import com.example.back.dto.PostDto.CreatePostDto;
import com.example.back.dto.PostDto.DeletePostDto;
import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.Posts;
import com.example.back.model.user.Users;
import com.example.back.repository.PermissionRepository;
import com.example.back.repository.PostInformationRepository;
import com.example.back.repository.PostPermissionRepository;
import com.example.back.repository.PostsRepository;
import com.example.back.repository.SubscribePostRepository;
import com.example.back.repository.SubscribeUserRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserPermissionReposotiry;
import com.example.back.repository.UserRepository;
import com.example.back.response.ErrorCode;
import com.example.back.response.ResponseDto.CreateResponseDto;
import com.example.back.response.ResponseDto.DeleteResponseDto;
import com.example.back.response.ResponseDto.ReadResponseDto;
import com.example.back.response.ResponseDto.UpdateResponseDto;
import com.example.back.security.JwtProvider;

import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    JwtProvider jwtProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private PostInfoMapper postInfoMapper;

    @Transactional
    public CreateResponseDto createPost(CreatePostDto createPostInfo) throws SQLException{

        // throw가 발생할 수 있는 경우는 두 가지.
        // userId가 아예 안 왔거나, 매핑이 잘못 됐거나, 아예 존재하지 않는 ID거나, 아니라면 로그인하지 않은 사람
        Users users = urRepo.findById(createPostInfo.getUserId()).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        ); 
        
        int publisherUserId = users.getId();

        // member인지를 확인해야한다.
        CreateResponseDto createDto = new CreateResponseDto();

        // 현재 로그인한 게 중요한 거다...
        // user가 있는 경우는 즉, 멤버인 경우이므로 if를 안 써줘도 되네...
        if(publisherUserId == createPostInfo.getUserId()){

            LOGGER.info("생성 권한을 충족하였습니다.");

            PostInformation newPostInfo = new PostInformation();
        
            newPostInfo.setUserId(users.getId());
            customModelMapper.strictMapper().map(createPostInfo, newPostInfo);
        
            newPostInfo.setId(null);

            int postId = postInformationRepository.savePostInformationAndReturnPostId(newPostInfo);
            roleProcessor.saveUserRole("PUBLISHER", users.getId(), postId);

            createDto.setStatus(201);
            createDto.setMessage("포스트가 성공적으로 생성되었습니다.");
            createDto.setPostId(postId);
        }
        else{
            new NoPermissionException("PERMISSION DENIED");
        }

        return createDto;
    }

    // token에서 id를 뽑아내는 식으로 하는 게 좋을듯
    public ReadResponseDto readPost(HttpHeaders header, int postId) throws NoPermissionException, InternalServerError, AccessDeniedException{

        
        System.out.println("header at Service :" + header);
        List<String> extractToken = jwtProvider.resolveToken(header);

        String token = jwtProvider.parseJwtInsideCookie(extractToken.get(0));

        int userId = jwtProvider.getUserIdFromJWT(token);

        HashMap<String,String> roleMap = roleProcessor.readRole(postId, userId);
        
        if(roleMap.get("userPermissionLevel") == null){
            String userRole = roleProcessor.findUserRole(userId, postId);
            // userRole이 비로그인인 경우 null일 수 있다.
            System.out.println("user :" + userRole);
            roleMap.put("userPermissionLevel", userRole);
        }
        
        boolean isProperAccess = roleProcessor.isProperAccessRequest(roleMap.get("userPermissionLevel"), roleMap.get("postPermissionLevel"));
        PostInformation postInfo = postInformationRepository.findByPostId(postId);

        if(isProperAccess){
            return new ReadResponseDto(200, "읽기 요청이 완료되었습니다.", postInfo.getContents(), postInfo.getTitle());
        }
        else{
            //false 일 경우는 권한이 없는 경우
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }
    
    }

    public UpdateResponseDto updatePost(UpdatePostDto body, int postId) throws NoPermissionException{

        int userId = body.getUserId();

        System.out.println(postId);

        // Users users = urRepo.findById(body.getUserId()).orElseThrow(()->
        //     new NullPointerException("NICKNAME NULL")
        // );  

        Posts post = postsRepository.findById(postId);
        int publisherUserId = post.getUserId();

        UpdateResponseDto updateDto = new UpdateResponseDto();
        
        if(publisherUserId == userId){

            LOGGER.info("수정 권한을 충족하였습니다.");

            PostInformation postInfo = postInformationRepository.findByPostId(postId);
            
            postInfo = postInfoMapper.updateDtoToPostInfoEntity(body);
            postInformationRepository.save(postInfo);

            updateDto.setStatus(200);
            updateDto.setMessage("수정 요청이 완료되었습니다.");
            
        }
        else{
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }

        return updateDto;

    }
    
    

    public DeleteResponseDto deletePost(DeletePostDto body) throws NoPermissionException{

        int userId = body.getUserId();
        int postId = body.getPostId();

        Users users = urRepo.findById(body.getUserId()).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        );  

        Posts post = postsRepository.findById(postId);
        int publisherUserId = post.getUserId();

        DeleteResponseDto deleteDto = new DeleteResponseDto();
        
        // 
        if(publisherUserId == userId){

            postsRepository.delete(post);
            deleteDto.setStatus(200);
            deleteDto.setMessage("삭제 요청이 완료되었습니다.");
        }
        else{
             //false 일 경우는 권한이 없는 경우
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }

        return deleteDto;
    }   
    

}
