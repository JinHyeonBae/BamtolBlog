package com.example.back;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;

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

import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.hibernate.mapping.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;

import antlr.collections.List;
import ch.qos.logback.core.util.Duration;
import net.bytebuddy.asm.Advice.Return;
import springfox.documentation.spring.web.json.Json;

@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
class MainApplicationTest {

	@Mock
	UserRepository urRepository;

	@Mock
	UserInformationRepository urInfoRepo;

	@Mock
	UserAuthRepository urAuthRepo;

	@Mock
	@Autowired
	AuthController authController;

	@Mock
	@Autowired
	PostController postController;

	@Mock
	@Autowired
	CustomModelMapper customModelMapper;

	
	public void setUp(){

		Users testUser = new Users(76, "11111@naver.com");
		UserInformation testUserInfo = new UserInformation("11111@naver.com", "hey", "jeno", "hallygally", 76);


		Posts testPost = new Posts();
		
	}

	//성공
	@Test
	public void SignUpTest(){

	ModelMapper modelMapper = customModelMapper.strictMapper();

	final UserInformation sDto = UserInformation.builder()
												.email("111@naver.com")
												.password("111")
												.name("bear")
												.nickname("helly")
												.build();

	AuthDto.SignUpDto test1Dto = modelMapper.map(sDto, AuthDto.SignUpDto.class);
	System.out.println(test1Dto.getEmail());
	HttpStatus status = authController.signUp(test1Dto);
	assertEquals(HttpStatus.CREATED, status);

	final UserInformation test2Dto = UserInformation.builder()
													.email("222@naver.com")
													.password("bbb")
													.name("jane")
													.nickname("jane")
													.build();

	AuthDto.SignUpDto signUp2Dto = modelMapper.map(test2Dto,AuthDto.SignUpDto.class);
	System.out.println(signUp2Dto.getEmail());
	status = authController.signUp(signUp2Dto);
	assertEquals(HttpStatus.CREATED, status);
	}

	//성공
	@Test
	public void LoginTest(){

		ModelMapper modelMapper = customModelMapper.strictMapper();

		//builder 패턴으로
		UserInformation user1Info = UserInformation.builder()
												.email("test@naver.com")
												.password("bbb")
												.build();

		UserInformation user2Info = UserInformation.builder()
												.email("ab@naver.com")
												.password("bbb")
												.build();

		LoginDto signUp1Dto = modelMapper.map(user1Info, AuthDto.LoginDto.class);
		LoginDto signUp2Dto = modelMapper.map(user2Info, AuthDto.LoginDto.class);

		ResponseEntity.BodyBuilder builder = authController.login(signUp1Dto);
		assertEquals(HttpStatus.ACCEPTED, builder.build().getStatusCode());

		ResponseEntity.BodyBuilder builder2 = authController.login(signUp2Dto);
		assertEquals(HttpStatus.ACCEPTED, builder2.build().getStatusCode());

	}

	//유닛 테스트 다시 생성
	@Test
	public void ReadPost(){

	int post1Id = 3;
	int post2Id = 2;
	int user1Id = 11;
	int user2Id = 12;


	//여기서 데이터베이스로 토큰을 불러오고 유효성 검사. 바로 대입하는 형식의 하드코딩은 안된다.

	String user1Token =
	"eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJhdWQiOiJiYmJAbmF2ZXIuY29tIiwic3ViIjoidXNlciIsImlzcyI6ImFkbWluIiwiZXhwIjoxNjQ5MTIwNTI3fQ._TPaSAkbBbcE_h-vJ0UbDUbsdTTbK-ObBKHflRnb6GI";
	String user2Token =
	"eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJhdWQiOiJhYkBuYXZlci5jb20iLCJzdWIiOiJ1c2VyIiwiaXNzIjoiYWRtaW4iLCJleHAiOjE2NDkxMjA1Mjd9.kuTVu4yZ12fDYx53I9px1sCgWK4V63kXiKqSHniFZgk";
	//Optional<UserAuth> userAuth = urAuthRepo.findById(9);

	//String token = userAuth.get().getToken();
	//System.out.println(token);
	//ResponseEntity<HashMap<String, String>> map = new
	//ResponseEntity<HashMap<String, String>>();

	try{
		ResponseEntity<HashMap<String, String>> map1 = postController.readPost(user1Token, post1Id, user1Id);
		assertEquals(HttpStatus.UNAUTHORIZED, map1.getStatusCode());

		System.out.println("-----------------------------");

		ResponseEntity<HashMap<String, String>> map2 =
		postController.readPost(user2Token, post1Id, user2Id);
		assertEquals(HttpStatus.ACCEPTED, map2.getStatusCode());

		System.out.println("------------------------------");

		ResponseEntity<HashMap<String, String>> map3 =
		postController.readPost(user1Token, post2Id, user1Id);
		assertEquals(HttpStatus.ACCEPTED, map3.getStatusCode());

		System.out.println("-----------------------------");

		ResponseEntity<HashMap<String, String>> map4 =
		postController.readPost(user2Token, post2Id, user2Id);
		assertEquals(HttpStatus.valueOf(401), map4.getStatusCode());

		System.out.println("------------------------------");

	}
	catch(NoPermissionException e){
	System.out.println(e.getExplanation());
	}

	}

	@Test
	public void createPost() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("token",
		 "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJhdWQiOiJ0ZXN0QG5hdmVyLmNvbSIsInN1YiI6InVzZXIiLCJpc3MiOiJhZG1pbiIsImV4cCI6MTY0OTYxODM0OH0.ZDk3R1hTKOgDcfmcI3H9plx_S657F_IifHgBOQECnVo");
		
	// HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	// System.out.println(response.statusCode());



	}

}
