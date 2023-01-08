package com.fu.server.service.impl;

import com.fu.server.domain.User;
import com.fu.server.domain.UserExample;
import com.fu.server.dto.UserDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.UserMapper;
import com.fu.server.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
        pageDto.setList(userDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(UserDto userDto) {

        User user = CopyUtil.copy(userDto,User.class);
        if(StringUtils.isEmpty(userDto.getId())){
            insert(user);
        }
        else {
            update(user);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(User user) {
        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }
    /**
     * 更新
     */
    private void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
