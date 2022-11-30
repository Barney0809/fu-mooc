package com.fu.business.controller.admin;

import com.fu.server.dto.CategoryDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.CategoryService;
import com.fu.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    public static final String BUSINESS_NAME = "商品分类";

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        categoryService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @GetMapping("/categoryList")
    public ResponseDto<List<CategoryDto>> categoryList() {
        List<CategoryDto> categoryList = categoryService.categoryList();
        return ResponseDto.success(categoryList);
    }

    @PostMapping("/save")
    public ResponseDto<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        categoryService.save(categoryDto);
        return ResponseDto.success(categoryDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<CategoryDto> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseDto.success();
    }
}
