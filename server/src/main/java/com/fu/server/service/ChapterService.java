package com.fu.server.service;

import com.fu.server.dto.ChapterDto;
import com.fu.server.dto.ChapterPageDto;

import java.util.List;

public interface ChapterService {
    void list(ChapterPageDto chapterPageDto);
    void save(ChapterDto chapterDto);
    void delete(String id);
    List<ChapterDto> listByCourse(String courseId);
}
