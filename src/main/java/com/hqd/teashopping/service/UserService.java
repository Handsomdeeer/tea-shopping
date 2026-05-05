package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.entity.User;
import com.hqd.teashopping.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用户服务类
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码（明文）
     * @return 登录成功的用户信息（不含密码），失败返回 null
     */
    public User login(String username, String password) {
        // 查询数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username)
               .eq(User::getStatus, 1); // 只查询正常状态的用户
        
        User user = userMapper.selectOne(wrapper);
        
        // 验证密码（BCrypt）
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        
        if (user != null) {
            // 清除敏感信息
            user.setPassword(null);
            // 生成 token（实际项目应该使用 JWT）
            user.setToken(UUID.randomUUID().toString());
        }
        
        return user;
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean register(User user) {
        // 检查用户名是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        
        if (count > 0) {
            return false; // 用户名已存在
        }
        
        // 加密密码（BCrypt）
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1); // 默认正常状态
        
        // 手动设置时间戳（自动填充未生效）
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        int result = userMapper.insert(user);
        return result > 0;
    }

    /**
     * 根据 ID 获取用户
     * @param id 用户 ID
     * @return 用户信息
     */
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null); // 不返回密码
        }
        return user;
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean updateUser(User user) {
        // 不允许更新密码和状态
        user.setPassword(null);
        user.setStatus(null);
        
        // 手动设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        
        int result = userMapper.updateById(user);
        return result > 0;
    }

    /**
     * 修改密码
     * @param userId 用户 ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        
        // 更新密码
        User newUser = new User();
        newUser.setId(userId);
        newUser.setPassword(passwordEncoder.encode(newPassword));
        newUser.setUpdateTime(LocalDateTime.now());
        
        int result = userMapper.updateById(newUser);
        return result > 0;
    }


}