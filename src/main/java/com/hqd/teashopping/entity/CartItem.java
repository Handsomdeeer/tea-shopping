package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车项实体类
 */
@Data
@TableName("cart_items")
public class CartItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private Integer selected;

    /** 商品名称（冗余字段，避免联表查询） */
    private String productName;

    /** 商品图片（冗余字段） */
    private String productImage;

    /** 商品单价（冗余字段） */
    private BigDecimal price;

    /** 小计金额（计算字段，不存数据库） */
    @TableField(exist = false)
    private BigDecimal subtotal;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
