package com.example.back.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),

    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member

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