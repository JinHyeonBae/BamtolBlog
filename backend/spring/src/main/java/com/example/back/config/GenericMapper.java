package com.example.back.config;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Configuration;

import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.model.post.PostInformation;

public interface GenericMapper<D, E>{

    D toDto(E e);
    E toEntity(D d);
    
}
