package com.fu.server.service;

import com.fu.server.dto.SmsDto;
import com.fu.server.dto.PageDto;

public interface SmsService {
    void list(PageDto pageDto);
    void save(SmsDto smsDto);
    void delete(String id);
}