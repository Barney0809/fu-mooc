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

}