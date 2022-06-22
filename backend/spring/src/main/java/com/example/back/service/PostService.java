package com.example.back.service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import javax.naming.NoPermissionException;

import com.example.back.config.CustomModelMapper;
import com.example.back.config.GenericMapper;
import com.example.back.dto.PostDto.CreatePostDto;
import com.example.back.dto.PostDto.DeletePostDto;
import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.model.SubscribePost;
import com.example.back.model.SubscribeUser;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserInformation;
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

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContext;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private GenericMapper<?, ?> mapper = Mappers.getMapper(GenericMapper.class);

    @Transactional
    public CreateResponseDto createPost(CreatePostDto createPostInfo) throws SQLException{

        //이건 데이터가 제대로 매핑이 안되었거나 안 온 경우니까 
        Users users = urRepo.findById(createPostInfo.getUserId()).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        );  

        // Role이 상시적으로 바뀌는데..음..

        int publisherUserId = users.getId();

        CreateResponseDto createDto = new CreateResponseDto();
        
        if(publisherUserId == createPostInfo.getUserId()){

            LOGGER.info("생성 권한을 충족하였습니다.");

            PostInformation newPostInfo = new PostInformation();
        
            newPostInfo.setUserId(users.getId());
            
            customModelMapper.strictMapper().map(createPostInfo, newPostInfo);
            
            newPostInfo.setId(null);
            postInformationRepository.savePostInformation(newPostInfo);

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
        HashMap<String,String> roleMap = roleProcessor.readRole(body);

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

        Users users = urRepo.findById(body.getUserId()).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        );  

        int publisherUserId = users.getId();

        UpdateResponseDto updateDto = new UpdateResponseDto();
        
        if(publisherUserId == userId){

            LOGGER.info("수정 권한을 충족하였습니다.");

            PostInformation postInfo = postInformationRepository.findByPostId(postId);
            
            mapper.updateCustomerFromDto(body, postInfo);
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

        int publisherUserId = users.getId();

        DeleteResponseDto deleteDto = new DeleteResponseDto();
        
        if(publisherUserId == userId){

            LOGGER.info("삭제 권한을 충족하였습니다.");
            Posts post = postsRepository.findById(postId);

            postsRepository.delete(post);
            deleteDto.setStatus(204);
            deleteDto.setMessage("삭제 요청이 완료되었습니다.");
        }
        else{
             //false 일 경우는 권한이 없는 경우
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }

        return deleteDto;
    }   
    

}
