package com.example.back.dto;

import com.example.back.model.post.PostInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class PostDto {


    @Getter
    @Setter
    @NoArgsConstructor
    public static class postInformationDto{
        String contents;
        String title;

        public postInformationDto(String contents, String title){
            this.contents = contents;
            this.title = title;
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createPostDto{

        String contents;
        String title;
        String displayLevel;
        int price;
        int userId;

        public PostInformation toEntity(){
            return PostInformation.builder()
                                .title(this.title)
                                .contents(this.contents)
                                .displayLevel(this.displayLevel)
                                .userId(this.userId)
                                .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class readPostDto{
        int userId;
        int postId;
    }

    

    
}
