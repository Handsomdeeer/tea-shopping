package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品评价实体类
 */
@Data
@TableName("reviews")
public class Review {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long orderId;
    
    private Long productId;
    
    private Long userId;
    
    private Integer rating;
    
    private String content;
    
    private String images;
    
    private String reply;
    
    private LocalDateTime replyTime;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
