package com.fu.business.controller.admin;

import com.fu.server.dto.ChapterDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.ChapterService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ChapterService chapterService;

    @GetMapping("/list")
    public ResponseDto<PageDto> chapter(PageDto pageDto) {
        chapterService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<ChapterDto> save(@RequestBody ChapterDto chapterDto) {
        chapterService.save(chapterDto);
        return ResponseDto.success(chapterDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<ChapterDto> delete(@PathVariable String id) {
        chapterService.delete(id);
        return ResponseDto.success();
    }
}
