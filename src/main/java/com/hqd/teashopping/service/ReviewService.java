package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqd.teashopping.entity.Product;
import com.hqd.teashopping.entity.Review;
import com.hqd.teashopping.entity.User;
import com.hqd.teashopping.mapper.ProductMapper;
import com.hqd.teashopping.mapper.ReviewMapper;
import com.hqd.teashopping.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品评价服务类
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 分页查询商品评价列表
     * @param productId 商品 ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 包含评价列表、总数、平均分的 Map
     */
    public Map<String, Object> getProductReviews(Long productId, Integer pageNum, Integer pageSize) {
        Page<Review> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getProductId, productId);
        wrapper.eq(Review::getStatus, 1); // 只查询正常状态的评价
        wrapper.orderByDesc(Review::getCreateTime);

        Page<Review> reviewPage = reviewMapper.selectPage(page, wrapper);
        List<Review> records = reviewPage.getRecords();

        // 查询用户信息并组装前端需要的数据
        List<Map<String, Object>> list = records.stream().map(review -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", review.getId());
            item.put("rating", review.getRating());
            item.put("content", review.getContent());
            item.put("images", parseImages(review.getImages()));
            item.put("reply", review.getReply());
            item.put("createTime", review.getCreateTime());

            User user = userMapper.selectById(review.getUserId());
            item.put("username", user != null ? user.getNickname() : "匿名用户");
            return item;
        }).collect(Collectors.toList());

        // 计算平均分
        Double averageRating = calculateAverageRating(productId);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", reviewPage.getTotal());
        result.put("pageNum", reviewPage.getCurrent());
        result.put("pageSize", reviewPage.getSize());
        result.put("averageRating", averageRating);

        return result;
    }

    /**
     * 计算商品平均评分
     */
    private Double calculateAverageRating(Long productId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getProductId, productId);
        wrapper.eq(Review::getStatus, 1);
        List<Review> reviews = reviewMapper.selectList(wrapper);
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double sum = reviews.stream().mapToInt(Review::getRating).sum();
        return BigDecimal.valueOf(sum / reviews.size())
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * 添加商品评价
     * @param review 评价信息
     * @return 是否成功
     */
    public boolean addReview(Review review) {
        review.setStatus(1);
        review.setCreateTime(LocalDateTime.now());
        review.setUpdateTime(LocalDateTime.now());
        int result = reviewMapper.insert(review);
        if (result > 0) {
            // 评价成功后更新商品评分和评价数
            updateProductRating(review.getProductId());
        }
        return result > 0;
    }

    /**
     * 更新商品评分和评价数
     */
    private void updateProductRating(Long productId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getProductId, productId);
        wrapper.eq(Review::getStatus, 1);
        List<Review> reviews = reviewMapper.selectList(wrapper);

        int reviewCount = reviews.size();
        if (reviewCount > 0) {
            double sum = reviews.stream().mapToInt(Review::getRating).sum();
            double avg = sum / reviewCount;
            BigDecimal rating = BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP);

            Product product = new Product();
            product.setId(productId);
            product.setRating(rating);
            product.setReviewCount(reviewCount);
            productMapper.updateById(product);
        }
    }

    /**
     * 解析图片 JSON 字符串为数组（简化处理）
     */
    private List<String> parseImages(String images) {
        if (images == null || images.trim().isEmpty()) {
            return null;
        }
        // 如果存储的是逗号分隔的 URL 列表
        return Arrays.asList(images.split(","));
    }
}
