package com.fu.server.service;

import com.fu.server.dto.SectionDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.SectionPageDto;

import java.util.List;

public interface SectionService {
    void list(SectionPageDto sectionPageDto);
    void save(SectionDto sectionDto);
    void delete(String id);
    List<SectionDto> listByCourse(String courseId);
}
