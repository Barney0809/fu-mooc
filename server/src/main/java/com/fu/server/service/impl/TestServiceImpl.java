package com.fu.server.service.impl;

import com.fu.server.domain.Test;
import com.fu.server.domain.TestExample;
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

    @Override
    public List<Test> list() {
        TestExample testExample = new TestExample();
        testExample.createCriteria().andIdEqualTo(1);
        return testMapper.selectByExample(testExample);
    }
}
