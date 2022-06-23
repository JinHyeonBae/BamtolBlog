package com.example.back;

import static org.mockito.Mockito.when;

import java.util.HashMap;

import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.service.PostService;
import com.example.back.service.ManageAllAboutRole;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class RoleTest {


    @Autowired
    // MockBean으로 하면 실패..
    ManageAllAboutRole manageAllAboutRole;

    @MockBean
    PostService postService;

    ReadPostDto readPostDto;


    private String postLevel;
    private String userLevel;


    @BeforeEach
    public void init(){
        // public & not publisher
        readPostDto = new ReadPostDto();
        readPostDto.setPostId(40);
        readPostDto.setUserId(27);

        // 26 vhtm
    }


    @Test
    public void findRoleTestWithoutPublisher(){

        // DOMAIN_SUBSCRIBER
        String role = manageAllAboutRole.findUserRole(116, 40);
        if(role.equals("DOMAIN_SUBSCRIBER")){
            System.out.println("\nSUCCESS");
        }
        else{
            System.out.print("-------------role name : " + role + "\n");
            System.out.println("FAIL");
        }

        manageAllAboutRole.reset();

        // MEMBER
        String role2 = manageAllAboutRole.findUserRole(102, 40);
        if(role2.equals("MEMBER")){
            System.out.println("SUCCESS");
        }
        else{
            System.out.println("FAIL");
            System.out.print("-------------role name : " + role2 + "\n");
        }
    }

    @Test
    public void readUserRoleTest(){
        System.out.println(this.readPostDto.getPostId());
        HashMap<String, String> map = manageAllAboutRole.readRole(this.readPostDto);
        // null
        System.out.println(map.get("postPermissionLevel"));
        Assertions.assertThat(map.get("postPermissionLevel")).isNotNull();
        Assertions.assertThat(map.get("userPermissionLevel")).isEqualTo(null);
    }


}
