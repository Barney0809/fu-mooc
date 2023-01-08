package com.fu.server.service;

import com.fu.server.dto.UserDto;
import com.fu.server.dto.PageDto;

public interface UserService {
    void list(PageDto pageDto);
    void save(UserDto userDto);
    void delete(String id);
}