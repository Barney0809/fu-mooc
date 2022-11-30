package com.fu.business.controller.admin;

import com.fu.server.dto.TeacherDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.TeacherService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    public static final String BUSINESS_NAME = "讲师";

    @Resource
    private TeacherService teacherService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        teacherService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<TeacherDto> save(@RequestBody TeacherDto teacherDto) {
        // 保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);
        teacherService.save(teacherDto);
        return ResponseDto.success(teacherDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<TeacherDto> delete(@PathVariable String id) {
        teacherService.delete(id);
        return ResponseDto.success();
    }
}
