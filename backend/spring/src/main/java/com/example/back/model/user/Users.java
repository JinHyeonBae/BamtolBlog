package com.example.back.model.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.back.model.SubscribePost;
import com.example.back.model.SubscribeUser;
import com.example.back.model.post.PostInformation;
import com.example.back.model.post.PostPermission;
import com.example.back.model.post.Posts;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//DAO
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) 
public class Users{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 사용자의 고유 넘버, 구독자 고유 넘버, 사용자 구독 레벨, 

    @Column(name="created_at", columnDefinition = "datetime")
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Posts> posts = new ArrayList<Posts>();

    @OneToMany(mappedBy = "user")
    private List<PostInformation> postsInfo = new ArrayList<PostInformation>();

    @OneToMany(mappedBy = "user",cascade={CascadeType.MERGE, CascadeType.REMOVE})
    private List<SubscribePost> subPost = new ArrayList<SubscribePost>();

    @OneToMany(mappedBy = "subscriber",cascade={CascadeType.MERGE, CascadeType.REMOVE})
    private List<SubscribeUser> subHost = new ArrayList<SubscribeUser>();

    @OneToMany(mappedBy = "publisher",cascade={CascadeType.MERGE, CascadeType.REMOVE})
    private List<SubscribeUser> subReader = new ArrayList<SubscribeUser>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<PostPermission> postPermit = new ArrayList<PostPermission>();

    @OneToOne(mappedBy = "user", targetEntity = UserInformation.class, cascade= {CascadeType.MERGE, CascadeType.REMOVE})
    private UserInformation userInfo = new UserInformation();

    @OneToOne(mappedBy = "user", targetEntity=UserAuth.class, cascade={CascadeType.MERGE, CascadeType.REMOVE})
    private UserAuth userAuth = new UserAuth();

}
