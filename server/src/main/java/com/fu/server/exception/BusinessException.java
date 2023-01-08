package com.fu.server.exception;

import com.fu.server.enums.ExceptionEnum;

public class BusinessException extends RuntimeException{

    private ExceptionEnum code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ExceptionEnum code) {
        super(code.getMessage());
        this.code = code;
    }

    public ExceptionEnum getCode() {
        return code;
    }

    public void setCode(ExceptionEnum code) {
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
