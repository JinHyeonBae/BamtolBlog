package com.example.back.exception;


import java.util.ArrayList;
import java.util.List;

import javax.naming.NoPermissionException;

import com.example.back.response.ErrorCode;
import com.example.back.response.ExceptionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //이거라구..?
public class AroundExceptionHandler{


    //@Autowired 를 빼니까 된다..?
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AroundExceptionHandler.class);

    ExceptionResponse exceptionResponse;

    
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> NullPointerExceptionHandler(NullPointerException e){ // 400
        LOGGER.info("ERROR : "+ e.getMessage());
        
        if(e.getMessage().contains("HEADER")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.HEADER_NULL_POINTER);
            return ResponseEntity.ok().body(response);
        }

        return null;
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class, 
                                InternalAuthenticationServiceException.class })

    // ExceptionResponse(String messages, String error, Integer status)
    public ResponseEntity<ExceptionResponse> UnauthorizeExceptionHandler(Exception e){ // 401
        LOGGER.info("Exeption Error :" + e.getMessage());

        if(e.getMessage().contains("EMAIL")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.INVALID_INPUT_EMAIL);
            return ResponseEntity.ok().body(response);
        }
        else if(e.getMessage().contains("Bad credentials")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.INVALID_INPUT_PASSWORD);
            return ResponseEntity.ok().body(response);
        }
        else if(e.getMessage().contains("INTERNAL")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.INTERNAL_ERROR);
            return ResponseEntity.ok().body(response);
        }
        
        return null;
    }

    @ExceptionHandler(value = {NoPermissionException.class, AccessDeniedException.class})
    public ResponseEntity<ExceptionResponse> ForbiddenExceptionHandler(Exception e){
        LOGGER.info("Exeption Error :" + e.getMessage());

        if(e.getMessage().contains("PERMISSION")){ 
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.PERMISSION_DENIED);
            return ResponseEntity.ok().body(response);
        }

        return null;
    }


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ExceptionResponse> NotFoundExceptionHandler(NotFoundException e){ //404
        LOGGER.info("ERROR :" + e.getMessage());
        return null;
    }



    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity IllegalStateExceptionHandler(IllegalStateException e){ //409
        LOGGER.info("ERROR : "+e.getMessage());

        if(e.getMessage().contains("AND")){
            ExceptionResponse response = ExceptionResponse.of(ErrorCode.DUPLICATE_EMAIN_AND_NICKNAME);
            return ResponseEntity.ok().body(response);
        }
        else{
            if(e.getMessage().contains("EMAIL")){
                ExceptionResponse response = ExceptionResponse.of(ErrorCode.DUPLICATE_EMAIL);
                return ResponseEntity.ok().body(response);
            }
            else if(e.getMessage().contains("NICKNAME")){
                ExceptionResponse response = ExceptionResponse.of(ErrorCode.DUPLICATE_NICKNAME);
                return ResponseEntity.ok().body(response);
            }
        }
        
        return null;
    }




}

