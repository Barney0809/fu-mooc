package com.fu.system.controller.admin;

import com.fu.server.dto.RoleDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.RoleService;
import com.fu.server.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

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

    /**
     * 保存资源
     * @param roleDto
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto) {
        LOG.info("保存角色资源关联开始");
        roleService.saveResource(roleDto);
        return ResponseDto.success(roleDto);
    }

    /**
     * 加载已关联的资源
     */
    @GetMapping("/list-resource/{roleId}")
    public ResponseDto listResource(@PathVariable String roleId) {
        LOG.info("加载资源开始");
        List<String> resourceIdList = roleService.listResource(roleId);
        return ResponseDto.success(resourceIdList);
    }


    /**
     * 保存用户
     * @param roleDto
     */
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RoleDto roleDto) {
        LOG.info("保存角色用户关联开始");
        roleService.saveUser(roleDto);
        return ResponseDto.success(roleDto);
    }
}