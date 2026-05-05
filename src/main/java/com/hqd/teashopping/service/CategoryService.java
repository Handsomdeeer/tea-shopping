package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.entity.Category;
import com.hqd.teashopping.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类服务类
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类列表（管理后台用）
     * @return 分类列表
     */
    public List<Category> getAllCategories() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        return categoryMapper.selectList(wrapper);
    }

    /**
     * 获取所有启用状态的分类列表
     * @return 分类列表
     */
    public List<Category> getCategoryList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, 1);
        wrapper.orderByAsc(Category::getSort);
        return categoryMapper.selectList(wrapper);
    }

    /**
     * 添加分类
     * @param category 分类信息
     * @return 是否成功
     */
    public boolean addCategory(Category category) {
        return categoryMapper.insert(category) > 0;
    }

    /**
     * 更新分类
     * @param category 分类信息
     * @return 是否成功
     */
    public boolean updateCategory(Category category) {
        return categoryMapper.updateById(category) > 0;
    }

    /**
     * 删除分类
     * @param id 分类 ID
     * @return 是否成功
     */
    public boolean deleteCategory(Long id) {
        return categoryMapper.deleteById(id) > 0;
    }
}
