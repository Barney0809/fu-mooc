package com.fu.server.service;

import com.fu.server.dto.ChapterDto;
import com.fu.server.dto.PageDto;

import java.util.List;

public interface ChapterService {
    void list(PageDto pageDto);
}
