package com.fu.server.service;

import com.fu.server.dto.TeacherDto;
import com.fu.server.dto.PageDto;

public interface TeacherService {
    void list(PageDto pageDto);
    void save(TeacherDto teacherDto);
    void delete(String id);
}
