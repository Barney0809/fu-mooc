package com.fu.server.service.impl;

import com.fu.server.domain.Member;
import com.fu.server.domain.MemberExample;
import com.fu.server.dto.LoginMemberDto;
import com.fu.server.dto.MemberDto;
import com.fu.server.dto.PageDto;
import com.fu.server.enums.ExceptionEnum;
import com.fu.server.exception.BusinessException;
import com.fu.server.mapper.MemberMapper;
import com.fu.server.service.MemberService;
import com.fu.server.utils.CopyUtil;
import com.fu.server.utils.UuidUtil;
import com.fu.server.utils.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

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
        member.setRegisterTime(now);
        memberMapper.insert(member);
    }
    /**
     * 更新
     */
    private void update(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }


    /**
     * 按手机号查找
     * @param mobile
     * @return
     */
    public Member selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }

    /**
     * 登录
     * @param memberDto
     */
    public LoginMemberDto login(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            LOG.info("手机号不存在, {}", memberDto.getMobile());
            throw new BusinessException(ExceptionEnum.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                // 登录成功
                LoginMemberDto loginMemberDto = CopyUtil.copy(member, LoginMemberDto.class);
                return loginMemberDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getPassword(), member.getPassword());
                throw new BusinessException(ExceptionEnum.LOGIN_MEMBER_ERROR);
            }
        }
    }
}
