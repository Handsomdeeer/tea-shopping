package com.hqd.teashopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqd.teashopping.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息 Mapper 接口
 */
@Mapper
public interface PaymentMapper extends BaseMapper<Payment> {
}
