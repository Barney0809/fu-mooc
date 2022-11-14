package com.fu.server.service;

import com.fu.server.dto.FileDto;
import com.fu.server.dto.PageDto;

public interface FileService {
    void list(PageDto pageDto);
    void save(FileDto fileDto);
    void delete(String id);
}
