package com.fu.server.service;

import com.fu.server.dto.*;

import java.util.List;

public interface CourseService {
    void list(CoursePageDto pageDto);
    void save(CourseDto courseDto);
    void delete(String id);
    void updateTime(String courseId);
    CourseContentDto findContent(String id);
    int saveContent(CourseContentDto contentDto);
    void sort(SortDto sortDto);
    List<CourseDto> listNew(PageDto pageDto);
}
