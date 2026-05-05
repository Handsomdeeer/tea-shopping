# 潮茶商城系统

**版本号：** V1.2  
**日期：** 2026 年 5 月 5 日

## 项目概述
基于 Spring Boot + MyBatis Plus 的茶叶电商平台后端系统，提供完整的电商功能，包括用户管理、商品管理、购物车、订单管理、支付、评价等模块。

## 技术栈
- **后端框架**: Spring Boot 3.x
- **持久层**: MyBatis Plus 3.5.7
- **数据库**: MySQL 8.0+
- **工具类库**: Lombok, Jakarta Validation
- **JDK 版本**: Java 17
- **模板引擎**: Thymeleaf（前端页面）
- **文件存储**: 阿里云 OSS

## 已完成的功能模块

### 1. 用户模块 ✅
- 用户登录（POST /api/v1/users/login）
- 用户注册（POST /api/v1/users/register）
- 获取用户信息（GET /api/v1/users/me）
- 更新用户信息（PUT /api/v1/users/me）
- 修改密码（PUT /api/v1/users/password）

**实现文件：**
- `entity/User.java` - 用户实体
- `dto/LoginRequest.java` - 登录请求 DTO
- `dto/RegisterRequest.java` - 注册请求 DTO
- `dto/ChangePasswordRequest.java` - 修改密码请求 DTO
- `mapper/UserMapper.java` - 用户 Mapper
- `service/UserService.java` - 用户服务
- `controller/UserController.java` - 用户控制器

### 2. 收货地址模块 ✅
- 获取收货地址列表（GET /api/v1/addresses）
- 添加收货地址（POST /api/v1/addresses）
- 更新收货地址（PUT /api/v1/addresses/{id}）
- 删除收货地址（DELETE /api/v1/addresses/{id}）

**实现文件：**
- `entity/Address.java` - 地址实体
- `mapper/AddressMapper.java` - 地址 Mapper
- `service/AddressService.java` - 地址服务
- `controller/AddressController.java` - 地址控制器

### 3. 商品模块 ✅
- 获取商品分类列表（GET /api/v1/categories）
- 获取商品列表（支持分页、筛选、搜索）（GET /api/v1/products）
- 获取商品详情（GET /api/v1/products/{id}）
- 管理员添加商品（POST /api/v1/admin/products）
- 管理员更新商品（PUT /api/v1/admin/products/{id}）
- 管理员删除商品（DELETE /api/v1/admin/products/{id}）

**实现文件：**
- `entity/Product.java` - 商品实体
- `entity/Category.java` - 分类实体
- `mapper/ProductMapper.java` - 商品 Mapper
- `mapper/CategoryMapper.java` - 分类 Mapper
- `service/ProductService.java` - 商品服务
- `service/CategoryService.java` - 分类服务
- `controller/ProductController.java` - 商品控制器

### 4. 购物车模块 ✅
- 获取购物车列表（GET /api/v1/cart）
- 添加到购物车（POST /api/v1/cart）
- 更新购物车商品数量（PUT /api/v1/cart/{id}）
- 删除购物车商品（DELETE /api/v1/cart/{id}）
- 批量删除购物车商品（DELETE /api/v1/cart/batch）

**实现文件：**
- `entity/CartItem.java` - 购物车项实体
- `mapper/CartItemMapper.java` - 购物车 Mapper
- `service/CartItemService.java` - 购物车服务
- `controller/CartController.java` - 购物车控制器

### 5. 订单模块 ✅
- 创建订单（POST /api/v1/orders）
- 获取订单列表（GET /api/v1/orders）
- 获取订单详情（GET /api/v1/orders/{id}）
- 取消订单（PUT /api/v1/orders/{id}/cancel）
- 确认支付（PUT /api/v1/orders/{id}/pay）
- 确认收货（PUT /api/v1/orders/{id}/confirm）
- 管理员发货（PUT /api/v1/admin/orders/{id}/ship）

**实现文件：**
- `entity/Order.java` - 订单实体
- `entity/OrderItem.java` - 订单项实体
- `mapper/OrderMapper.java` - 订单 Mapper
- `mapper/OrderItemMapper.java` - 订单项 Mapper
- `service/OrderService.java` - 订单服务
- `controller/OrderController.java` - 订单控制器

### 6. 支付模块 ✅
- 生成支付二维码（POST /api/v1/payment/qrcode）
- 查询支付状态（GET /api/v1/payment/status/{orderId}）
- 支付配置管理（GET/PUT /api/v1/payment-config）

