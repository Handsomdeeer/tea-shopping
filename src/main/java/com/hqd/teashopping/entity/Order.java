package com.hqd.teashopping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
@TableName("orders")
public class Order {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private Integer status;
    
    private String receiverName;
    
    private String receiverPhone;
    
    private String receiverAddress;
    
    private BigDecimal totalAmount;
    
    private BigDecimal freightAmount;
    
    private BigDecimal payAmount;
    
    private String remark;
    
    private LocalDateTime payTime;
    
    private LocalDateTime deliveryTime;
    
    private LocalDateTime receiveTime;
    
    private LocalDateTime cancelTime;
    
    private String cancelReason;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
