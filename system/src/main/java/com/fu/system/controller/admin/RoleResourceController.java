package com.fu.system.controller.admin;

import com.fu.server.dto.RoleResourceDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.RoleResourceService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    public static final String BUSINESS_NAME = "角色资源关联";

    @Resource
    private RoleResourceService roleResourceService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        roleResourceService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<RoleResourceDto> save(@RequestBody RoleResourceDto roleResourceDto) {
        // 保存校验
        ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");
        roleResourceService.save(roleResourceDto);
        return ResponseDto.success(roleResourceDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleResourceDto> delete(@PathVariable String id) {
        roleResourceService.delete(id);
        return ResponseDto.success();
    }
}