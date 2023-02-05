package com.fu.server.service;

import com.fu.server.dto.MemberCourseDto;
import com.fu.server.dto.PageDto;

public interface MemberCourseService {
    void list(PageDto pageDto);
    void save(MemberCourseDto memberCourseDto);
    void delete(String id);
}