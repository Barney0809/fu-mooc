package com.fu.server.enums;

public enum ExceptionEnum {
    USER_LOGIN_NAME_EXIST("登录名已存在"),
    ;

    private String message;

    ExceptionEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
