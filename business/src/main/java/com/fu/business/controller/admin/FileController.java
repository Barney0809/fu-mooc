package com.fu.business.controller.admin;

import com.fu.server.dto.FileDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.FileService;
import com.fu.server.utils.ValidatorUtil;
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

    @PostMapping("/save")
    public ResponseDto<FileDto> save(@RequestBody FileDto fileDto) {
        // 保存校验
        ValidatorUtil.require(fileDto.getPath(), "相对路径");
        ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);
        ValidatorUtil.length(fileDto.getKey(), "文件标识", 1, 32);
        fileService.save(fileDto);
        return ResponseDto.success(fileDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<FileDto> delete(@PathVariable String id) {
        fileService.delete(id);
        return ResponseDto.success();
    }
}
