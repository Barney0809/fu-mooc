package com.fu.server.service;

import com.fu.server.dto.CourseContentDto;
import com.fu.server.dto.CourseDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.SortDto;

import java.util.List;

public interface CourseService {
    void list(PageDto pageDto);
    void save(CourseDto courseDto);
    void delete(String id);
    void updateTime(String courseId);
    CourseContentDto findContent(String id);
    int saveContent(CourseContentDto contentDto);
    void sort(SortDto sortDto);
    List<CourseDto> listNew(PageDto pageDto);
}
