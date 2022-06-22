package com.example.back.config;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.back.dto.PostDto.UpdatePostDto;
import com.example.back.model.post.PostInformation;

@Mapper(componentModel = "spring")
public interface GenericMapper<D, E>{

    // D toDto(E e);
    // E toEntity(D d);

    
    void updateCustomerFromDto(UpdatePostDto updateDto, @MappingTarget PostInformation updatedPostInfo);

}
