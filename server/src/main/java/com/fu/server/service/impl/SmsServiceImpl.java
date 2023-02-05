package com.fu.server.service.impl;

import com.fu.server.domain.Sms;
import com.fu.server.domain.SmsExample;
import com.fu.server.dto.SmsDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.SmsMapper;
import com.fu.server.service.SmsService;
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
public class SmsServiceImpl implements SmsService {
    @Resource
    private SmsMapper smsMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SmsExample smsExample = new SmsExample();
        List<Sms> smsList = smsMapper.selectByExample(smsExample);
        PageInfo<Sms> pageInfo = new PageInfo<>(smsList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SmsDto> smsDtoList = CopyUtil.copyList(smsList, SmsDto.class);
        pageDto.setList(smsDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(SmsDto smsDto) {

        Sms sms = CopyUtil.copy(smsDto,Sms.class);
        if(StringUtils.isEmpty(smsDto.getId())){
            insert(sms);
        }
        else {
            update(sms);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        smsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(Sms sms) {
        Date now = new Date();
        sms.setId(UuidUtil.getShortUuid());
        smsMapper.insert(sms);
    }
    /**
     * 更新
     */
    private void update(Sms sms) {
        smsMapper.updateByPrimaryKey(sms);
    }
}
