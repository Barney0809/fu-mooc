package com.fu.server.service;

import com.fu.server.dto.RoleDto;
import com.fu.server.dto.PageDto;

import java.util.List;

public interface RoleService {
    void list(PageDto pageDto);
    void save(RoleDto roleDto);
    void delete(String id);
    void saveResource(RoleDto roleDto);
    List<String> listResource(String roleId);
    void saveUser(RoleDto roleDto);
    List<String> listUser(String roleId);
}