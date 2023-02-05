package com.fu.server.service.impl;

import com.fu.server.domain.MemberCourse;
import com.fu.server.domain.MemberCourseExample;
import com.fu.server.dto.MemberCourseDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.MemberCourseMapper;
import com.fu.server.service.MemberCourseService;
import com.fu.server.utils.CopyUtil;
import com.fu.server.utils.UuidUtil;
import com.fu.server.utils.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class MemberCourseServiceImpl implements MemberCourseService {
    @Resource
    private MemberCourseMapper memberCourseMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);
        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberCourseDto> memberCourseDtoList = CopyUtil.copyList(memberCourseList, MemberCourseDto.class);
        pageDto.setList(memberCourseDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(MemberCourseDto memberCourseDto) {

        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto,MemberCourse.class);
        if(StringUtils.isEmpty(memberCourseDto.getId())){
            insert(memberCourse);
        }
        else {
            update(memberCourse);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(MemberCourse memberCourse) {
        Date now = new Date();
        memberCourse.setId(UuidUtil.getShortUuid());
        memberCourseMapper.insert(memberCourse);
    }
    /**
     * 更新
     */
    private void update(MemberCourse memberCourse) {
        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }
}
