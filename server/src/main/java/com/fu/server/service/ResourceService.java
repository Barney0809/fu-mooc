package com.fu.server.service;

import com.fu.server.dto.ResourceDto;
import com.fu.server.dto.PageDto;

public interface ResourceService {
    void list(PageDto pageDto);
    void save(ResourceDto resourceDto);
    void delete(String id);
    void saveJson(String json);
}