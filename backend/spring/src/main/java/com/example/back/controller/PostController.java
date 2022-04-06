package com.example.back.controller;

import javax.naming.NoPermissionException;

import com.example.back.dto.PostDto.createPostDto;
import com.example.back.dto.PostDto.readPostDto;
import com.example.back.response.ResponseDto.CreateResponseDto;
import com.example.back.response.ResponseDto.ReadResponseDto;
import com.example.back.security.JwtProvider;
import com.example.back.service.AuthService;
import com.example.back.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ResponseHeader;
import io.swagger.models.Response;

@RestController
public class PostController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;
    

    //읽기 요청
    @PostMapping("@{nickname}/posts/{postId}")
    public ResponseEntity<ReadResponseDto> readPost(@RequestHeader HttpHeaders headers, @RequestBody readPostDto body,
        @PathVariable int postId, @PathVariable String nickname) throws NoPermissionException{
        // 먼저 온 토큰으로 userId를 받는다.   
        
        ReadResponseDto readDto = new ReadResponseDto();

        ResponseEntity<ReadResponseDto> responseEntity = null;
            
        //이 권한 검사는 서비스에서 다 마쳐야 하나..?
            try{
                readDto = postService.readPost(body.getUserId(), postId);
                
                responseEntity = ResponseEntity.status(readDto.getStatus()).body(readDto);
            }
            catch(NoPermissionException e){
                System.out.println(e.getMessage());
                System.out.println("해당 포스트를 볼 권한이 없습니다.");
                //throw new NoPermissionException("해당 포스트를 볼 권한이 없습니다.");
                
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            catch(NullPointerException e){
                System.out.println("해당 포스트를 볼 권한이 없습니다.");
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        
        return responseEntity;
    }

    //쓰기 요청
    @PostMapping("@{nickname}/posts/write")
    public ResponseEntity<CreateResponseDto> createPost(@RequestHeader HttpHeaders headers, 
                                                                  @PathVariable String nickname, @RequestBody createPostDto body){

        System.out.println(headers);
        String token = headers.get("token").get(0);
        System.out.println("token :" + token);
        // token이 valid하다고 가정 
        ResponseEntity<CreateResponseDto> responseEntity = null;

        if(authService.isValidToken(token)){
            System.out.println("정확한 토큰");

            CreateResponseDto result = postService.createPost(body, nickname);
            HttpStatus status = result.getStatus();
            responseEntity = ResponseEntity.status(status).body(result);
            
        }
        else{
            CreateResponseDto createDto = new CreateResponseDto(HttpStatus.UNAUTHORIZED,  "토큰이 유효하지 않습니다.");
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createDto);
        }

        return responseEntity;

    }

    //수정 요청
    @PutMapping("posts/{postId}")
    public void updatePost(@PathVariable int postId){

    }

    //패치 요청
    @PatchMapping("posts/{postId}")
    public void patchPost(@PathVariable int postId){
        
    }

    // 삭제 요청
    @DeleteMapping("posts/{postId}")
    public void deletePost(){
        
    }
    
    
}
