package com.fu.server.service.impl;

import com.fu.server.domain.Teacher;
import com.fu.server.domain.TeacherExample;
import com.fu.server.dto.TeacherDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.TeacherMapper;
import com.fu.server.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        pageDto.setList(teacherDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(TeacherDto teacherDto) {

        Teacher teacher = CopyUtil.copy(teacherDto,Teacher.class);
        if(StringUtils.isEmpty(teacherDto.getId())){
            insert(teacher);
        }
        else {
            update(teacher);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(Teacher teacher) {
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }
    /**
     * 更新
     */
    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }
}
