package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqd.teashopping.entity.Category;
import com.hqd.teashopping.entity.Product;
import com.hqd.teashopping.mapper.CategoryMapper;
import com.hqd.teashopping.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品服务类
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分页查询商品列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param categoryId 分类 ID（可选）
     * @param keyword 关键词（可选）
     * @param status 状态筛选
     * @return 分页结果
     */
    public Page<Product> getProductList(Integer pageNum, Integer pageSize,
                                        Long categoryId, String keyword, Integer status) {
        Page<Product> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Product::getStatus, status);
        }

        if (categoryId != null) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w.like(Product::getName, keyword)
                           .or()
                           .like(Product::getDescription, keyword));
        }

        // 按销量排序
        wrapper.orderByDesc(Product::getSales);

        Page<Product> result = productMapper.selectPage(page, wrapper);

        // 填充分类名称
        List<Long> categoryIds = result.getRecords().stream()
                .map(Product::getCategoryId)
                .distinct()
                .collect(Collectors.toList());
        if (!categoryIds.isEmpty()) {
            LambdaQueryWrapper<Category> catWrapper = new LambdaQueryWrapper<>();
            catWrapper.in(Category::getId, categoryIds);
            Map<Long, String> categoryMap = categoryMapper.selectList(catWrapper).stream()
                    .collect(Collectors.toMap(Category::getId, Category::getName));
            for (Product p : result.getRecords()) {
                p.setCategoryName(categoryMap.get(p.getCategoryId()));
            }
        }

        return result;
    }

    /**
     * 根据 ID 获取商品详情
     * @param id 商品 ID
     * @return 商品信息
     */
    public Product getProductById(Long id) {
        return productMapper.selectById(id);
    }

    /**
     * 获取所有分类
     * @return 分类列表
     */
    public List<Product> getAllCategories() {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, 1);
        return productMapper.selectList(wrapper);
    }

    /**
     * 管理员添加商品
     * @param product 商品信息
     * @return 是否成功
     */
    public boolean addProduct(Product product) {
        int result = productMapper.insert(product);
        return result > 0;
    }

    /**
     * 管理员更新商品
     * @param product 商品信息
     * @return 是否成功
     */
    public boolean updateProduct(Product product) {
        int result = productMapper.updateById(product);
        return result > 0;
    }

    /**
     * 管理员删除商品
     * @param id 商品 ID
     * @return 是否成功
     */
    public boolean deleteProduct(Long id) {
        int result = productMapper.deleteById(id);
        return result > 0;
    }
}
