package com.example.back.service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NoPermissionException;

import com.example.back.config.CustomModelMapper;
import com.example.back.dto.PostDto.CreatePostDto;
import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.exception.ErrorCode;
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
import com.example.back.response.ResponseDto.CreateResponseDto;
import com.example.back.response.ResponseDto.DeleteResponseDto;
import com.example.back.response.ResponseDto.ReadResponseDto;
import com.example.back.response.ResponseDto.UpdateResponseDto;
import com.example.back.security.JwtProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;


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

    //private PostInfoMapper postInfoMapper


    // TODO : DTO에서 userId를 추출, 

    private int getUserIdFromJWT(String token){

        return jwtProvider.getUserIdFromJWT(token);
    }

    private String getTokenFronCookie(HttpHeaders headers){
        
        List<String> extractToken = jwtProvider.resolveToken(headers);
        
        if(extractToken.isEmpty())
            return null;

        String token = jwtProvider.parseJwtInsideCookie(extractToken.get(0));
        System.out.print("TOKEN    :" + token);
        return token;
    }


    @Transactional
    public CreateResponseDto createPost(HttpHeaders header, CreatePostDto createPostInfo) throws SQLException, NoPermissionException{

        // throw가 발생할 수 있는 경우는 두 가지.
        // userId가 아예 안 왔거나, 매핑이 잘못 됐거나, 아예 존재하지 않는 ID거나, 아니라면 로그인하지 않은 사람

        String token = getTokenFronCookie(header);
        int userId = -1;
        
        if(token == null) {
            LOGGER.info("비회원입니다.");
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }
        else userId = getUserIdFromJWT(token);

       
        Users users = urRepo.findById(userId).orElseThrow(()->
            new NullPointerException("NICKNAME NULL")
        ); 
        
        int publisherUserId = users.getId();

        // member인지를 확인해야한다.
        CreateResponseDto createDto = new CreateResponseDto();

        // 현재 로그인한 게 중요한 거다...
        // user가 있는 경우는 즉, 멤버인 경우이므로 if를 안 써줘도 되네...
        if(publisherUserId == userId){

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
            new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }

        return createDto;
    }

    // token에서 id를 뽑아내는 식으로 하는 게 좋을듯
    // Unknown column 'subscribeu0_.publisher_id' in 'field list'
    public ReadResponseDto readPost(HttpHeaders header, int postId) throws NoPermissionException, InternalServerError, AccessDeniedException, NoSuchElementException{


        String token = getTokenFronCookie(header);
        int userId = -1;
        
        if(token == null) LOGGER.info("비회원입니다.");
        else userId = getUserIdFromJWT(token);

       
        HashMap<String,String> roleMap = roleProcessor.readRole(postId, userId);

                
        boolean isProperAccess = roleProcessor.isProperAccessRequest(roleMap.get("userPermissionLevel"), roleMap.get("postPermissionLevel"));
        PostInformation postInfo = postInformationRepository.findByPostId(postId).orElseThrow(()->{
            throw new NoSuchElementException("POST NOT FOUND");
        });

        if(isProperAccess){
            return new ReadResponseDto(200, "읽기 요청이 완료되었습니다.", postInfo.getContents(), postInfo.getTitle());
        }
        else{
            //false 일 경우는 권한이 없는 경우
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }
    
    }

    public UpdateResponseDto updatePost(HttpHeaders header, UpdatePostDto body, int postId) throws NoPermissionException, NoSuchElementException{

        String token = getTokenFronCookie(header);
        int userId = -1;
        
        if(token == null) {
            LOGGER.info("비회원입니다.");
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }
        else userId = getUserIdFromJWT(token);

        System.out.println("p :" + postId);

        Posts post = postsRepository.findById(postId).orElseThrow(()->
            new NoSuchElementException("POST NOT FOUND")
        );


        int publisherUserId = post.getUserId();

        UpdateResponseDto updateDto = new UpdateResponseDto();
        
        if(publisherUserId == userId){

            LOGGER.info("수정 권한을 충족하였습니다.");

            PostInformation postInfo = postInformationRepository.findByPostId(postId).get();
            
            postInfo.setTitle(body.getTitle());
            postInfo.setContents(body.getContents());
            postInfo.setDisplayLevel(body.getDisplayLevel());
            postInfo.setPrice(body.getPrice());

            //customModelMapper.strictMapper().map(body, postInfo);
            //postInfo = PostInfoMapper.INSTANCE.updateDtoToPostInfoEntity(body);
            postInformationRepository.save(postInfo);

            updateDto.setStatus(200);
            updateDto.setMessage("수정 요청이 완료되었습니다.");
            
        }
        else{
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }

        return updateDto;
    }
    

    public DeleteResponseDto deletePost(HttpHeaders header, int postId) throws NoPermissionException, NullPointerException, NoSuchElementException{

        String token = getTokenFronCookie(header);
        int userId = -1;
        
        if(token == null) {
            LOGGER.info("비회원입니다.");
            throw new NoPermissionException(ErrorCode.PERMISSION_DENIED.name());
        }
        else userId = getUserIdFromJWT(token);

        //  내부 문제여야 하나, 아니면 그냥 NULL인가?
        LOGGER.info("postID :" + postId);
        Posts post = postsRepository.findById(postId).orElseThrow(()->
            new NoSuchElementException("POST NOT FOUND")
        );

        int publisherUserId = post.getUserId();

        DeleteResponseDto deleteDto = new DeleteResponseDto();
        
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
