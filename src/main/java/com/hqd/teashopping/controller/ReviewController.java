package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.Review;
import com.hqd.teashopping.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商品评价控制器
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * 获取商品评价列表（分页）
     * GET /api/v1/products/{productId}/reviews?pageNum=1&pageSize=5
     */
    @GetMapping("/products/{productId}/reviews")
    public Result<Map<String, Object>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {

        Map<String, Object> data = reviewService.getProductReviews(productId, pageNum, pageSize);
        return Result.success(data);
    }

    /**
     * 添加商品评价
     * POST /api/v1/reviews
     */
    @PostMapping("/reviews")
    public Result<Void> addReview(@RequestBody Review review) {
        boolean success = reviewService.addReview(review);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("评价失败");
        }
    }
}
