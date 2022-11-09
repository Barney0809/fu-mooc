package com.fu.server.dto;


import java.util.List;

public class CategoryDto {

    /**
     * id
     */
    private String id;

    /**
     * 父id
     */
    private String parent;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 子分类
     */
    private List<CategoryDto> childCategoryList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<CategoryDto> getChildCategoryList() {
        return childCategoryList;
    }

    public void setChildCategoryList(List<CategoryDto> childCategoryList) {
        this.childCategoryList = childCategoryList;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id='" + id + '\'' +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", childCategoryList=" + childCategoryList +
                '}';
    }
}