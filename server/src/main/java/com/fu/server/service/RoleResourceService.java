package com.fu.server.service;

import com.fu.server.dto.RoleResourceDto;
import com.fu.server.dto.PageDto;

public interface RoleResourceService {
    void list(PageDto pageDto);
    void save(RoleResourceDto roleResourceDto);
    void delete(String id);
}