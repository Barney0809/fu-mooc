package com.fu.server.service;

import com.fu.server.dto.CourseContentFileDto;
import com.fu.server.dto.PageDto;

import java.util.List;

public interface CourseContentFileService {
    List<CourseContentFileDto> list(String courseId);
    void save(CourseContentFileDto courseContentFileDto);
    void delete(String id);
}
