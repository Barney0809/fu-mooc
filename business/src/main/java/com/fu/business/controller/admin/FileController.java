package com.fu.business.controller.admin;

import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.FileService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    public static final String BUSINESS_NAME = "文件";

    @Resource
    private FileService fileService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        fileService.list(pageDto);
        return ResponseDto.success(pageDto);
    }
}
