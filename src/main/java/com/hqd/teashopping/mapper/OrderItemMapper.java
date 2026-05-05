package com.hqd.teashopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqd.teashopping.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项 Mapper 接口
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
