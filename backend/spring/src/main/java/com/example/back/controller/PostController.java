package com.example.back.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
    @ApiOperation(value="포스트 생성", notes = "포스트를 생성할 때 요청 규격입니다.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "포스트 생성 요청 성공"),
        @ApiResponse(code = 403, message = "포스트를 생성할 권한이 없음"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CreateResponseDto> createPost(HttpServletRequest request, @RequestBody CreatePostDto body){

        ResponseEntity<CreateResponseDto> responseEntity;

        System.out.println("Header :" + request.getCookies());
        String token = request.getCookies()[0].toString();

        if(jwtProvider.validateToken(token)){
            System.out.println("정확한 토큰");

            CreateResponseDto result = postService.createPost(body);
            HttpStatus status = result.getStatus();
            
            responseEntity = ResponseEntity.status(status).body(result);
            
        }
        else{
            CreateResponseDto createDto = new CreateResponseDto(HttpStatus.FORBIDDEN,  "토큰이 유효하지 않습니다.", 0);
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(createDto);
        }

        return responseEntity;
    }

    //읽기 요청
    @GetMapping("/posts/{postId}")
    @ApiOperation(value="포스트 읽기 요청", notes = "포스트 읽기 요청을 할 때의 규격입니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "포스트 읽기 요청 성공"),
        @ApiResponse(code = 403, message = "해당 포스트를 볼 권한이 없음"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ReadResponseDto> readPost(@RequestHeader HttpHeaders headers, @RequestBody ReadPostDto body) throws NoPermissionException{
        // 먼저 온 토큰으로 userId를 받는다.   
        
        ReadResponseDto readDto = new ReadResponseDto();

        ResponseEntity<ReadResponseDto> responseEntity = null;
            
        //이 권한 검사는 서비스에서 다 마쳐야 하나..?
            try{
                readDto = postService.readPost(body);
                
                responseEntity = ResponseEntity.status(readDto.getStatus()).body(readDto);
            }
            catch(NoPermissionException e){
                System.out.println(e.getMessage());
                System.out.println("해당 포스트를 볼 권한이 없습니다.");
                //throw new NoPermissionException("해당 포스트를 볼 권한이 없습니다.");
                
                responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            catch(NullPointerException e){
                System.out.println("해당 포스트를 볼 권한이 없습니다.");
                responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
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
