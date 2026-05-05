package com.hqd.teashopping.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.dto.ChangePasswordRequest;
import com.hqd.teashopping.dto.LoginRequest;
import com.hqd.teashopping.dto.RegisterRequest;
import com.hqd.teashopping.entity.User;
import com.hqd.teashopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*") // 允许跨域，生产环境应该限制具体域名
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * POST /api/v1/users/login
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 用户注册
     * POST /api/v1/users/register
     */
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        
        boolean success = userService.register(user);
        
        Map<String, Object> data = new HashMap<>();
        if (success) {
            data.put("userId", user.getId());
            data.put("username", user.getUsername());
            return Result.success(data);
        } else {
            return Result.error("用户名已存在");
        }
    }

    /**
     * 获取当前用户信息
     * GET /api/v1/users/me
     */
    @GetMapping("/me")
    public Result<User> getCurrentUser(@RequestParam(required = false) Long userId,
                                       @RequestHeader(value = "Authorization", required = false) String token) {
        // 如果提供了 userId，直接使用
        if (userId != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                return Result.success(user);
            } else {
                return Result.error("用户不存在");
            }
        }
        
        // 否则从 token 中解析（简化处理，实际应该验证 token）
        if (token != null && token.startsWith("Bearer ")) {
            // TODO: 从 token 中解析 userId
            return Result.error("请提供 userId 参数");
        }
        
        return Result.error("请提供 userId 参数或有效的 Authorization 头");
    }

    /**
     * 更新用户信息
     * PUT /api/v1/users/me
     */
    @PutMapping("/me")
    public Result<Void> updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 修改密码
     * PUT /api/v1/users/password
     */
    @PutMapping("/password")
    public Result<Void> changePassword(@RequestBody ChangePasswordRequest request,
                                       @RequestParam Long userId) {
        boolean success = userService.changePassword(userId, 
                                                     request.getOldPassword(), 
                                                     request.getNewPassword());
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("原密码错误");
        }
    }
}