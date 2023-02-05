package com.fu.business.controller.admin;

import com.fu.server.dto.SmsDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.SmsService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/sms")
public class SmsController {

    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        smsService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<SmsDto> save(@RequestBody SmsDto smsDto) {
        // 保存校验
        ValidatorUtil.require(smsDto.getMobile(), "手机号");
        ValidatorUtil.length(smsDto.getMobile(), "手机号", 1, 50);
        ValidatorUtil.require(smsDto.getCode(), "验证码");
        ValidatorUtil.require(smsDto.getUse(), "用途");
        ValidatorUtil.require(smsDto.getAt(), "生成时间");
        ValidatorUtil.require(smsDto.getStatus(), "用途");
        smsService.save(smsDto);
        return ResponseDto.success(smsDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<SmsDto> delete(@PathVariable String id) {
        smsService.delete(id);
        return ResponseDto.success();
    }
}