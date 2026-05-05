package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.Order;
import com.hqd.teashopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表（分页、状态筛选）
     * GET /api/v1/orders?pageNum=1&pageSize=10&status=0&userId=1
     */
    @GetMapping("/orders")
    public Result<Map<String, Object>> getOrders(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long userId) {
        Map<String, Object> data = orderService.getOrderList(pageNum, pageSize, status, userId);
        return Result.success(data);
    }

    /**
     * 创建订单
     * POST /api/v1/orders
     */
    @PostMapping("/orders")
    public Result<Map<String, Long>> createOrder(@RequestBody Map<String, Object> params) {
        if (params.get("userId") == null || params.get("addressId") == null || params.get("cartItemIds") == null) {
            return Result.error("参数不完整");
        }
        Long userId = Long.valueOf(params.get("userId").toString());
        Long addressId = Long.valueOf(params.get("addressId").toString());
        List<?> rawIds = (List<?>) params.get("cartItemIds");
        if (rawIds == null || rawIds.isEmpty()) {
            return Result.error("购物车商品不能为空");
        }
        List<Long> cartItemIds = rawIds.stream()
                .map(id -> Long.valueOf(id.toString())).toList();
        String remark = (String) params.get("remark");
        Order order = orderService.createOrder(userId, addressId, cartItemIds, remark);
        Map<String, Long> data = Map.of("orderId", order.getId());
        return Result.success(data);
    }

    /**
     * 获取订单详情
     * GET /api/v1/orders/{id}
     */
    @GetMapping("/orders/{id}")
    public Result<Map<String, Object>> getOrder(@PathVariable Long id) {
        Map<String, Object> order = orderService.getOrderById(id);
        if (order == null) {
            return Result.error("订单不存在");
        }
        return Result.success(order);
    }

    /**
     * 确认支付（用户）
     * PUT /api/v1/orders/{id}/pay
     */
    @PutMapping("/orders/{id}/pay")
    public Result<Void> payOrder(@PathVariable Long id) {
        boolean success = orderService.payOrder(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("支付失败");
        }
    }

    /**
     * 取消订单
     * PUT /api/v1/orders/{id}/cancel
     */
    @PutMapping("/orders/{id}/cancel")
    public Result<Void> cancelOrder(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String reason = params.get("reason");
        boolean success = orderService.cancelOrder(id, reason);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("取消失败");
        }
    }

    /**
     * 确认收货
     * PUT /api/v1/orders/{id}/confirm
     */
    @PutMapping("/orders/{id}/confirm")
    public Result<Void> confirmReceive(@PathVariable Long id) {
        boolean success = orderService.confirmReceive(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("确认收货失败");
        }
    }

    /**
     * 管理员发货
     * PUT /api/v1/admin/orders/{id}/ship
     */
    @PutMapping("/admin/orders/{id}/ship")
    public Result<Void> shipOrder(@PathVariable Long id, @RequestBody Map<String, String> params) {
        String logisticsCompany = params.get("logisticsCompany");
        String logisticsNo = params.get("logisticsNo");
        boolean success = orderService.shipOrder(id, logisticsCompany, logisticsNo);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("发货失败");
        }
    }
}
