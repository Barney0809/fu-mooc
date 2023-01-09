package com.fu.server.service;

import com.fu.server.domain.User;
import com.fu.server.dto.LoginUserDto;
import com.fu.server.dto.UserDto;
import com.fu.server.dto.PageDto;

public interface UserService {
    void list(PageDto pageDto);
    void save(UserDto userDto);
    void delete(String id);
    User selectByLoginName(String loginName);
    void savePassword(UserDto userDto);
    LoginUserDto login(UserDto userDto);
}