package com.example.back;

import com.example.back.controller.PostController;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.Posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest
public class PostTest {

    Posts testPost = new Posts();
    PostInformation testPostInfo = new PostInformation();


	@Mock
	PostController postController;



    
    @Test
    public void createPostTest(){
        testPostInfo.setTitle("테스트 제목");
        testPostInfo.setContents("테스트 셋입니다.");
        testPostInfo.setPrice(300);
        testPostInfo.setDisplayLevel("public");
        testPostInfo.setUserId(71);

        if(testPostInfo.getPrice() != 0)  // 유료이다.
            testPostInfo.setIsCharged(1);
        else // 무료이다.
            testPostInfo.setIsCharged(0);


        HttpHeaders header = new HttpHeaders();


    }

    @Test
    public void readPostTest(){



    }

    
}