**实现文件：**
- `entity/Payment.java` - 支付信息实体
- `entity/PaymentConfig.java` - 支付配置实体
- `mapper/PaymentMapper.java` - 支付 Mapper
- `mapper/PaymentConfigMapper.java` - 支付配置 Mapper
- `service/PaymentService.java` - 支付服务
- `controller/PaymentController.java` - 支付控制器
- `controller/PaymentConfigController.java` - 支付配置控制器

### 7. 评价模块 ✅
- 提交评价（POST /api/v1/reviews）
- 获取商品评价列表（GET /api/v1/products/{productId}/reviews）

**实现文件：**
- `entity/Review.java` - 评价实体
- `mapper/ReviewMapper.java` - 评价 Mapper
- `service/ReviewService.java` - 评价服务
- `controller/ReviewController.java` - 评价控制器

### 8. 轮播图模块 ✅
- 获取轮播图列表（GET /api/v1/banners）
- 获取广告图列表（GET /api/v1/banners/ad）
- 管理员获取轮播图列表（GET /api/v1/admin/banners）
- 管理员添加轮播图（POST /api/v1/admin/banners）
- 管理员更新轮播图（PUT /api/v1/admin/banners/{id}）
- 管理员删除轮播图（DELETE /api/v1/admin/banners/{id}）
- 上传轮播图（POST /api/v1/admin/banners/upload）

**实现文件：**
- `entity/BannerImage.java` - 轮播图实体
- `mapper/BannerImageMapper.java` - 轮播图 Mapper
- `service/BannerImageService.java` - 轮播图服务
- `controller/BannerController.java` - 前台轮播图控制器
- `controller/BannerImageController.java` - 后台轮播图控制器

### 9. 后台管理模块 ✅
- 管理员登录（POST /api/v1/admin/login）
- 获取管理员列表（GET /api/v1/admin/list）
- 创建管理员（POST /api/v1/admin）
- 获取统计数据（GET /api/v1/admin/statistics）

**实现文件：**
- `entity/Admin.java` - 管理员实体
- `mapper/AdminMapper.java` - 管理员 Mapper
- `service/AdminService.java` - 管理员服务
- `controller/AdminController.java` - 管理员控制器

## 数据库配置

### 1. 初始化数据库
执行 SQL 脚本创建数据库和表：
```bash
mysql -u root -p < database/schema.sql
```

### 2. 配置数据库连接
编辑 `src/main/resources/application.properties`：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chaocha_mall?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的密码
```

## 运行项目

### 方式一：使用 Maven
```bash
mvn spring-boot:run
```

### 方式二：使用 IDEA
1. 打开项目
2. 右键 `TeaShoppingApplication.java`
3. 选择 "Run 'TeaShoppingApplication'"

### 方式三：打包后运行
```bash
mvn clean package
java -jar target/tea-shopping-0.0.1-SNAPSHOT.jar
```

## API 测试示例

### 1. 用户登录
```bash
curl -X POST http://localhost:8080/api/v1/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "123456"
  }'
```

### 2. 用户注册
```bash
curl -X POST http://localhost:8080/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser2026",
    "password": "Abc123456",
    "phone": "13800138000",
    "email": "test@example.com"
  }'
```

### 3. 获取商品列表
```bash
curl -X GET "http://localhost:8080/api/v1/products?pageNum=1&pageSize=10"
```

### 4. 添加到购物车
```bash
curl -X POST http://localhost:8080/api/v1/cart \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productId": 1,
    "quantity": 2
  }'
