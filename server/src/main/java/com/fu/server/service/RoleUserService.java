package com.fu.server.service;

import com.fu.server.dto.RoleUserDto;
import com.fu.server.dto.PageDto;

public interface RoleUserService {
    void list(PageDto pageDto);
    void save(RoleUserDto roleUserDto);
    void delete(String id);
}