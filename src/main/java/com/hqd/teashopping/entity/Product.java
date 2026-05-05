package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 */
@Data
@TableName("products")
public class Product {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private Long categoryId;
    
    private String description;
    
    private String detail;
    
    private BigDecimal price;
    
    private BigDecimal originalPrice;
    
    private Integer stock;
    
    private Integer sales;
    
    @JsonProperty("imageUrl")
    private String mainImage;

    /** 分类名称（非数据库字段，联表查询时填充） */
    @TableField(exist = false)
    private String categoryName;

    private BigDecimal rating;

    private Integer reviewCount;

    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
