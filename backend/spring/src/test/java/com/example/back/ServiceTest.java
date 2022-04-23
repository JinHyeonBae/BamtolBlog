package com.example.back;

import javax.naming.NoPermissionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.example.back.dto.PostDto.createPostDto;
import com.example.back.dto.PostDto.readPostDto;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserInformation;
import com.example.back.repository.PostInformationRepository;
import com.example.back.repository.PostPermissionRepository;
import com.example.back.repository.PostsRepository;
import com.example.back.repository.SubscribePostRepository;
import com.example.back.repository.SubscribeUserRepository;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.response.ResponseDto.CreateResponseDto;
import com.example.back.response.ResponseDto.ReadResponseDto;
import com.example.back.service.PostService;
import com.example.back.service.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
//@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Autowired
    private ObjectMapper objectMapper;
  
    private UserInformation testUserInfo_1 = new UserInformation();
    private UserInformation testUserInfo_2 = new UserInformation();

    private PostInformation testPostInfo_1 = new PostInformation();
    private PostInformation testPostInfo_2 = new PostInformation();
    private PostInformation testPostInfo_3 = new PostInformation();

    private ArrayList<PostPermission> testPostPermission_1 = new ArrayList<PostPermission>();
    private ArrayList<PostPermission> testPostPermission_2 = new ArrayList<PostPermission>();
    private ArrayList<PostPermission> testPostPermission_3 = new ArrayList<PostPermission>();
    

    @MockBean
    private PostInformationRepository postInfoRepo;

    @MockBean
    private PostsRepository postRepo;

    @InjectMocks
    private PostService postService;

    @MockBean    
    private PostPermissionRepository postPermitRepo;

    @MockBean
    private SubscribeUserRepository subUserRepository;

    @MockBean
    private SubscribePostRepository subPostRepository;

    @MockBean
    private UserInformationRepository urInfoRepo;

    String testUserToken;
    int postId;

    @BeforeEach
    @Test
    public void init(){

        testUserInfo_1 = UserInformation.builder()
                        .name("jane")   
                        .nickname("jane")
                        .userId(22)
                        .email("333@naver.com")
                        .build();


        testUserInfo_2 = UserInformation.builder()
                        .name("helly")   
                        .nickname("hello")
                        .userId(23)
                        .email("343@naver.com")
                        .build();

        int price = 0;
        testPostInfo_1 = PostInformation.builder()
                        .title("테스트1")
                        .contents("testPostInfo_1로 진행하는 테스트입니다.")
                        .displayLevel("public")
                        .price(price)
                        .userId(22)
                        .postId(1)
                        .build();

        // 22번 유저가 1번 포스트에서 도메인 권한 가짐
        testPostPermission_1.add(
            PostPermission.builder()
                            .userId(22)
                            .postId(1)
                            .permissionId(15)
                            .build());

        //22번 유저가 2번 포스터에서 포스트 권한 가짐
        testPostPermission_1.add(
            PostPermission.builder()
                            .userId(23)
                            .postId(1)
                            .permissionId(13)
                            .build());
                            


        testPostInfo_2 = PostInformation.builder()
                        .title("테스트2")
                        .contents("testPostInfo_2로 진행하는 테스트입니다.")
                        .displayLevel("private")
                        .price(price)
                        .userId(23)
                        .postId(2)
                        .build();
        

        testPostPermission_2.add(
            PostPermission.builder()
                        .userId(23)
                        .postId(2)
                        .permissionId(15)
                        .build());
        
        testPostPermission_2.add(
            PostPermission.builder()
                        .userId(22)
                        .postId(2)
                        .permissionId(12)
                        .build());

        price = 500;
        testPostInfo_3 = PostInformation.builder()
                        .title("테스트3")
                        .contents("testPostInfo_3으로 진행하는 테스트입니다.")
                        .displayLevel("private")
                        .price(price)
                        .userId(2)
                        .postId(3)
                        .build();
        

        testPostPermission_3.add(
            PostPermission.builder()
                        .userId(2)
                        .postId(3)
                        .permissionId(15)
                        .build());

        testPostPermission_3.add(
            PostPermission.builder()
                        .userId(22)
                        .postId(3)
                        .permissionId(13)
                        .build());

    }

    @Test
    @Transactional
    @DisplayName("포스트 생성 요청")
    public void createPostServiceTest() throws JsonProcessingException{
        
        //given
        String nickname = testUserInfo_1.getNickname();
        createPostDto cDto = new createPostDto(testPostInfo_1.getContents(), testPostInfo_1.getTitle(),
                                                 testPostInfo_1.getDisplayLevel(), testPostInfo_1.getPrice(), nickname, testPostInfo_1.getUserId());
    
        when(urInfoRepo.findByNickname(nickname)).thenReturn(testUserInfo_1);

        doNothing().when(postRepo).savePosts(new Posts(testPostInfo_1.getPostId()));
        doNothing().when(postInfoRepo).savePostInformation(testPostInfo_1);

        //when
        CreateResponseDto response_publisher = postService.createPost(cDto);

        //then
        Assertions.assertThat(response_publisher.getStatus()).isEqualTo(HttpStatus.CREATED);

        System.out.println("------------------------------------");
        System.out.println("반환 상태 :" + response_publisher.getStatus());
        System.out.println("응답 메시지 :" + response_publisher.getMessage());
        System.out.println("------------------------------------");



        cDto = new createPostDto(testPostInfo_2.getContents(), testPostInfo_2.getTitle(),
        testPostInfo_2.getDisplayLevel(), testPostInfo_2.getPrice(), testUserInfo_1.getNickname(), testPostInfo_2.getUserId());
        
        when(urInfoRepo.findByNickname(testUserInfo_1.getNickname())).thenReturn(testUserInfo_1);

        doNothing().when(postRepo).savePosts(new Posts(testPostInfo_2.getPostId()));
        doNothing().when(postInfoRepo).savePostInformation(testPostInfo_2);

        //when
        CreateResponseDto response_not_publisher = postService.createPost(cDto);


        System.out.println("------------------------------------");
        System.out.println("반환 상태 :" + response_not_publisher.getStatus());
        System.out.println("응답 메시지 :" + response_not_publisher.getMessage());
        System.out.println("------------------------------------");
        
        
    }


    @Test
    @DisplayName("포스트 읽기 요청")
    public void readPostServiceTest() throws JsonProcessingException, NoPermissionException{
        
        //String content = objectMapper.writeValueAsString(testPostInfo_1);
        
        //given (준비)

        // 포스트 id는 db에 들어가서야 정해지므로, 임의로 지정
        
        int readTargetPostId = 2;
        // readPDto는 API에서 받는 정보를 말함
        readPostDto readPDto_havePermission = new readPostDto(testPostInfo_1.getUserId(),1);
        PostPermission postPermit = testPostPermission_1.get(0);

        // 권한이 있는 경우
        when(postPermitRepo.findByUserIdAndPostId(testPostInfo_1.getUserId(),1)).thenReturn(postPermit);
        when(postInfoRepo.findByPostId(postPermit.getPostId())).thenReturn(testPostInfo_1);
        //when (실행)
        ReadResponseDto response_havePermit = postService.readPost(readPDto_havePermission);

        //then (결과)
        Assertions.assertThat(response_havePermit).isNotNull();
        Assertions.assertThat(response_havePermit.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response_havePermit.getMessage()).isEqualTo("읽기 요청이 완료되었습니다.");
        Assertions.assertThat(response_havePermit.getContents()).isEqualTo(testPostInfo_1.getContents());
        Assertions.assertThat(response_havePermit.getTitles()).isEqualTo(testPostInfo_1.getTitle());
                

        System.out.println("------------------------------------");
        System.out.println("상태 코드 :" + response_havePermit.getStatus());
        System.out.println("Message :" + response_havePermit.getMessage());
        System.out.println("title :" + response_havePermit.getTitles());
        System.out.println("contents : "+ response_havePermit.getContents());
        System.out.println("------------------------------------");
        
        
        //권한이 없는 경우
        readPostDto readPDto_NoPermission = new readPostDto(testPostInfo_3.getUserId(),readTargetPostId);
        
        when(postPermitRepo.findByUserIdAndPostId(readTargetPostId, testPostInfo_3.getPostId())).thenReturn(null);
        when(subUserRepository.findById(testPostInfo_3.getUserId())).thenReturn(null);
        when(subPostRepository.findByUserIdAndPostId(testPostInfo_3.getUserId(),readTargetPostId)).thenReturn(null);
        when(postInfoRepo.findByPostId(readTargetPostId)).thenReturn(testPostInfo_1);

        when(postInfoRepo.findByPostId(postPermit.getPostId())).thenReturn(null);

        //when
        ReadResponseDto response_NoPermission = postService.readPost(readPDto_NoPermission);

        Assertions.assertThat(response_NoPermission).isNotNull();
        Assertions.assertThat(response_NoPermission.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);

        System.out.println("------------------------------------");
        System.out.println("상태 코드 :" + response_NoPermission.getStatus());
        System.out.println("에러 Message :" + response_NoPermission.getMessage());
        System.out.println("------------------------------------");
    }
    
}
