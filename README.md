# 潮茶商城系统 - 后端代码实现说明

**版本号：** V1.1  
**日期：** 2026 年 5 月 5 日  

## 项目概述
基于 Spring Boot 4.0.4 + MyBatis Plus 的茶叶电商平台后端系统

## 技术栈
- **后端框架**: Spring Boot 4.0.4
- **持久层**: MyBatis Plus 3.5.7
- **数据库**: MySQL 8.0+
- **分页插件**: PageHelper 2.1.0
- **工具类库**: Lombok
- **JDK 版本**: Java 17

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

### 2. 商品模块 ✅
- 获取商品列表（支持分页、筛选、搜索）（GET /api/v1/products）
- 获取商品详情（GET /api/v1/products/{id}）
- 管理员添加商品（POST /api/v1/admin/products）
- 管理员更新商品（PUT /api/v1/admin/products/{id}）
- 管理员删除商品（DELETE /api/v1/admin/products/{id}）

**实现文件：**
- `entity/Product.java` - 商品实体
- `entity/Category.java` - 分类实体
- `mapper/ProductMapper.java` - 商品 Mapper
- `service/ProductService.java` - 商品服务
- `controller/ProductController.java` - 商品控制器

### 3. 购物车模块 ✅
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

### 4. 其他实体类 ✅
- `entity/Order.java` - 订单实体
- `entity/OrderItem.java` - 订单项实体
- `entity/Payment.java` - 支付信息实体
- `entity/Review.java` - 评价实体
- `entity/Address.java` - 收货地址实体

**对应的 Mapper 接口：**
- `OrderMapper.java`
- `OrderItemMapper.java`
- `PaymentMapper.java`
- `ReviewMapper.java`
- `AddressMapper.java`

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
│   │   └── MybatisPlusConfig.java
│   ├── controller/         # 控制器
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   └── CartController.java
│   ├── dto/                # 数据传输对象
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   └── ChangePasswordRequest.java
│   ├── entity/             # 实体类
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   └── ...
│   ├── mapper/             # Mapper 接口
│   │   ├── UserMapper.java
│   │   ├── ProductMapper.java
│   │   └── ...
│   ├── service/            # 服务层
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   └── CartItemService.java
│   └── TeaShoppingApplication.java
├── src/main/resources/
│   ├── application.properties  # 配置文件
│   └── mapper/                 # MyBatis XML（如有需要）
└── database/
    └── schema.sql              # 数据库初始化脚本
```

## 当前功能状态 ✅
以下功能模块已实现并可正常使用：
1. 订单模块（OrderController, OrderService）✅
2. 支付模块（PaymentController, PaymentService）✅
3. 评价模块（ReviewController, ReviewService）✅
4. 地址管理模块（AddressController, AddressService）✅
5. 后台管理模块（AdminController）✅
6. 分类管理（CategoryService）✅
7. 轮播图管理（BannerController, BannerImageController）✅

## 注意事项
1. 密码加密目前使用 MD5+ 盐值，生产环境建议使用 BCrypt
2. Token 生成使用 UUID，生产环境建议使用 JWT
3. 跨域配置允许所有来源，生产环境应该限制具体域名
4. 缺少全局异常处理，建议添加 @RestControllerAdvice
5. 缺少拦截器/过滤器进行权限验证

## 下一步计划
1. 实现订单相关功能
2. 集成第三方支付（微信/支付宝）
3. 添加全局异常处理
4. 实现权限认证（Spring Security 或 JWT）
5. 添加日志记录（使用 Logback）
6. 编写单元测试
