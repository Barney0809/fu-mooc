package com.fu.server.service.impl;

import com.fu.server.domain.${Domain};
import com.fu.server.domain.${Domain}Example;
import com.fu.server.dto.${Domain}Dto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.${Domain}Mapper;
import com.fu.server.service.${Domain}Service;
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
public class ${Domain}ServiceImpl implements ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(new ${Domain}Example());
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Dto.class);
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(${Domain}Dto ${domain}Dto) {

        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto,${Domain}.class);
        if(StringUtils.isEmpty(${domain}Dto.getId())){
            insert(${domain});
        }
        else {
            update(${domain});
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(${Domain} ${domain}) {
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }
    /**
     * 更新
     */
    private void update(${Domain} ${domain}) {
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }
}
