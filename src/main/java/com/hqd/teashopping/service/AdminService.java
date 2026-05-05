package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.entity.Admin;
import com.hqd.teashopping.entity.Order;
import com.hqd.teashopping.mapper.AdminMapper;
import com.hqd.teashopping.mapper.OrderMapper;
import com.hqd.teashopping.mapper.ProductMapper;
import com.hqd.teashopping.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 管理员服务类
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 管理员登录
     * @param username 账号
     * @param password 密码（明文，实际应使用 BCrypt 比对）
     * @return 登录成功的管理员信息（含 token），失败返回 null
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Admin login(String username, String password) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        Admin admin = adminMapper.selectOne(wrapper);

        if (admin != null) {
            boolean matches;
            if (admin.getPassword().startsWith("$2a$")) {
                matches = passwordEncoder.matches(password, admin.getPassword());
            } else {
                matches = admin.getPassword().equals(password);
            }
            if (matches) {
                admin.setPassword(null);
                admin.setToken(UUID.randomUUID().toString());
                return admin;
            }
        }
        return null;
    }

    /**
     * 创建管理员
     * @param admin 管理员信息
     * @return 是否成功
     */
    public boolean createAdmin(Admin admin) {
        // 检查用户名是否存在
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, admin.getUsername());
        Long count = adminMapper.selectCount(wrapper);
        if (count > 0) {
            return false;
        }
        // BCrypt 加密密码
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setStatus(1);
        admin.setRole(admin.getRole() != null ? admin.getRole() : 1);
        int result = adminMapper.insert(admin);
        return result > 0;
    }

    /**
     * 获取管理员列表
     * @return 管理员列表
     */
    public List<Admin> listAdmins() {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Admin::getCreateTime);
        List<Admin> list = adminMapper.selectList(wrapper);
        for (Admin admin : list) {
            admin.setPassword(null);
        }
        return list;
    }

    /**
     * 获取后台统计数据
     * @return 统计数据
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(LocalTime.MAX);

        // 今日订单数（排除已取消）
        LambdaQueryWrapper<Order> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.ne(Order::getStatus, 4)
                .between(Order::getCreateTime, todayStart, todayEnd);
        long todayOrders = orderMapper.selectCount(orderWrapper);
        stats.put("totalOrders", todayOrders);

        // 今日销售额（已支付且未取消订单：status 1-3）
        LambdaQueryWrapper<Order> salesWrapper = new LambdaQueryWrapper<>();
        salesWrapper.between(Order::getStatus, 1, 3)
                .between(Order::getCreateTime, todayStart, todayEnd);
        List<Order> paidOrders = orderMapper.selectList(salesWrapper);
        BigDecimal totalSales = BigDecimal.ZERO;
        for (Order o : paidOrders) {
            if (o.getPayAmount() != null) {
                totalSales = totalSales.add(o.getPayAmount());
            }
        }
        stats.put("totalSales", totalSales);

        // 用户总数
        long totalUsers = userMapper.selectCount(null);
        stats.put("totalUsers", totalUsers);

        // 商品总数
        long totalProducts = productMapper.selectCount(null);
        stats.put("totalProducts", totalProducts);

        // 近7天销售趋势
        List<Map<String, Object>> trend = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDateTime dayStart = date.atStartOfDay();
            LocalDateTime dayEnd = date.atTime(LocalTime.MAX);

            LambdaQueryWrapper<Order> dayWrapper = new LambdaQueryWrapper<>();
            dayWrapper.between(Order::getStatus, 1, 3)
                    .between(Order::getCreateTime, dayStart, dayEnd);
            List<Order> dayOrders = orderMapper.selectList(dayWrapper);
            BigDecimal daySales = BigDecimal.ZERO;
            for (Order o : dayOrders) {
                if (o.getPayAmount() != null) {
                    daySales = daySales.add(o.getPayAmount());
                }
            }
            Map<String, Object> dayMap = new HashMap<>();
            dayMap.put("date", date.format(fmt));
            dayMap.put("sales", daySales);
            dayMap.put("orders", dayOrders.size());
            trend.add(dayMap);
        }
        stats.put("trend", trend);

        return stats;
    }
}
