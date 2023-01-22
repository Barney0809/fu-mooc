package com.fu.server.service.impl;

import com.fu.server.domain.RoleUser;
import com.fu.server.domain.RoleUserExample;
import com.fu.server.dto.RoleUserDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.RoleUserMapper;
import com.fu.server.service.RoleUserService;
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
public class RoleUserServiceImpl implements RoleUserService {
    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
        pageDto.setList(roleUserDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(RoleUserDto roleUserDto) {

        RoleUser roleUser = CopyUtil.copy(roleUserDto,RoleUser.class);
        if(StringUtils.isEmpty(roleUserDto.getId())){
            insert(roleUser);
        }
        else {
            update(roleUser);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(RoleUser roleUser) {
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }
    /**
     * 更新
     */
    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }
}
