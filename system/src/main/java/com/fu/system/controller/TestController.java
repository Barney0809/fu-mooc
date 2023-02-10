package com.fu.system.controller;

import com.fu.server.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${system.test}")
    private String systemTest;

    @GetMapping("test-config")
    public ResponseDto test(){
        return ResponseDto.success(systemTest);
    }
}
