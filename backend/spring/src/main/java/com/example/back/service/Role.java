package com.example.back.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Role {    
    
    // 접속한 유저 권한
    UNAUTH(1),
    AUTH(2),
    ADMIN(3),

    // 포스트에 대한 유저의 권한
    GUEST(11),
    MEMBER(12),
    POST_SUBSCRIBER(13),
    DOMAIN_SUBSCRIBER(14),
    PUBLISHER(15),

    // 포스트가 가지는 권한
    PUBLIC(21),
    PROTECT(22),
    PRIVATE(23);

    private int value = 0;


    public static Role valueOf(int roleNum){
        Role role = null;
        switch (roleNum) {
            case 11:
                role = Role.GUEST;
                break;
            case 12:
                role = Role.MEMBER;
                break;
            case 13:
                role = Role.POST_SUBSCRIBER;
                break;
            case 14:
                role = Role.DOMAIN_SUBSCRIBER;
                break;
            case 21:
                role = Role.PUBLIC;
                break;
            case 22:
                role = Role.PROTECT;
                break;
            default:
                role=Role.PRIVATE;
        }
        return role;
    }



}
