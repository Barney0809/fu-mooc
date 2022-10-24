package com.fu.system.controller;

import com.fu.server.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/test/{id}")
    public String test(@PathVariable String id){
        return testService.selectByPrimaryKey(Integer.parseInt(id)).getName();
    }
}
