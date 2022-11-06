package com.fu.server.service;

import com.fu.server.dto.${Domain}Dto;
import com.fu.server.dto.PageDto;

public interface ${Domain}Service {
    void list(PageDto pageDto);
    void save(${Domain}Dto ${domain}Dto);
    void delete(String id);
}
