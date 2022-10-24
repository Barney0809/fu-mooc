package com.fu.server.service;

import com.fu.server.domain.Test;

import java.util.List;

public interface TestService {
    public Test selectByPrimaryKey(Integer id);
    public List<Test> list();
}
