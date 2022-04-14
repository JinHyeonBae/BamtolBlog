package com.example.back;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.back.config.CustomModelMapper;
import com.example.back.controller.AuthController;
import com.example.back.controller.PostController;
import com.example.back.dto.AuthDto;
import com.example.back.dto.AuthDto.LoginDto;
import com.example.back.dto.AuthDto.SignUpDto;
import com.example.back.dto.PostDto.postInformationDto;
import com.example.back.model.post.Posts;
import com.example.back.model.user.UserAuth;
import com.example.back.model.user.UserInformation;
import com.example.back.model.user.Users;
import com.example.back.repository.UserAuthRepository;
import com.example.back.repository.UserInformationRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.Role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class MainApplicationTest {

	UserRepository urRepository;
	UserInformationRepository urInfoRepo;
	UserAuthRepository urAuthRepo;

	@Autowired
	AuthController authController;

	@Autowired
	PostController postController;

	@Autowired
	CustomModelMapper customModelMapper;

	UserInformation testUser_1;
	UserInformation testUser_2;

	UserInformation failUser_1;
	UserInformation failUser_2;	


	@BeforeEach
	public void setUp(){
		testUser_1 = UserInformation.builder()
									.email("111@naver.com")
									.password("111")
									.name("bear")
									.nickname("helly")
									.build();
		
		
		testUser_2 = UserInformation.builder()
									.email("222@naver.com")
									.password("bbb")
									.name("jane")
									.nickname("jane")
									.build();

		// nickname이 겹치는 경우
		failUser_1 = UserInformation.builder()
									.email("333@naver.com")
									.password("bbb")
									.name("jane")
									.nickname("jane")
									.build();
		
		// 이메일이 겹치는 경우
		failUser_2 = UserInformation.builder()
									.email("222@naver.com")
									.password("bbb")
									.name("jane")
									.nickname("jane")
									.build();
	}

	// 성공
	// 문제 발견. jane이라는 닉네임은 유니크한데, user_id 때문에 데이터가 user 테이블에 먼저 들어가야하므로 들어간 후에 유효성 검사를 하는 것
	@Test
	public void SignUpTest(){

		ModelMapper modelMapper = customModelMapper.strictMapper();

		System.out.println("성공하는 구간입니다.");
		System.out.println("-------------------------------------");
		SignUpDto test1Dto = modelMapper.map(testUser_1, SignUpDto.class);
		
		HttpStatus status = authController.signUp(test1Dto);
		assertEquals(HttpStatus.CREATED, status);


		AuthDto.SignUpDto signUp2Dto = modelMapper.map(testUser_2,AuthDto.SignUpDto.class);
		System.out.println(signUp2Dto.getEmail());
		status = authController.signUp(signUp2Dto);
		assertEquals(HttpStatus.CREATED, status);

		System.out.println("-------------------------------------");

		System.out.println("실패하는 구간입니다.");
		System.out.println("-------------------------------------");

		AuthDto.SignUpDto test3Dto = modelMapper.map(failUser_1, AuthDto.SignUpDto.class);
		System.out.println(test3Dto.getEmail());
		status = authController.signUp(test3Dto);
		assertEquals(HttpStatus.valueOf(409), status);


		AuthDto.SignUpDto signUp4Dto = modelMapper.map(failUser_2,AuthDto.SignUpDto.class);
		System.out.println(signUp4Dto.getEmail());
		status = authController.signUp(signUp4Dto);
		assertEquals(HttpStatus.valueOf(409), status);

		System.out.println("-------------------------------------");
		
	}

	// //성공
	// @Test
	// public void LoginTest(){

	// 	ModelMapper modelMapper = customModelMapper.strictMapper();
		
	// 	System.out.println("login" + modelMapper);

	// 	LoginDto signUp1Dto = modelMapper.map(testUser_1, AuthDto.LoginDto.class);
	// 	LoginDto signUp2Dto = modelMapper.map(testUser_2, AuthDto.LoginDto.class);

	// 	ResponseEntity.BodyBuilder builder = authController.login(signUp1Dto);
	// 	assertEquals(HttpStatus.ACCEPTED, builder.build().getStatusCode());

	// 	ResponseEntity.BodyBuilder builder2 = authController.login(signUp2Dto);
	// 	assertEquals(HttpStatus.ACCEPTED, builder2.build().getStatusCode());

	// }

}
