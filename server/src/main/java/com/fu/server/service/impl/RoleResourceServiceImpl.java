package com.fu.server.service.impl;

import com.fu.server.domain.RoleResource;
import com.fu.server.domain.RoleResourceExample;
import com.fu.server.dto.RoleResourceDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.RoleResourceMapper;
import com.fu.server.service.RoleResourceService;
import com.fu.server.utils.CopyUtil;
import com.fu.server.utils.UuidUtil;
import com.fu.server.utils.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleResourceServiceImpl implements RoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RoleResourceDto.class);
        pageDto.setList(roleResourceDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(RoleResourceDto roleResourceDto) {

        RoleResource roleResource = CopyUtil.copy(roleResourceDto,RoleResource.class);
        if(StringUtils.isEmpty(roleResourceDto.getId())){
            insert(roleResource);
        }
        else {
            update(roleResource);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(RoleResource roleResource) {
        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }
    /**
     * 更新
     */
    private void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }
}
