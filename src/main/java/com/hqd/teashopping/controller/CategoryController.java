package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.Category;
import com.hqd.teashopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制器
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类（管理后台用）
     * GET /api/v1/admin/categories
     */
    @GetMapping("/admin/categories")
    public Result<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }

    /**
     * 添加分类
     * POST /api/v1/admin/categories
     */
    @PostMapping("/admin/categories")
    public Result<Void> addCategory(@RequestBody Category category) {
        boolean success = categoryService.addCategory(category);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 更新分类
     * PUT /api/v1/admin/categories/{id}
     */
    @PutMapping("/admin/categories/{id}")
    public Result<Void> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        boolean success = categoryService.updateCategory(category);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 删除分类
     * DELETE /api/v1/admin/categories/{id}
     */
    @DeleteMapping("/admin/categories/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        boolean success = categoryService.deleteCategory(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }
}
