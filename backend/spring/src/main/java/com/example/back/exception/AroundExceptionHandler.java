package com.example.back.exception;

import java.util.Map;

import javax.naming.AuthenticationException;

import com.example.back.response.ErrorCode;
import com.example.back.response.ExceptionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AroundExceptionHandler{


    //@Autowired 를 빼니까 된다..?
    ExceptionResponse exceptionResponse;

    @GetMapping("/invalid-input")
    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class, 
                                InternalAuthenticationServiceException.class})
    // ExceptionResponse(String messages, String error, Integer status)
    public ResponseEntity<ExceptionResponse> UnauthorizeExceptionHandler(Exception e){
        System.out.println("Exeption Error :" + e.getMessage());
        //String erorrMsg = e.getCause();

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
        System.out.println("Exeption Error :" + e.getMessage());
        return null;
    }


    @GetMapping("/null-pointer")
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> NullPointerExceptionHandler(NullPointerException e){
        System.out.println("NULL POINTER");
        return null;
    }

    @GetMapping("/illgegal-statement")
    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<ExceptionResponse> IllegalStateExceptionHandler(IllegalStateException e){
        System.out.println("ERROR : "+e.getMessage());

        if(e.getMessage().contains("EMAIL")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.EMAIL_DUPLICATION);
            return ResponseEntity.ok().body(response);
        }
        else if(e.getMessage().contains("NICKNAME")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.NICKNAME_DUPLICATION);
            return ResponseEntity.ok().body(response);
        }
        
        return null;

    }



}

