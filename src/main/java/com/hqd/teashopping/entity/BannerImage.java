package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 轮播图/广告图片实体类
 */
@Data
@TableName("banner_images")
public class BannerImage {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String imageUrl;

    private String type;

    private Integer sortOrder;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
