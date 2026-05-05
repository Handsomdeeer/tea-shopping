package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品分类实体类
 */
@Data
@TableName("categories")
public class Category {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String icon;
    
    private Long parentId;
    
    private Integer sort;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
