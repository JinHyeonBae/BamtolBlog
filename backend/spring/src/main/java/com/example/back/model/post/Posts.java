package com.example.back.model.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.back.model.SubscribePost;
import com.example.back.model.user.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="posts")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private int userId; //publisher

    public Posts(int userId){
        this.userId = userId;
    }

    @OneToMany(mappedBy = "post",targetEntity=PostPermission.class)
    private List<PostPermission> postPermit = new ArrayList<PostPermission>();

    @OneToMany(mappedBy = "post",targetEntity=SubscribePost.class)
    private List<SubscribePost> subPost = new ArrayList<SubscribePost>();

    @OneToOne(mappedBy = "post", targetEntity=PostInformation.class)
    private PostInformation postInfo = new PostInformation();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", insertable = false, updatable = false)
    Users user;

    // public void add(PostInformation pi){
    //     pi.setPost(this);
    //     this.postInfo.
    // }

    public void add(PostPermission pp){
        pp.setPost(this);
        this.postPermit.add(pp);
    }

    public void add(PostInformation pp){
        pp.setPost(this);
        this.postInfo = pp;
    }


}
