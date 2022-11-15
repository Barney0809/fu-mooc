package com.fu.file.controller;

import com.fu.server.dto.FileDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.enums.FileUseEnum;
import com.fu.server.service.FileService;
import com.fu.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @Resource
    private FileService fileService;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file,String use) throws IOException {
        LOG.info("上传文件开始");
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        // 保存文件到本地
        String key = UuidUtil.getShortUuid();
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = dir + File.separator + key + "." + suffix;
        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(fileName);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileService.save(fileDto);
        String url = FILE_DOMAIN + path;
        fileDto.setPath(url);
        return ResponseDto.success(fileDto);
    }
}