```

## 项目结构
```
tea-shopping/
├── src/main/java/com/hqd/teashopping/
│   ├── common/              # 公共类
│   │   └── Result.java     # 统一响应结果
│   ├── config/             # 配置类
│   │   ├── MybatisPlusConfig.java    # MyBatis Plus 配置
│   │   ├── MyMetaObjectHandler.java  # 自动填充处理器
│   │   └── OssProperties.java        # OSS 配置属性
│   ├── controller/         # 控制器
│   │   ├── UserController.java       # 用户控制器
│   │   ├── AddressController.java    # 地址控制器
│   │   ├── ProductController.java    # 商品控制器
│   │   ├── CategoryController.java   # 分类控制器
│   │   ├── CartController.java       # 购物车控制器
│   │   ├── OrderController.java      # 订单控制器
│   │   ├── PaymentController.java    # 支付控制器
│   │   ├── PaymentConfigController.java  # 支付配置控制器
│   │   ├── ReviewController.java     # 评价控制器
│   │   ├── BannerController.java     # 前台轮播图控制器
│   │   ├── BannerImageController.java    # 后台轮播图控制器
│   │   ├── AdminController.java      # 管理员控制器
│   │   ├── FileUploadController.java # 文件上传控制器
│   │   └── PageController.java       # 页面控制器（Thymeleaf）
│   ├── dto/                # 数据传输对象
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   └── ChangePasswordRequest.java
│   ├── entity/             # 实体类
│   │   ├── User.java
│   │   ├── Address.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Payment.java
│   │   ├── PaymentConfig.java
│   │   ├── Review.java
│   │   ├── BannerImage.java
│   │   └── Admin.java
│   ├── mapper/             # Mapper 接口
│   │   ├── UserMapper.java
│   │   ├── AddressMapper.java
│   │   ├── ProductMapper.java
│   │   ├── CategoryMapper.java
│   │   ├── CartItemMapper.java
│   │   ├── OrderMapper.java
│   │   ├── OrderItemMapper.java
│   │   ├── PaymentMapper.java
│   │   ├── PaymentConfigMapper.java
│   │   ├── ReviewMapper.java
│   │   ├── BannerImageMapper.java
│   │   └── AdminMapper.java
│   ├── service/            # 服务层
│   │   ├── UserService.java
│   │   ├── AddressService.java
│   │   ├── ProductService.java
│   │   ├── CategoryService.java
│   │   ├── CartItemService.java
│   │   ├── OrderService.java
│   │   ├── PaymentService.java
│   │   ├── PaymentConfigService.java
│   │   ├── ReviewService.java
│   │   ├── BannerImageService.java
│   │   └── AdminService.java
│   ├── utils/              # 工具类
│   │   └── AliOssUtil.java # 阿里云 OSS 工具
│   └── TeaShoppingApplication.java
├── src/main/resources/
│   ├── application.properties  # 配置文件
│   ├── static/                 # 静态资源
│   │   ├── css/
│   │   └── js/
│   └── templates/              # Thymeleaf 模板
│       ├── admin/              # 后台管理页面
│       ├── user/               # 用户中心页面
│       ├── order/              # 订单页面
│       ├── product/            # 商品页面
│       ├── fragments/          # 公共片段
│       ├── index.html          # 首页
│       ├── login.html          # 登录页
│       ├── register.html       # 注册页
│       ├── cart.html           # 购物车页
│       └── review.html         # 评价页
└── database/
    └── schema.sql              # 数据库初始化脚本
```

## 当前功能状态

以下功能模块已实现并可正常使用：

| 模块 | 状态 | 说明 |
|------|------|------|
| 用户管理 | ✅ | 注册、登录、个人信息管理、密码修改 |
| 收货地址 | ✅ | 增删改查、默认地址设置 |
| 商品管理 | ✅ | 商品CRUD、分类管理、搜索筛选 |
| 购物车 | ✅ | 增删改查、批量操作 |
| 订单管理 | ✅ | 创建、查询、取消、支付、确认收货、发货 |
| 支付管理 | ✅ | 二维码生成、支付状态查询、支付配置 |
| 评价管理 | ✅ | 提交评价、查看评价列表 |
| 轮播图管理 | ✅ | 前台展示、后台CRUD、图片上传 |
| 后台管理 | ✅ | 管理员登录、数据统计、管理员管理 |
| 文件上传 | ✅ | 阿里云OSS集成 |
| 页面渲染 | ✅ | Thymeleaf模板引擎，完整的前后台页面 |

## 注意事项

1. **密码加密**：目前使用 BCrypt 加密算法，确保密码安全存储
2. **Token认证**：当前使用简化认证机制，生产环境建议集成 JWT 或 Spring Security
3. **跨域配置**：开发环境允许所有来源，生产环境应该限制具体域名
4. **全局异常处理**：建议使用 @RestControllerAdvice 添加统一异常处理
5. **权限验证**：建议添加拦截器/过滤器进行细粒度的权限控制
6. **日志记录**：建议集成 Logback 进行详细的日志记录
7. **数据库连接**：请在 application.properties 中配置正确的数据库连接信息
8. **OSS配置**：使用阿里云OSS需要在 application.properties 中配置相关参数

## 下一步计划

1. 集成 Spring Security + JWT 实现完善的认证授权机制
2. 集成真实的第三方支付平台（微信支付、支付宝SDK）
3. 添加全局异常处理和统一响应封装
4. 实现细粒度的权限控制和访问拦截
5. 添加完善的日志记录和监控
6. 编写单元测试和集成测试
7. 优化数据库查询性能，添加缓存机制（Redis）
8. 实现订单超时自动取消功能
9. 添加商品库存锁定机制
10. 实现消息通知功能（短信、邮件）
