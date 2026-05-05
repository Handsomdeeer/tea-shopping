package com.hqd.teashopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqd.teashopping.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车项 Mapper 接口
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {
}
