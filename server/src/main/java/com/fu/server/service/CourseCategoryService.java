package com.fu.server.service;

import com.fu.server.dto.CategoryDto;
import com.fu.server.dto.CourseCategoryDto;
import com.fu.server.dto.PageDto;

import java.util.List;

public interface CourseCategoryService {
    void list(PageDto pageDto);
    void save(CourseCategoryDto courseCategoryDto);
    void delete(String id);
    void saveBatch(String courseId, List<CategoryDto> categoryDtoList);
    List<CourseCategoryDto> listByCourse(String courseId);
}
