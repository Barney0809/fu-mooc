package com.fu.business.controller.admin;

import com.fu.server.dto.*;
import com.fu.server.service.CourseCategoryService;
import com.fu.server.service.CourseService;
import com.fu.server.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {
    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;


    @GetMapping("/list")
    public ResponseDto<PageDto> list(CoursePageDto pageDto) {
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

    /**
     * 查找课程下所有分类
     *
     * @param courseId
     */
    @GetMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable(value = "courseId") String courseId) {
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        return ResponseDto.success(dtoList);
    }

    @GetMapping("/find-content/{id}")
    public ResponseDto findContent(@PathVariable String id) {
        CourseContentDto contentDto = courseService.findContent(id);
        return ResponseDto.success(contentDto);
    }

    @PostMapping("/save-content")
    public ResponseDto saveContent(@RequestBody CourseContentDto contentDto) {
        courseService.saveContent(contentDto);
        return ResponseDto.success();
    }

    @RequestMapping(value = "/sort")
    public ResponseDto sort(@RequestBody SortDto sortDto) {
        LOG.info("更新排序");
        courseService.sort(sortDto);
        return ResponseDto.success();
    }
}
