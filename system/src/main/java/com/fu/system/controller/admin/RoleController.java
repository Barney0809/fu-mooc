package com.fu.system.controller.admin;

import com.fu.server.dto.RoleDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.RoleService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    public static final String BUSINESS_NAME = "角色";

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        roleService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<RoleDto> save(@RequestBody RoleDto roleDto) {
        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);
        roleService.save(roleDto);
        return ResponseDto.success(roleDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleDto> delete(@PathVariable String id) {
        roleService.delete(id);
        return ResponseDto.success();
    }
}