package com.fu.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.fu.server.domain.Resource;
import com.fu.server.domain.ResourceExample;
import com.fu.server.dto.ResourceDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.ResourceMapper;
import com.fu.server.service.ResourceService;
import com.fu.server.utils.CopyUtil;
import com.fu.server.utils.UuidUtil;
import com.fu.server.utils.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(ResourceDto resourceDto) {

        Resource resource = CopyUtil.copy(resourceDto,Resource.class);
        if(StringUtils.isEmpty(resourceDto.getId())){
            insert(resource);
        }
        else {
            update(resource);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增，ID是自定义好的，不是自动生成的
     */
    private void insert(Resource resource) {
        resourceMapper.insert(resource);
    }
    /**
     * 更新
     */
    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 保存资源树
     * @param json
     */
    @Transactional
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        LOG.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            this.insert(CopyUtil.copy(list.get(i), Resource.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    private void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }
}
