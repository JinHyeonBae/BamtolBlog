// package com.example.back.service;

// import java.util.ArrayList;
// import java.util.List;

// import com.example.back.dto.AuthDto;
// import com.example.back.dto.UserDto;
// import com.example.back.model.Permission;
// import com.example.back.model.user.UserPermission;
// import com.example.back.model.user.Users;
// import com.example.back.repository.PermissionRepository;
// import com.example.back.repository.UserPermissionReposotiry;
// import com.example.back.repository.UserRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;


// // 사용자별 데이터를 로드하는 인터페이스
// @Service
// public class CustomUserDetailService implements UserDetailsService{
    
//     // db로 아이디 값을 체크하면 끝
//     @Autowired
//     UserRepository urepo;
//     @Autowired
//     PermissionRepository prepo;
//     @Autowired
//     UserPermissionReposotiry uprepo;

//     // 자동으로 Login을 진행시켜줌
//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        
//         // email로 db 조회
//         Users userAuths = urepo.findByEmail(email);  
        
//         if(userAuths == null){
//             throw new UsernameNotFoundException("User "+ email + " Not Found");
//         }
        
//         List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
//         //유저 레벨 권한
//         authList.add(new SimpleGrantedAuthority("AUTH"));

//         //엔티티 아님!
//         return new User(userAuths.getEmail(), userAuths.getPassword(),authList);
//     }
// }
