package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 支付配置实体类
 */
@Data
@TableName("payment_configs")
public class PaymentConfig {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String wechatQrcode;

    private String alipayQrcode;
}
