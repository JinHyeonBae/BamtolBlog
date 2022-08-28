package com.example.back.config;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.model.post.PostInformation;


@Mapper(componentModel = "spring")
public interface PostInfoMapper extends GenericMapper<UpdatePostDto, PostInformation>{

    PostInfoMapper INSTANCE = Mappers.getMapper(PostInfoMapper.class);

    // TODO : crud 추가
    PostInformation updateDtoToPostInfoEntity(UpdatePostDto updateDto);
    
    
}


