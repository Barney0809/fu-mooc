package com.fu.business.controller.admin;

import com.fu.server.dto.CourseContentFileDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.CourseContentFileService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/courseContentFile")
public class CourseContentFileController {

    public static final String BUSINESS_NAME = "课程内容文件";

    @Resource
    private CourseContentFileService courseContentFileService;

    @GetMapping("/list/{courseId}")
    public ResponseDto list(@PathVariable String courseId) {
        List<CourseContentFileDto> fileDtoList = courseContentFileService.list(courseId);
        return ResponseDto.success(fileDtoList);
    }

    @PostMapping("/save")
    public ResponseDto<CourseContentFileDto> save(@RequestBody CourseContentFileDto courseContentFileDto) {
        // 保存校验
        ValidatorUtil.require(courseContentFileDto.getCourseId(), "课程id");
        ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
        ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 100);
        courseContentFileService.save(courseContentFileDto);
        return ResponseDto.success(courseContentFileDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<CourseContentFileDto> delete(@PathVariable String id) {
        courseContentFileService.delete(id);
        return ResponseDto.success();
    }
}
