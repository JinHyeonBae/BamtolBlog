package com.example.back.service.Impl;

import java.util.HashMap;

import com.example.back.dto.PostDto.ReadPostDto;
import com.example.back.service.Role;

public interface ManageAllAboutRoleImpl {

    
    public void addRole();
    public HashMap<String, String> readRole(ReadPostDto readPostDto);
    
    public void updateRole(Role role);
    public void deleteRole();
    // Role update 할 경우

}
