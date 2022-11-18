package com.fu.server.service.impl;

import com.fu.server.domain.CourseContentFile;
import com.fu.server.domain.CourseContentFileExample;
import com.fu.server.dto.CourseContentFileDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.CourseContentFileMapper;
import com.fu.server.service.CourseContentFileService;
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
public class CourseContentFileServiceImpl implements CourseContentFileService {
    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 列表查询
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        return CopyUtil.copyList(fileList, CourseContentFileDto.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(CourseContentFileDto courseContentFileDto) {

        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto,CourseContentFile.class);
        if(StringUtils.isEmpty(courseContentFileDto.getId())){
            insert(courseContentFile);
        }
        else {
            update(courseContentFile);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }
    /**
     * 更新
     */
    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }
}
