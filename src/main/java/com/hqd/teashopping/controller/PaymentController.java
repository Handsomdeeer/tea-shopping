package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.PaymentConfig;
import com.hqd.teashopping.service.OrderService;
import com.hqd.teashopping.service.PaymentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付控制器
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentConfigService paymentConfigService;

    @Autowired
    private OrderService orderService;

    /**
     * 获取支付二维码
     * POST /api/v1/payment/qrcode
     */
    @PostMapping("/payment/qrcode")
    public Result<Map<String, Object>> generateQrcode(@RequestBody Map<String, Object> params) {
        if (params.get("orderId") == null) {
            return Result.error("orderId 不能为空");
        }
        Long orderId = Long.valueOf(params.get("orderId").toString());
        String payType = (String) params.get("payType");

        PaymentConfig config = paymentConfigService.getConfig();
        String qrcodeUrl = "";
        if (config != null) {
            if ("ALIPAY".equals(payType)) {
                qrcodeUrl = config.getAlipayQrcode();
            } else {
                qrcodeUrl = config.getWechatQrcode();
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("qrcodeUrl", qrcodeUrl);
        data.put("expireTime", 1800);
        return Result.success(data);
    }

    /**
     * 查询支付状态
     * GET /api/v1/payment/status/{orderId}
     */
    @GetMapping("/payment/status/{orderId}")
    public Result<Map<String, Object>> getPaymentStatus(@PathVariable Long orderId) {
        Map<String, Object> order = orderService.getOrderById(orderId);
        Map<String, Object> data = new HashMap<>();
        if (order == null) {
            data.put("payStatus", 0);
        } else {
            // 订单状态 0:待付款, 1:待发货(已支付), 2:待收货, 3:已完成
            Integer status = (Integer) order.get("status");
            data.put("payStatus", status != null && status >= 1 ? 1 : 0);
        }
        return Result.success(data);
    }
}
