package com.example.back.controller;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
import javax.websocket.server.PathParam;

import com.example.back.dto.UserDto;
import com.example.back.model.user.Users;
import com.example.back.service.AuthService;
import com.example.back.service.Role;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 컨트롤러를 대표하는 최상단 타이틀
// //@Api(tags = {"Users"})
// @RestController
// public class UserController {
    
//     @Autowired
//     private UserService us;
//     // @Autowired
//     // private AuthService as;
    
//     @PostConstruct
//     @GetMapping("/users/")
//     //@ApiOperation(value = "유저 리스트", notes = "모든 유저의 리스트를 출력")
//     public ResponseEntity<List<UserDto>> getAllUsers(){
        
//         List<UserDto> users = null;
//         try{
//             users = us.findAllUsers();
            
//         }
//         catch(Exception ex){
//             System.out.println(ex.getMessage());
//         }
    
//         return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
//     }

    

// }
