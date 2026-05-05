package com.hqd.teashopping.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.Category;
import com.hqd.teashopping.entity.Product;
import com.hqd.teashopping.service.CategoryService;
import com.hqd.teashopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取商品分类列表
     * GET /api/v1/categories
     */
    @GetMapping("/categories")
    public Result<List<Category>> getCategories() {
        List<Category> categories = categoryService.getCategoryList();
        return Result.success(categories);
    }

    /**
     * 获取商品列表（分页、筛选、排序）
     * GET /api/v1/products
     */
    @GetMapping("/products")
    public Result<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        
        Page<Product> page = productService.getProductList(pageNum, pageSize, categoryId, keyword, status);
        
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("pageNum", page.getCurrent());
        data.put("pageSize", page.getSize());
        data.put("list", page.getRecords());
        
        return Result.success(data);
    }

    /**
     * 获取商品详情
     * GET /api/v1/products/{id}
     */
    @GetMapping("/products/{id}")
    public Result<Product> getProductDetail(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return Result.success(product);
        } else {
            return Result.error("商品不存在");
        }
    }

    /**
     * 管理员添加商品
     * POST /api/v1/admin/products
     */
    @PostMapping("/admin/products")
    public Result<Map<String, Object>> addProduct(@RequestBody Product product) {
        boolean success = productService.addProduct(product);
        
        Map<String, Object> data = new HashMap<>();
        if (success) {
            data.put("productId", product.getId());
            return Result.success(data);
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 管理员更新商品
     * PUT /api/v1/admin/products/{id}
     */
    @PutMapping("/admin/products/{id}")
    public Result<Void> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        boolean success = productService.updateProduct(product);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 管理员删除商品
     * DELETE /api/v1/admin/products/{id}
     */
    @DeleteMapping("/admin/products/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        boolean success = productService.deleteProduct(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }
}
