package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.PaymentConfig;
import com.hqd.teashopping.service.PaymentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 支付配置管理控制器
 */
@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class PaymentConfigController {

    @Autowired
    private PaymentConfigService paymentConfigService;

    /**
     * 获取支付配置
     * GET /api/v1/admin/payment-config
     */
    @GetMapping("/payment-config")
    public Result<PaymentConfig> getConfig() {
        PaymentConfig config = paymentConfigService.getConfig();
        return Result.success(config);
    }

    /**
     * 保存支付配置
     * PUT /api/v1/admin/payment-config
     */
    @PutMapping("/payment-config")
    public Result<Void> saveConfig(@RequestBody PaymentConfig config) {
        boolean success = paymentConfigService.saveConfig(config);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("保存失败");
        }
    }
}
