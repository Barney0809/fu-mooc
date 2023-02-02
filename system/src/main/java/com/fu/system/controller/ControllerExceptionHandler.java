package com.fu.system.controller;

import com.fu.server.dto.ResponseDto;
import com.fu.server.exception.BusinessException;
import com.fu.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e) {
        LOG.warn(e.getMessage());
        return ResponseDto.error("请求参数异常！");
    }
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto businessExceptionHandler(BusinessException e) {
        LOG.error("业务异常：{}", e.getCode().getMessage());
        return ResponseDto.error(e.getCode().getMessage());
    }
}
