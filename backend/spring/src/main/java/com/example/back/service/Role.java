package com.example.back.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {    
    
    // 접속한 유저 권한
    UNAUTH("UNAUTH", 1),
    AUTH("AUTH", 2),
    ADMIN("ADMIN", 3),

    // 포스트에 대한 유저의 권한
    GUEST("GUEST", 11),
    MEMBER("MEMBER", 12),
    POST_SUBSCRIBER("POST_SUBSCRIBER", 13),
    DOMAIN_SUBSCRIBER("DOMAIN_SUBSCRIBER", 14),
    PUBLISHER("PUBLISHER", 15),

    // 포스트가 가지는 권한
    PUBLIC("PUBLIC", 21),
    PROTECT("PROTECT", 22),
    PRIVATE("PRIVATE", 23);

    private String key;
    private int value = 0;
    //key cnrk



    public static Role valueOf(int roleNum){
        Role role = null;

        int q = roleNum / 10;
        int r = roleNum % 10;

        if(q == 1){
            if(r == 1)
                role = Role.GUEST;
            else if(r == 2)
                role = Role.MEMBER;
            else if(r == 3)
                role = Role.POST_SUBSCRIBER;
            else if(r == 4)
                role = Role.DOMAIN_SUBSCRIBER;
            else
                role = Role.PUBLISHER;
        }
        else{
            if(r == 1)
                role = Role.PUBLIC;
            else if(r == 2)
                role = Role.PROTECT;
            else
                role = Role.PRIVATE;
        }
        
        return role;
    }
}


