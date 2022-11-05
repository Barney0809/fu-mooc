package com.fu.business.controller;

import com.fu.server.dto.ResponseDto;
import com.fu.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//ControllerAdvice是Controller增强器，可以对Controller做统一的处理，如异常处理，数据处理等
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e) {
        /*
            有时候我们的接口原本是不对外的，或者只跟特定的第三方应用做对接，这时为了内部安全，
            不应该把参数的校验规则暴露出去，所以需要模糊返回信息。
            类似登录接口应该返回“用户名或密码错误”而不是用户名不存在或密码错误（容易被探测）
            如果开发过程中提示“请求参数异常”，说明后端有校验拦截，而前端没有。此时应该把前端校验加上。
         */
//        return ResponseDto.error(e.getMessage());
        return ResponseDto.error("请求参数异常");
    }
}
