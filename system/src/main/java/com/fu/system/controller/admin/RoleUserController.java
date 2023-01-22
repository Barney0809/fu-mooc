package com.fu.system.controller.admin;

import com.fu.server.dto.RoleUserDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.RoleUserService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleUser")
public class RoleUserController {

    public static final String BUSINESS_NAME = "角色用户关联";

    @Resource
    private RoleUserService roleUserService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        roleUserService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<RoleUserDto> save(@RequestBody RoleUserDto roleUserDto) {
        // 保存校验
        ValidatorUtil.require(roleUserDto.getRoleId(), "角色");
        ValidatorUtil.require(roleUserDto.getUserId(), "用户");
        roleUserService.save(roleUserDto);
        return ResponseDto.success(roleUserDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleUserDto> delete(@PathVariable String id) {
        roleUserService.delete(id);
        return ResponseDto.success();
    }
}