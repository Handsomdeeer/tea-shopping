package com.hqd.teashopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面路由控制器
 * 负责将所有前端页面路径映射到对应的 Thymeleaf 模板
 */
@Controller
public class PageController {

    /* ==================== 首页 ==================== */

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /* ==================== 用户认证 ==================== */

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /* ==================== 商品 ==================== */

    @GetMapping("/product/list")
    public String productList() {
        return "product/list";
    }

    @GetMapping("/product/detail")
    public String productDetail() {
        return "product/detail";
    }

    /* ==================== 购物车 ==================== */

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    /* ==================== 订单 ==================== */

    @GetMapping("/order/list")
    public String orderList() {
        return "order/list";
    }

    @GetMapping("/order/detail")
    public String orderDetail() {
        return "order/detail";
    }

    @GetMapping("/order/pay")
    public String orderPay() {
        return "order/pay";
    }

    @GetMapping("/order/confirm")
    public String orderConfirm() {
        return "order/confirm";
    }

    /* ==================== 评价 ==================== */

    @GetMapping("/review")
    public String review() {
        return "review";
    }

    /* ==================== 用户中心 ==================== */

    @GetMapping("/user/profile")
    public String userProfile() {
        return "user/profile";
    }

    @GetMapping("/user/address")
    public String userAddress() {
        return "user/address";
    }

    @GetMapping("/user/password")
    public String userPassword() {
        return "user/password";
    }

    /* ==================== 管理后台 ==================== */

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/product/list")
    public String adminProductList() {
        return "admin/product/list";
    }

    @GetMapping("/admin/product/form")
    public String adminProductForm() {
        return "admin/product/form";
    }

    @GetMapping("/admin/order/list")
    public String adminOrderList() {
        return "admin/order/list";
    }

    @GetMapping("/admin/category/list")
    public String adminCategoryList() {
        return "admin/category/list";
    }

    @GetMapping("/admin/payment")
    public String adminPayment() {
        return "admin/payment";
    }

    @GetMapping("/admin/banner/list")
    public String adminBannerList() {
        return "admin/banner/list";
    }

    @GetMapping("/admin/admin")
    public String adminAdmin() {
        return "admin/admin";
    }
}
