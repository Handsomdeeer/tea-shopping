package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqd.teashopping.entity.Address;
import com.hqd.teashopping.entity.CartItem;
import com.hqd.teashopping.entity.Order;
import com.hqd.teashopping.entity.OrderItem;
import com.hqd.teashopping.mapper.AddressMapper;
import com.hqd.teashopping.mapper.CartItemMapper;
import com.hqd.teashopping.mapper.OrderItemMapper;
import com.hqd.teashopping.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订单服务类
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    /**
     * 分页查询订单列表（支持状态筛选）
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param status 订单状态（可选）
     * @param userId 用户ID（可选，用户端传入则只查该用户）
     * @return 包含订单列表和分页信息的 Map
     */
    public Map<String, Object> getOrderList(Integer pageNum, Integer pageSize, Integer status, Long userId) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        if (userId != null) {
            wrapper.eq(Order::getUserId, userId);
        }
        wrapper.orderByDesc(Order::getCreateTime);

        Page<Order> orderPage = orderMapper.selectPage(page, wrapper);
        List<Order> records = orderPage.getRecords();

        // 查询订单项并组装数据
        List<Map<String, Object>> list = records.stream().map(order -> {
            Map<String, Object> item = new HashMap<>();
            item.put("orderId", order.getId());
            item.put("orderNo", order.getOrderNo());
            item.put("receiverName", order.getReceiverName());
            item.put("receiverPhone", order.getReceiverPhone());
            item.put("payAmount", order.getPayAmount());
            item.put("status", order.getStatus());
            item.put("createTime", order.getCreateTime());

            LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
            itemWrapper.eq(OrderItem::getOrderId, order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            item.put("productList", orderItems);

            return item;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", orderPage.getTotal());
        result.put("pageNum", orderPage.getCurrent());
        result.put("pageSize", orderPage.getSize());
        return result;
    }

    /**
     * 订单发货
     * @param orderId 订单 ID
     * @param logisticsCompany 物流公司
     * @param logisticsNo 物流单号
     * @return 是否成功
     */
    /**
     * 根据订单ID查询订单详情（包含订单项）
     * @param orderId 订单 ID
     * @return 订单详情 Map，包含 itemList
     */
    public Map<String, Object> getOrderById(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("id", order.getId());
        result.put("orderNo", order.getOrderNo());
        result.put("userId", order.getUserId());
        result.put("status", order.getStatus());
        result.put("receiverName", order.getReceiverName());
        result.put("receiverPhone", order.getReceiverPhone());
        result.put("receiverAddress", order.getReceiverAddress());
        result.put("totalAmount", order.getTotalAmount());
        result.put("freightAmount", order.getFreightAmount());
        result.put("payAmount", order.getPayAmount());
        result.put("remark", order.getRemark());
        result.put("payTime", order.getPayTime());
        result.put("deliveryTime", order.getDeliveryTime());
        result.put("receiveTime", order.getReceiveTime());
        result.put("cancelTime", order.getCancelTime());
        result.put("cancelReason", order.getCancelReason());
        result.put("createTime", order.getCreateTime());
        result.put("updateTime", order.getUpdateTime());

        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
        result.put("itemList", items);
        result.put("productList", items);

        return result;
    }

    public boolean payOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        order.setStatus(1); // 待发货
        order.setPayTime(LocalDateTime.now());
        return orderMapper.updateById(order) > 0;
    }

    public boolean shipOrder(Long orderId, String logisticsCompany, String logisticsNo) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getStatus() != 1) {
            return false;
        }
        order.setStatus(2); // 待收货
        order.setDeliveryTime(LocalDateTime.now());
        return orderMapper.updateById(order) > 0;
    }

    public boolean cancelOrder(Long orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        order.setStatus(4); // 已取消
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason(reason);
        return orderMapper.updateById(order) > 0;
    }

    public boolean confirmReceive(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getStatus() != 2) {
            return false;
        }
        order.setStatus(3); // 已完成
        order.setReceiveTime(LocalDateTime.now());
        return orderMapper.updateById(order) > 0;
    }

    /**
     * 创建订单
     * @param userId 用户 ID
     * @param addressId 收货地址 ID
     * @param cartItemIds 购物车项 ID 列表
     * @param remark 订单备注
     * @return 创建的订单
     */
    @Transactional
    public Order createOrder(Long userId, Long addressId, List<Long> cartItemIds, String remark) {
        // 查询收货地址
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new RuntimeException("收货地址不存在");
        }

        // 查询购物车商品
        List<CartItem> cartItems = cartItemMapper.selectBatchIds(cartItemIds);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车商品为空");
        }

        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            totalAmount = totalAmount.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        BigDecimal freightAmount = BigDecimal.ZERO;
        BigDecimal payAmount = totalAmount.add(freightAmount);

        // 生成订单号
        String orderNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 6);

        // 创建订单
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setStatus(0); // 待付款
        order.setReceiverName(address.getReceiverName());
        order.setReceiverPhone(address.getReceiverPhone());
        order.setReceiverAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        order.setTotalAmount(totalAmount);
        order.setFreightAmount(freightAmount);
        order.setPayAmount(payAmount);
        order.setRemark(remark);
        orderMapper.insert(order);

        // 创建订单项
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getProductName());
            orderItem.setProductImage(item.getProductImage());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemMapper.insert(orderItem);
        }

        // 删除购物车商品
        for (Long id : cartItemIds) {
            cartItemMapper.deleteById(id);
        }

        return order;
    }
}
