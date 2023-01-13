package com.fu.system.controller.admin;

import com.fu.server.dto.ResourceDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.ResourceService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    public static final String BUSINESS_NAME = "资源";

    @Resource
    private ResourceService resourceService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        resourceService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<ResourceDto> save(@RequestBody ResourceDto resourceDto) {
        // 保存校验
        ValidatorUtil.require(resourceDto.getName(), "名称");
        ValidatorUtil.length(resourceDto.getName(), "名称", 1, 100);
        ValidatorUtil.length(resourceDto.getPage(), "页面", 1, 50);
        ValidatorUtil.length(resourceDto.getRequest(), "请求", 1, 200);
        resourceService.save(resourceDto);
        return ResponseDto.success(resourceDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<ResourceDto> delete(@PathVariable String id) {
        resourceService.delete(id);
        return ResponseDto.success();
    }
}