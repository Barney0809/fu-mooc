package com.fu.server.service.impl;

import com.fu.server.domain.File;
import com.fu.server.domain.FileExample;
import com.fu.server.dto.FileDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.FileMapper;
import com.fu.server.service.FileService;
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
public class FileServiceImpl implements FileService {
    @Resource
    private FileMapper fileMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        FileExample fileExample = new FileExample();
        List<File> fileList = fileMapper.selectByExample(fileExample);
        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList = CopyUtil.copyList(fileList, FileDto.class);
        pageDto.setList(fileDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(FileDto fileDto) {

        File file = CopyUtil.copy(fileDto,File.class);
        if(StringUtils.isEmpty(fileDto.getId())){
            insert(file);
        }
        else {
            update(file);
        }
    }

    /**
     * 删除
     */
    @Override
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
    private void insert(File file) {
        Date now = new Date();
        file.setCreatedAt(now);
        file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
        fileMapper.insert(file);
    }
    /**
     * 更新
     */
    private void update(File file) {
        file.setUpdatedAt(new Date());
        fileMapper.updateByPrimaryKey(file);
    }
}
