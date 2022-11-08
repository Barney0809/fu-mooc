package com.fu.server.service;

import com.fu.server.dto.CategoryDto;
import com.fu.server.dto.PageDto;

public interface CategoryService {
    void list(PageDto pageDto);
    void save(CategoryDto categoryDto);
    void delete(String id);
}
