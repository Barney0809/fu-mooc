package com.fu.business.controller.admin;

import com.fu.server.dto.MemberCourseDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.MemberCourseService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/memberCourse")
public class MemberCourseController {

    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private MemberCourseService memberCourseService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        memberCourseService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<MemberCourseDto> save(@RequestBody MemberCourseDto memberCourseDto) {
        // 保存校验
        ValidatorUtil.require(memberCourseDto.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseDto.getCourseId(), "课程id");
        ValidatorUtil.require(memberCourseDto.getAt(), "报名时间");
        memberCourseService.save(memberCourseDto);
        return ResponseDto.success(memberCourseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<MemberCourseDto> delete(@PathVariable String id) {
        memberCourseService.delete(id);
        return ResponseDto.success();
    }
}