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

    @PostMapping("/save")
    public ResponseDto<MemberDto> save(@RequestBody MemberDto memberDto) {
        // 保存校验
        ValidatorUtil.length(memberDto.getMobile(), "手机号", 1, 11);
        ValidatorUtil.require(memberDto.getPassword(), "密码");
        ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);
        ValidatorUtil.length(memberDto.getPhoto(), "头像url", 1, 200);
        memberService.save(memberDto);
        return ResponseDto.success(memberDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<MemberDto> delete(@PathVariable String id) {
        memberService.delete(id);
        return ResponseDto.success();
    }
}