package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.CartItem;
import com.hqd.teashopping.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车控制器
 */
@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartItemService cartItemService;

    /**
     * 获取购物车列表
     * GET /api/v1/cart
     */
    @GetMapping
    public Result<List<CartItem>> getCart(@RequestParam Long userId) {
        List<CartItem> items = cartItemService.getCartItems(userId);
        return Result.success(items);
    }

    /**
     * 添加到购物车
     * POST /api/v1/cart
     */
    @PostMapping
    public Result<Map<String, Object>> addToCart(@RequestBody CartItem cartItem) {
        boolean success = cartItemService.addToCart(cartItem);
        
        Map<String, Object> data = new HashMap<>();
        if (success) {
            data.put("cartItemId", cartItem.getId());
            data.put("quantity", cartItem.getQuantity());
            return Result.success(data);
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 更新购物车商品数量
     * PUT /api/v1/cart/{id}
     */
    @PutMapping("/{id}")
    public Result<Void> updateQuantity(@PathVariable Long id,
                                       @RequestBody Map<String, Integer> params) {
        Integer quantity = params.get("quantity");
        boolean success = cartItemService.updateQuantity(id, quantity);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 删除购物车商品
     * DELETE /api/v1/cart/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCartItem(@PathVariable Long id) {
        boolean success = cartItemService.deleteCartItem(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 批量删除购物车商品
     * DELETE /api/v1/cart/batch
     */
    @DeleteMapping("/batch")
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        boolean success = cartItemService.batchDelete(ids);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }
}
