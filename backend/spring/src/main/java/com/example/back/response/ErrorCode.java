package com.example.back.response;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),

    HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다."),

    // Member
    HEADER_NULL_POINTER(40001, "M001", "헤더가 NULL입니다."),
    INPUT_NULL_POINTER(40002, "M002", "입력값이 NULL입니다."),
    

    EMAIL_INPUT_INVALID(40101, "M001", "존재하지 않는 이메일입니다."),
    PASSWORD_INPUT_INVALID(40102, "M002", "패스워드가 틀렸습니다."),
    
    EMAIL_DUPLICATION(40901, "M010", "중복된 이메일입니다."),
    NICKNAME_DUPLICATION(40902, "M011", "중복된 닉네임입니다.");


    //admin


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}