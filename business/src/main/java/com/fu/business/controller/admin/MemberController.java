package com.fu.business.controller.admin;

import com.fu.server.dto.MemberDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.MemberService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/member")
public class MemberController {

    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        memberService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

}