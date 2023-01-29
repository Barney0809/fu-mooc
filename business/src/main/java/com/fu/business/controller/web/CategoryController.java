package com.fu.business.controller.web;

import com.fu.server.dto.CategoryDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.CategoryService;
import com.fu.server.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("webCategoryController")
@RequestMapping("/web/category")
public class CategoryController {

    public static final String BUSINESS_NAME = "分类";
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);


    @Resource
    private CategoryService categoryService;

    /**
     * 列表查询
     */
    @GetMapping("/all")
    public ResponseDto all() {
        List<CategoryDto> categoryDtoList = categoryService.all();
        return ResponseDto.success(categoryDtoList);
    }
}
