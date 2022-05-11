package com.example.back.exception;


import java.util.ArrayList;
import java.util.List;

import com.example.back.response.ErrorCode;
import com.example.back.response.ExceptionResponse;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //이거라구..?
public class AroundExceptionHandler{


    //@Autowired 를 빼니까 된다..?
    ExceptionResponse exceptionResponse;

    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class, 
                                InternalAuthenticationServiceException.class})

    // ExceptionResponse(String messages, String error, Integer status)
    public ResponseEntity<ExceptionResponse> UnauthorizeExceptionHandler(Exception e){
        System.out.println("Exeption Error :" + e.getMessage());

        if(e.getMessage().contains("EMAIL")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.EMAIL_INPUT_INVALID);
            return ResponseEntity.ok().body(response);
        }
        else if(e.getMessage().contains("Bad credentials")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.PASSWORD_INPUT_INVALID);
            return ResponseEntity.ok().body(response);
        }
        
        return null;
    }


    @GetMapping("/not-found")
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ExceptionResponse> NotFoundExceptionHandler(NotFoundException e){
        System.out.println("ERROR :" + e.getMessage());
        return null;
    }


    @GetMapping("/null-pointer")
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> NullPointerExceptionHandler(NullPointerException e){
        System.out.println("ERROR : "+ e.getMessage());
        
        if(e.getMessage().contains("HEADER")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.HEADER_NULL_POINTER);
            return ResponseEntity.ok().body(response);
        }

        return null;
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity IllegalStateExceptionHandler(IllegalStateException e){
        System.out.println("ERROR : "+e.getMessage());

        if(e.getMessage().contains("AND")){
            List<ExceptionResponse> exceptionList = new ArrayList<>();

            exceptionList = ExceptionResponse.more(ErrorCode.EMAIL_DUPLICATION, ErrorCode.NICKNAME_DUPLICATION);
            return ResponseEntity.ok().body(exceptionList);
        }
        else{
            if(e.getMessage().contains("EMAIL")){
                ExceptionResponse response = ExceptionResponse.of(ErrorCode.EMAIL_DUPLICATION);
                return ResponseEntity.ok().body(response);
            }
            else if(e.getMessage().contains("NICKNAME")){
                ExceptionResponse response = ExceptionResponse.of(ErrorCode.NICKNAME_DUPLICATION);
                return ResponseEntity.ok().body(response);
            }
        }
        
        return null;
    }




}

