package com.fu.business.controller.admin;

import com.fu.server.dto.ChapterDto;
import com.fu.server.service.ChapterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @GetMapping("/list")
    public List<ChapterDto> chapter() {
        return chapterService.list();
    }
}
