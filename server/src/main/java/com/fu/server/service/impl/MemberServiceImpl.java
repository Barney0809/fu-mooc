package com.fu.server.service.impl;

import com.fu.server.domain.Member;
import com.fu.server.domain.MemberExample;
import com.fu.server.dto.MemberDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.MemberMapper;
import com.fu.server.service.MemberService;
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
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberExample memberExample = new MemberExample();
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberDto> memberDtoList = CopyUtil.copyList(memberList, MemberDto.class);
        pageDto.setList(memberDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(MemberDto memberDto) {

        Member member = CopyUtil.copy(memberDto,Member.class);
        if(StringUtils.isEmpty(memberDto.getId())){
            insert(member);
        }
        else {
            update(member);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(Member member) {
        Date now = new Date();
        member.setId(UuidUtil.getShortUuid());
        memberMapper.insert(member);
    }
    /**
     * 更新
     */
    private void update(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }
}
