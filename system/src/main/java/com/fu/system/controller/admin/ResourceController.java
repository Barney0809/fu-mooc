package com.fu.system.controller.admin;

import com.fu.server.dto.ResourceDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.ResourceService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

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
    public ResponseDto save(@RequestBody String jsonStr) {
        // 保存校验
        ValidatorUtil.require(jsonStr, "资源");
        resourceService.saveJson(jsonStr);
        return ResponseDto.success();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<ResourceDto> delete(@PathVariable String id) {
        resourceService.delete(id);
        return ResponseDto.success();
    }

    /**
     * 资源树查询
     */
    @GetMapping("/load-tree")
    public ResponseDto loadTree() {
        List<ResourceDto> resourceDtoList = resourceService.loadTree();
        return ResponseDto.success(resourceDtoList);
    }
}