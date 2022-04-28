package com.example.back.dto;

import com.example.back.model.post.PostPermission;
import com.example.back.service.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PermissionDto{

    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPermissionDto{
        private Role role;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPermissionDto{

        private int userId;
        private int postId;
        private int permissionId;

        public PostPermission toEntity(){
                return PostPermission.builder()
                    .userId(this.userId)
                    .permissionId(this.permissionId)
                    .postId(this.postId)
                    .build();
        }


    }

}
