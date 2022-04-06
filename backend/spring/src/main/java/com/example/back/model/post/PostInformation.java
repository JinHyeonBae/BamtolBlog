package com.example.back.model.post;

import javax.persistence.*;

import com.example.back.service.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 테이블의 필드와 매핑되는 영역(DTO)
@Entity
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_Information")
public class PostInformation {
    
    @Id
    // 기본키 생성을 DB에 위임하도록 함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="post_id")
    private int postId;

    @Column(name="user_id")
    private int userId;

    @Column(name="title", columnDefinition = "TEXT")
    private String title;
    
    @Column(name="contents", columnDefinition = "TEXT")
    private String contents;

    @Column(name="createdAt", columnDefinition = "datetime")
    private String createdAt;

    @Column(name="updatedAt", columnDefinition = "datetime")
    private String updatedAt;

    @Column(name="display_level")
    private String displayLevel;

    @Column(name="price")
    private int price;

    @Column(name="is_charged", columnDefinition = "int")
    private int isCharged;

    @Builder
    public PostInformation(String title, String contents, String displayLevel, int isCharged, int price, int userId){
        this.title = title;
        this.contents = contents;
        this.displayLevel = displayLevel;
        this.isCharged = isCharged;
        this.userId = userId;
    }

}
