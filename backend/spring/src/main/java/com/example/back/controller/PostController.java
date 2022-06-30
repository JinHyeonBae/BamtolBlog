package com.example.back.controller;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;

import com.example.back.dto.PostDto.CreatePostDto;
import com.example.back.dto.PostDto.DeletePostDto;
import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.response.ResponseDto.CreateResponseDto;
import com.example.back.response.ResponseDto.DeleteResponseDto;
import com.example.back.response.ResponseDto.ReadResponseDto;
import com.example.back.response.ResponseDto.UpdateResponseDto;
import com.example.back.security.JwtProvider;
import com.example.back.service.AuthService;
import com.example.back.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
    public ResponseEntity<CreateResponseDto> createPost(HttpServletRequest request, @RequestBody CreatePostDto body) throws SQLException{

        CreateResponseDto result = postService.createPost(body);
        
        return ResponseEntity.ok().body(result);
    }

    //읽기 요청
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ReadResponseDto> readPost(@RequestHeader HttpHeaders headers, @PathVariable(value="postId") String postId) 
        throws NoPermissionException, InternalServerError, AccessDeniedException, InternalAuthenticationServiceException{
        // 먼저 온 토큰으로 userId를 받는다.
        Integer IntpostId = Integer.valueOf(postId);
        System.out.println("header :" + headers);
        ReadResponseDto readDto = postService.readPost(headers, IntpostId);
        return ResponseEntity.ok().body(readDto);
    }

    // @PostMapping("/posts/{postId}")
    // public ResponseEntity<ReadResponseDto> readPost_post(@RequestHeader HttpHeaders headers, @RequestBody ReadPostDto body) throws NoPermissionException, InternalServerError, AccessDeniedException, InternalAuthenticationServiceException{
    //     // 먼저 온 토큰으로 userId를 받는다.
    //     ReadResponseDto readDto = postService.readPost(body);
    //     return ResponseEntity.ok().body(readDto);
    // }


    //수정 요청
    @PutMapping("posts/{postId}")
    public ResponseEntity<UpdateResponseDto> updatePost(@RequestHeader HttpHeaders headers, @RequestBody UpdatePostDto body, @PathVariable int postId) throws NoPermissionException{
        UpdateResponseDto result = postService.updatePost(body, postId);
        
        return ResponseEntity.ok().body(result);
    }

    //패치 요청
    @PatchMapping("posts/{postId}")
    public void patchPost(@PathVariable int postId){
        
    }

    // 삭제 요청
    @DeleteMapping("posts/{postId}")
    public ResponseEntity<DeleteResponseDto> deletePost(@RequestHeader HttpHeaders headers, @RequestBody DeletePostDto body) throws NoPermissionException{
        System.out.println("삭제 요청 컨트롤러 확인");
        DeleteResponseDto result = postService.deletePost(body);
        
        return ResponseEntity.ok().body(result);
        
    }
    
    
}
