package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.entity.CartItem;
import com.hqd.teashopping.entity.Product;
import com.hqd.teashopping.mapper.CartItemMapper;
import com.hqd.teashopping.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车服务类
 */
@Service
public class CartItemService {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取用户购物车列表
     * @param userId 用户 ID
     * @return 购物车项列表
     */
    public List<CartItem> getCartItems(Long userId) {
        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartItem::getUserId, userId);
        List<CartItem> items = cartItemMapper.selectList(wrapper);
        for (CartItem item : items) {
            // 从商品表获取最新信息（图片、名称、价格）
            Product product = productMapper.selectById(item.getProductId());
            if (product != null) {
                item.setProductImage(product.getMainImage());
                item.setProductName(product.getName());
                item.setPrice(product.getPrice());
            }
            // 计算小计金额
            if (item.getPrice() != null && item.getQuantity() != null) {
                item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        return items;
    }

    /**
     * 添加到购物车
     * @param cartItem 购物车项
     * @return 是否成功
     */
    public boolean addToCart(CartItem cartItem) {
        // 检查是否已存在该商品
        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartItem::getUserId, cartItem.getUserId())
               .eq(CartItem::getProductId, cartItem.getProductId());

        CartItem existItem = cartItemMapper.selectOne(wrapper);

        if (existItem != null) {
            // 已存在，更新数量
            existItem.setQuantity(existItem.getQuantity() + cartItem.getQuantity());
            return cartItemMapper.updateById(existItem) > 0;
        } else {
            // 不存在，查询商品信息并填充冗余字段
            Product product = productMapper.selectById(cartItem.getProductId());
            if (product != null) {
                cartItem.setProductName(product.getName());
                cartItem.setProductImage(product.getMainImage());
                cartItem.setPrice(product.getPrice());
            }
            return cartItemMapper.insert(cartItem) > 0;
        }
    }

    /**
     * 更新购物车商品数量
     * @param id 购物车项 ID
     * @param quantity 新数量
     * @return 是否成功
     */
    public boolean updateQuantity(Long id, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setQuantity(quantity);
        return cartItemMapper.updateById(cartItem) > 0;
    }

    /**
     * 删除购物车商品
     * @param id 购物车项 ID
     * @return 是否成功
     */
    public boolean deleteCartItem(Long id) {
        return cartItemMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除购物车商品
     * @param ids ID 列表
     * @return 是否成功
     */
    public boolean batchDelete(List<Long> ids) {
        for (Long id : ids) {
            cartItemMapper.deleteById(id);
        }
        return true;
    }

    /**
     * 清空购物车
     * @param userId 用户 ID
     * @return 是否成功
     */
    public boolean clearCart(Long userId) {
        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartItem::getUserId, userId);
        return cartItemMapper.delete(wrapper) > 0;
    }
}
