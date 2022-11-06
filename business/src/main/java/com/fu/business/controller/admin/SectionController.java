package com.fu.business.controller.admin;

import com.fu.server.dto.SectionDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.SectionService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    @GetMapping("/list")
    public ResponseDto<PageDto> section(PageDto pageDto) {
        sectionService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<SectionDto> save(@RequestBody SectionDto sectionDto) {
        sectionService.save(sectionDto);
        return ResponseDto.success(sectionDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<SectionDto> delete(@PathVariable String id) {
        sectionService.delete(id);
        return ResponseDto.success();
    }
}
