package com.fu.business.controller.admin;

import com.fu.server.dto.CourseDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.CourseService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    public static final String BUSINESS_NAME = "";

    @Resource
    private CourseService courseService;

    @GetMapping("/list")
    public ResponseDto<PageDto> course(PageDto pageDto) {
        courseService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<CourseDto> save(@RequestBody CourseDto courseDto) {
        // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);
        courseService.save(courseDto);
        return ResponseDto.success(courseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<CourseDto> delete(@PathVariable String id) {
        courseService.delete(id);
        return ResponseDto.success();
    }
}
