package com.fu.server.service;

import com.fu.server.dto.MemberDto;
import com.fu.server.dto.PageDto;

public interface MemberService {
    void list(PageDto pageDto);
    void save(MemberDto memberDto);
    void delete(String id);
}