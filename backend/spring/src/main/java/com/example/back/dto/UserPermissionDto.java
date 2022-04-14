package com.example.back.dto;

import com.example.back.model.Permission;
import com.example.back.model.user.UserPermission;
import com.example.back.service.Role;

//dto

public class UserPermissionDto {
    
    private Role role;

    public UserPermission toEntity() {
        return UserPermission.builder()
            .permissionId(role.getValue())
            .build();
    }

}
