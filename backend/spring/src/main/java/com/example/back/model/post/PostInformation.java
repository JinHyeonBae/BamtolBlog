package com.example.back.model.post;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 테이블의 필드와 매핑되는 영역(DTO)
@Entity
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_information")
public class PostInformation {
    
    @Id
    // 기본키 생성을 DB에 위임하도록 함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="post_id")
    private Integer postId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="title", columnDefinition = "TEXT")
    private String title;
    
    @Column(name="contents", columnDefinition = "TEXT")
    private String contents;

    @Column(name="created_at", columnDefinition = "datetime")
    private String createdAt;

    @Column(name="updated_at", columnDefinition = "datetime")
    private String updatedAt;

    @Column(name="display_level")
    private String displayLevel;

    @Column(name="is_charged", columnDefinition = "integer")
    private Integer isCharged;

    @Builder
    public PostInformation(String title, String contents, String displayLevel, int userId){
        this.title = title;
        this.contents = contents;
        this.displayLevel = displayLevel;
        this.userId = userId;
    }

    @Builder
    public PostInformation(String title, String contents, String displayLevel, int isCharged, int postId, int userId){
        this.title = title;
        this.contents = contents;
        this.displayLevel = displayLevel;
        this.isCharged = isCharged;
        this.userId = userId;
        this.postId = postId;
    }


}
