package com.fu.server.service;

import com.fu.server.dto.ChapterDto;
import com.fu.server.dto.ChapterPageDto;

public interface ChapterService {
    void list(ChapterPageDto chapterPageDto);
    void save(ChapterDto chapterDto);
    void delete(String id);
}
