package com.fu.server.service.impl;

import com.fu.server.domain.Test;
import com.fu.server.mapper.TestMapper;
import com.fu.server.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;

    @Override
    public Test selectByPrimaryKey(Integer id) {
        return testMapper.selectByPrimaryKey(id);
    }
}
