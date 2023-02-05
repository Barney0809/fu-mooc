package com.fu.business.controller.web;

import com.fu.server.dto.MemberCourseDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.MemberCourseService;
import com.fu.server.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("webMemberCourseController")
@RequestMapping("/web/member-course")
public class MemberCourseController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberCourseController.class);
    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private MemberCourseService memberCourseService;

    /**
     * 报名
     */
    @PostMapping("/enroll")
    public ResponseDto enroll(@RequestBody MemberCourseDto memberCourseDto) {
        // 保存校验
        ValidatorUtil.require(memberCourseDto.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseDto.getCourseId(), "课程id");

        memberCourseDto = memberCourseService.enroll(memberCourseDto);
        return ResponseDto.success(memberCourseDto);
    }

    /**
     * 获取报名信息
     */
    @PostMapping("/get-enroll")
    public ResponseDto getEnroll(@RequestBody MemberCourseDto memberCourseDto) {
        memberCourseDto = memberCourseService.getEnroll(memberCourseDto);
        return ResponseDto.success(memberCourseDto);
    }
}
