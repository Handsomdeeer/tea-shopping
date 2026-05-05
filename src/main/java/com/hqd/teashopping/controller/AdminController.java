package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.Admin;
import com.hqd.teashopping.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * POST /admin/login
     */
    @PostMapping("/login")
    public Result<Admin> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            return Result.success(admin);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 获取管理员列表
     * GET /api/v1/admin/list
     */
    @GetMapping("/list")
    public Result<List<Admin>> listAdmins() {
        List<Admin> list = adminService.listAdmins();
        return Result.success(list);
    }

    /**
     * 创建管理员
     * POST /api/v1/admin
     */
    @PostMapping
    public Result<Void> createAdmin(@RequestBody Admin admin) {
        boolean success = adminService.createAdmin(admin);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("用户名已存在");
        }
    }

    /**
     * 获取后台统计数据
     * GET /admin/statistics
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = adminService.getStatistics();
        return Result.success(stats);
    }
}
