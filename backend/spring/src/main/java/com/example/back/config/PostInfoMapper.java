package com.example.back.config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.model.post.PostInformation;
import com.example.back.service.PostService;


@Mapper(componentModel = "spring")
public interface PostInfoMapper extends GenericMapper<UpdatePostDto, PostInformation>{

    PostInfoMapper INSTANCE = Mappers.getMapper(PostInfoMapper.class);

    // TODO : crud 추가
    PostInformation updateDtoToPostInfoEntity(UpdatePostDto updateDto);
    
    
}


