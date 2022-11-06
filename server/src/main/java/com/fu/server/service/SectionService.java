package com.fu.server.service;

import com.fu.server.dto.SectionDto;
import com.fu.server.dto.PageDto;

public interface SectionService {
    void list(PageDto pageDto);
    void save(SectionDto sectionDto);
    void delete(String id);
}
