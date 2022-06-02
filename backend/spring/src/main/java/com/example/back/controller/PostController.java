package com.example.back.controller;

import java.nio.file.AccessDeniedException;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;

import com.example.back.dto.PostDto.CreatePostDto;
import com.example.back.dto.PostDto.ReadPostDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;


@RestController
public class PostController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;
    
    //쓰기 요청
    @PostMapping("/posts/write")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CreateResponseDto> createPost(HttpServletRequest request, @RequestBody CreatePostDto body){

        CreateResponseDto result = postService.createPost(body);
        
        return ResponseEntity.ok().body(result);
    }

    //읽기 요청
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ReadResponseDto> readPost(@RequestHeader HttpHeaders headers, @RequestBody ReadPostDto body) throws NoPermissionException, InternalServerError, AccessDeniedException{
        // 먼저 온 토큰으로 userId를 받는다.   
        
        ReadResponseDto readDto = postService.readPost(body);

        return ResponseEntity.ok().body(readDto);
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
