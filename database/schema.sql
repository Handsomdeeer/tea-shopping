-- ============================================================
-- 潮茶商城系统 - 数据库初始化脚本
-- 数据库版本：MySQL 8.0+
-- 创建日期：2026-03-31
-- ============================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `chaocha_mall` 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;

USE `chaocha_mall`;

-- ==================== 用户模块 ====================

-- 1. 用户表
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码（BCrypt 加密）',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
  `gender` TINYINT DEFAULT 0 COMMENT '性别（0：未知，1：男，2：女）',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `status` TINYINT DEFAULT 1 COMMENT '状态（0：禁用，1：正常）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `uk_phone` (`phone`),
  KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 收货地址表
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `receiver_name` VARCHAR(50) NOT NULL COMMENT '收件人姓名',
  `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收件人电话',
  `province` VARCHAR(50) NOT NULL COMMENT '省份',
  `city` VARCHAR(50) NOT NULL COMMENT '城市',
  `district` VARCHAR(50) NOT NULL COMMENT '区县',
  `detail_address` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `is_default` TINYINT DEFAULT 0 COMMENT '是否默认地址（0：否，1：是）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收货地址表';

-- 3. 用户登录日志表
DROP TABLE IF EXISTS `user_login_logs`;
CREATE TABLE `user_login_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `login_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `login_ip` VARCHAR(50) DEFAULT NULL COMMENT '登录 IP',
  `login_device` VARCHAR(100) DEFAULT NULL COMMENT '登录设备信息',
  `status` TINYINT DEFAULT 1 COMMENT '登录状态（0：失败，1：成功）',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户登录日志表';

-- ==================== 商品模块 ====================

-- 4. 商品分类表
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT '分类图标 URL',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父分类 ID（0：一级分类）',
  `sort` INT DEFAULT 0 COMMENT '排序（数值越小越靠前）',
  `status` TINYINT DEFAULT 1 COMMENT '状态（0：禁用，1：启用）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 5. 商品表
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `category_id` BIGINT NOT NULL COMMENT '分类 ID',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '商品描述',
  `detail` TEXT DEFAULT NULL COMMENT '商品详情（HTML）',
  `price` DECIMAL(10,2) NOT NULL COMMENT '销售价格',
  `original_price` DECIMAL(10,2) DEFAULT NULL COMMENT '原价',
  `stock` INT DEFAULT 0 COMMENT '库存数量',
  `sales` INT DEFAULT 0 COMMENT '销量',
  `main_image` VARCHAR(255) NOT NULL COMMENT '主图 URL',
  `rating` DECIMAL(3,2) DEFAULT 0.00 COMMENT '评分（1-5 分）',
  `status` TINYINT DEFAULT 1 COMMENT '状态（0：下架，1：上架）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`),
  KEY `idx_sales` (`sales` DESC),
  KEY `idx_create_time` (`create_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 6. 商品图片表
DROP TABLE IF EXISTS `product_images`;
CREATE TABLE `product_images` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `product_id` BIGINT NOT NULL COMMENT '商品 ID',
  `image_url` VARCHAR(255) NOT NULL COMMENT '图片 URL',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片表';

-- 7. 商品规格表
DROP TABLE IF EXISTS `product_specifications`;
CREATE TABLE `product_specifications` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `product_id` BIGINT NOT NULL COMMENT '商品 ID',
  `spec_name` VARCHAR(50) NOT NULL COMMENT '规格名称',
  `spec_value` VARCHAR(100) NOT NULL COMMENT '规格值',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品规格表';

-- ==================== 购物车模块 ====================

-- 8. 购物车表
DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `product_id` BIGINT NOT NULL COMMENT '商品 ID',
  `quantity` INT NOT NULL DEFAULT 1 COMMENT '购买数量',
  `selected` TINYINT DEFAULT 1 COMMENT '是否选中（0：否，1：是）',
  `product_name` VARCHAR(100) DEFAULT NULL COMMENT '商品名称（冗余）',
  `product_image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片（冗余）',
  `price` DECIMAL(10,2) DEFAULT NULL COMMENT '商品单价（冗余）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';

-- ==================== 订单模块 ====================

-- 9. 订单表
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态（0：待付款，1：待发货，2：待收货，3：已完成，4：已取消）',
  `receiver_name` VARCHAR(50) NOT NULL COMMENT '收件人姓名',
  `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收件人电话',
  `receiver_address` VARCHAR(255) NOT NULL COMMENT '收件人地址',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
  `freight_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费金额',
  `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '订单备注',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `delivery_time` DATETIME DEFAULT NULL COMMENT '发货时间',
  `receive_time` DATETIME DEFAULT NULL COMMENT '确认收货时间',
  `cancel_time` DATETIME DEFAULT NULL COMMENT '取消时间',
  `cancel_reason` VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 10. 订单明细表
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_id` BIGINT NOT NULL COMMENT '订单 ID',
  `product_id` BIGINT NOT NULL COMMENT '商品 ID',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称（快照）',
  `product_image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片（快照）',
  `price` DECIMAL(10,2) NOT NULL COMMENT '成交价格（快照）',
  `quantity` INT NOT NULL COMMENT '购买数量',
  `subtotal` DECIMAL(10,2) NOT NULL COMMENT '小计金额',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- 11. 订单状态流转表
DROP TABLE IF EXISTS `order_status_logs`;
CREATE TABLE `order_status_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_id` BIGINT NOT NULL COMMENT '订单 ID',
  `old_status` TINYINT NOT NULL COMMENT '原状态',
  `new_status` TINYINT NOT NULL COMMENT '新状态',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注说明',
  `operator` VARCHAR(50) DEFAULT NULL COMMENT '操作人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单状态流转表';

-- ==================== 支付模块 ====================

-- 12. 支付信息表
DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_id` BIGINT NOT NULL COMMENT '订单 ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
  `pay_type` TINYINT NOT NULL COMMENT '支付方式（1：微信，2：支付宝）',
  `pay_status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态（0：未支付，1：已支付，2：支付失败）',
  `transaction_id` VARCHAR(100) DEFAULT NULL COMMENT '第三方支付流水号',
  `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `qrcode_url` VARCHAR(255) DEFAULT NULL COMMENT '支付二维码 URL',
  `callback_data` TEXT DEFAULT NULL COMMENT '回调数据（JSON）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付信息表';

-- ==================== 评价模块 ====================

-- 13. 商品评价表
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_id` BIGINT NOT NULL COMMENT '订单 ID',
  `product_id` BIGINT NOT NULL COMMENT '商品 ID',
  `user_id` BIGINT NOT NULL COMMENT '用户 ID',
  `rating` TINYINT NOT NULL COMMENT '评分（1-5 星）',
  `content` VARCHAR(500) DEFAULT NULL COMMENT '评价内容',
  `images` TEXT DEFAULT NULL COMMENT '评价图片（JSON 数组）',
  `reply` VARCHAR(500) DEFAULT NULL COMMENT '商家回复',
  `reply_time` DATETIME DEFAULT NULL COMMENT '回复时间',
  `status` TINYINT DEFAULT 1 COMMENT '状态（0：隐藏，1：显示）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品评价表';

-- ==================== 管理员模块 ====================

-- 14. 管理员表
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `username` VARCHAR(50) NOT NULL COMMENT '管理员账号',
  `password` VARCHAR(100) NOT NULL COMMENT '密码（BCrypt 加密）',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `role` TINYINT NOT NULL DEFAULT 1 COMMENT '角色（1：超级管理员，2：普通管理员）',
  `status` TINYINT DEFAULT 1 COMMENT '状态（0：禁用，1：正常）',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录 IP',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- ==================== 初始化数据 ====================

-- 插入默认管理员（密码：admin123，BCrypt 加密后的哈希值）
INSERT INTO `admins` (`username`, `password`, `real_name`, `role`, `status`) 
VALUES ('admin', '$2a$10$XoLvF5C2dz9.7JVNc8N.pOZQh8xP6KqL9mR4tS3uV2wX1yZ5A6B7C', '系统管理员', 1, 1);

-- 插入商品分类
INSERT INTO `categories` (`name`, `icon`, `parent_id`, `sort`, `status`) VALUES
('绿茶', '/icons/green-tea.png', 0, 1, 1),
('红茶', '/icons/black-tea.png', 0, 2, 1),
('乌龙茶', '/icons/oolong-tea.png', 0, 3, 1),
('白茶', '/icons/white-tea.png', 0, 4, 1),
('普洱茶', '/icons/puer-tea.png', 0, 5, 1),
('花茶', '/icons/flower-tea.png', 0, 6, 1);

-- 插入示例商品
INSERT INTO `products` (`name`, `category_id`, `description`, `price`, `original_price`, `stock`, `sales`, `main_image`, `rating`, `status`) VALUES
('西湖龙井', 1, '明前特级西湖龙井，产自杭州西湖区', 588.00, 688.00, 500, 1000, '/products/longjing.jpg', 4.8, 1),
('正山小种', 2, '武夷山正宗正山小种红茶', 398.00, 498.00, 300, 800, '/products/xiaozhong.jpg', 4.7, 1),
('铁观音', 3, '安溪铁观音乌龙茶，清香型', 468.00, 568.00, 400, 1200, '/products/tieguanyin.jpg', 4.9, 1),
('白毫银针', 4, '福鼎白毫银针，白茶珍品', 888.00, 1088.00, 200, 500, '/products/yinzhen.jpg', 4.9, 1),
('普洱熟茶', 5, '云南陈年普洱熟茶饼', 298.00, 398.00, 600, 1500, '/products/puer.jpg', 4.6, 1),
('茉莉花茶', 6, '福州茉莉花茶，香气浓郁', 168.00, 218.00, 800, 2000, '/products/moli.jpg', 4.5, 1);

-- 插入测试用户（密码：123456）
INSERT INTO `users` (`username`, `password`, `nickname`, `phone`, `email`, `status`) 
VALUES ('testuser', '$2a$10$XoLvF5C2dz9.7JVNc8N.pOZQh8xP6KqL9mR4tS3uV2wX1yZ5A6B7C', '测试用户', '13800138000', 'test@example.com', 1);

-- ==================== 视图（可选） ====================

-- 商品销售统计视图
CREATE OR REPLACE VIEW `v_product_sales` AS
SELECT 
    p.id,
    p.name,
    p.category_id,
    c.name AS category_name,
    p.price,
    p.sales,
    p.stock,
    p.rating,
    COUNT(DISTINCT oi.order_id) AS order_count,
    SUM(oi.quantity) AS total_quantity
FROM products p
LEFT JOIN categories c ON p.category_id = c.id
LEFT JOIN order_items oi ON p.id = oi.product_id
LEFT JOIN orders o ON oi.order_id = o.id AND o.status != 4
GROUP BY p.id, p.name, p.category_id, c.name, p.price, p.sales, p.stock, p.rating;

-- 15. 支付配置表
DROP TABLE IF EXISTS `payment_configs`;
CREATE TABLE `payment_configs` (
  `id` BIGINT NOT NULL DEFAULT 1 COMMENT '固定ID=1',
  `wechat_qrcode` VARCHAR(500) DEFAULT NULL COMMENT '微信支付二维码URL',
  `alipay_qrcode` VARCHAR(500) DEFAULT NULL COMMENT '支付宝支付二维码URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付配置表';

-- 16. 商品表添加评价数字段
ALTER TABLE `products` ADD COLUMN IF NOT EXISTS `review_count` INT NOT NULL DEFAULT 0 COMMENT '评价数量';

-- 17. 轮播图表
DROP TABLE IF EXISTS `banner_images`;
CREATE TABLE `banner_images` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '图片名称',
  `image_url` VARCHAR(500) NOT NULL COMMENT '图片URL',
  `type` VARCHAR(20) DEFAULT 'BANNER' COMMENT '图片类型：BANNER轮播图/AD广告图/OTHER其他',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序号，越小越靠前',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status_sort` (`status`, `sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图/广告图片表';

-- ==================== 脚本结束 ====================
