package com.fu.server.service.impl;

import com.fu.server.domain.Category;
import com.fu.server.domain.CategoryExample;
import com.fu.server.dto.CategoryDto;
import com.fu.server.dto.PageDto;
import com.fu.server.mapper.CategoryMapper;
import com.fu.server.service.CategoryService;
import com.fu.server.utils.CopyUtil;
import com.fu.server.utils.UuidUtil;
import com.fu.server.utils.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 列表查询
     */
    @Override
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Override
    public void save(CategoryDto categoryDto) {

        Category category = CopyUtil.copy(categoryDto,Category.class);
        if(StringUtils.isEmpty(categoryDto.getId())){
            insert(category);
        }
        else {
            update(category);
        }
    }

    /**
     * 删除
     */
    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除子分类
     * @param id
     */
    private void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            // 如果是一级分类，需要删除其下的二级分类
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(example);
        }
    }

    @Override
    public List<CategoryDto> categoryList() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        categoryExample.createCriteria().andParentEqualTo("00000000");
        List<CategoryDto> parentCategoryList = CopyUtil.copyList(categoryMapper.selectByExample(categoryExample), CategoryDto.class);
        subCategoryList(parentCategoryList);
        return parentCategoryList;
    }

    /**
     *
     */
    private void subCategoryList(List<CategoryDto> categoryList){
        for (CategoryDto category : categoryList) {
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.setOrderByClause("sort asc");
            categoryExample.createCriteria().andParentEqualTo(category.getId());
            List<Category> list = categoryMapper.selectByExample(categoryExample);
            if(list.size() == 0){
                continue;
            }
            List<CategoryDto> childCategoryList = CopyUtil.copyList(list, CategoryDto.class);
            category.setChildCategoryList(childCategoryList);
            subCategoryList(childCategoryList);
        }
    }

    /**
     * 列表查询
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        return categoryDtoList;
    }

    /**
     * 新增
     */
    private void insert(Category category) {
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }
    /**
     * 更新
     */
    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
}
