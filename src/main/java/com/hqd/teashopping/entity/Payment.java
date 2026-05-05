package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付信息实体类
 */
@Data
@TableName("payments")
public class Payment {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long orderId;
    
    private String orderNo;
    
    private Integer payType;
    
    private Integer payStatus;
    
    private String transactionId;
    
    private BigDecimal payAmount;
    
    private LocalDateTime payTime;
    
    private String qrcodeUrl;
    
    private String callbackData;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
