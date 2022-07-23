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

        String title;
        String contents;

        public postInformationDto(String contents, String title){
            this.contents = contents;
            this.title = title;
        }
    }

    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    //@ApiModel
    public static class CreatePostDto{

        String title;
        String contents;
        String displayLevel;
        int price;
        
        public PostInformation PostInfoToEntity(){
            return PostInformation.builder()
                                .title(this.title)
                                .contents(this.contents)
                                .displayLevel(this.displayLevel)
                                .price(this.price)
                                .build();
        
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePostDto{

        String title;
        String contents;
        String displayLevel;
        int price;

    }    
}
