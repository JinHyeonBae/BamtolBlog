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
    TOKEN_CLAIMS_NULL_POINTER(40003, "M003", "토큰 값이 전달되지 않았습니다."),

    PERMISSION_DENIED(40301, "C006", "접근이 거부되었습니다."),

    INVALID_INPUT_EMAIL(40101, "M001", "존재하지 않는 이메일입니다."),
    INVALID_INPUT_PASSWORD(40102, "M002", "패스워드가 틀렸습니다."),
    

    EXPIRE_TOKEN(40301, "M011", "토큰이 만료되었습니다."),
    INVALID_TOKEN(40302, "M012", "적절하지 않은 토큰입니다."),


    DUPLICATE_EMAIL(40901, "M010", "중복된 이메일입니다."),
    DUPLICATE_NICKNAME(40902, "M011", "중복된 닉네임입니다."),
    DUPLICATE_EMAIN_AND_NICKNAME(40903, "M012", "이메일, 닉네임 중복입니다."),

    INTERNAL_ERROR(50001, "S001", "내부 서버 에러입니다.");

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