package com.fu.server.service;

import com.fu.server.dto.ChapterDto;
import com.fu.server.dto.PageDto;

public interface ChapterService {
    void list(PageDto pageDto);
    void save(ChapterDto chapterDto);
    void delete(String id);
}
