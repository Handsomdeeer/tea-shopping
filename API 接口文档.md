# 潮茶商城系统 - API 接口文档

## ChaoCha E-Commerce System - API Documentation

**版本号：** V1.2  
**日期：** 2026 年 5 月 5 日  
**基础路径：** `/api/v1`

---

# 1. 概述

## 1.1 文档说明

本文档描述了潮茶商城系统的 RESTful API 接口规范，包含用户管理、商品管理、购物车、订单管理等核心功能模块的接口定义。

## 1.2 通信协议

- **协议**：HTTP/HTTPS
- **数据格式**：JSON
- **字符编码**：UTF-8
- **默认端口**：8080

## 1.3 响应格式

所有接口响应统一采用以下格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1711872000000
}
```

**字段说明：**

| 字段        | 类型      | 说明                   |
| --------- | ------- | -------------------- |
| code      | Integer | 响应状态码（200：成功，500：失败） |
| message   | String  | 响应消息                 |
| data      | Object  | 响应数据                 |
| timestamp | Long    | 响应时间戳                |

## 1.4 认证方式

除登录、注册等公开接口外，其他接口需要在请求头中携带 Token：

```
Authorization: Bearer {token}
```

---

# 2. 用户管理模块 (User)

## 2.1 用户登录

**接口标识：** SRS-INT-API-001

### 接口信息

- **路径：** `POST /api/v1/users/login`
- **描述：** 用户登录，获取访问令牌
- **权限：** 公开接口

### 请求参数

**请求体（JSON）：**

| 字段       | 类型     | 必填  | 说明                |
| -------- | ------ | --- | ----------------- |
| username | String | 是   | 用户名               |
| password | String | 是   | 密码（明文传输，建议 HTTPS） |

**请求示例：**

```json
{
  "username": "admin",
  "password": "123456"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "超级管理员",
    "avatar": null,
    "gender": 0,
    "email": null,
    "phone": "13800138000",
    "status": 1,
    "createTime": "2026-01-01T10:00:00",
    "updateTime": "2026-01-01T10:00:00"
  },
  "timestamp": 1711872000000
}
```

**失败响应：**

```json
{
  "code": 500,
  "message": "用户名或密码错误",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 2.2 用户注册

**接口标识：** SRS-INT-API-002

### 接口信息

- **路径：** `POST /api/v1/users/register`
- **描述：** 新用户注册账号
- **权限：** 公开接口

### 请求参数

**请求体（JSON）：**

| 字段         | 类型     | 必填  | 说明                |
| ---------- | ------ | --- | ----------------- |
| username   | String | 是   | 用户名（6-20 位字母数字组合） |
| password   | String | 是   | 密码（8-20 位，含字母和数字） |
| phone      | String | 是   | 手机号码              |
| verifyCode | String | 是   | 短信验证码             |

**请求示例：**

```json
{
  "username": "user2026",
  "password": "Abc123456",
  "phone": "13800138000",
  "verifyCode": "123456"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "id": 1001,
    "username": "user2026"
  },
  "timestamp": 1711872000000
}
```

---

## 2.3 获取当前用户信息

**接口标识：** SRS-INT-API-003

### 接口信息

- **路径：** `GET /api/v1/users/me`
- **描述：** 获取当前登录用户的个人信息
- **权限：** 需要认证

### 请求参数

**查询参数：**

| 参数       | 类型      | 必填  | 说明                   |
| -------- | ------- | --- | -------------------- |
| userId   | Long    | 否   | 用户ID（可选，用于测试）     |

**请求头：**

| 参数            | 类型     | 必填  | 说明                   |
| ------------- | ------ | --- | -------------------- |
| Authorization | String | 否   | Bearer Token（可选）    |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1001,
    "username": "user2026",
    "nickname": "茶友 2026",
    "avatar": "https://example.com/avatar/1001.jpg",
    "gender": 1,
    "email": "user@example.com",
    "phone": "13800138000",
    "createTime": "2026-01-01 10:00:00"
  },
  "timestamp": 1711872000000
}
```

---

## 2.4 更新用户信息

**接口标识：** SRS-INT-API-004

### 接口信息

- **路径：** `PUT /api/v1/users/me`
- **描述：** 更新当前用户的基本信息
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段          | 类型      | 必填  | 说明          |
| ----------- | ------- | --- | ----------- |
| id          | Long    | 是   | 用户ID        |
| nickname    | String  | 否   | 昵称          |
| avatar      | String  | 否   | 头像 URL      |
| gender      | Integer | 否   | 性别（0：未知，1：男，2：女） |
| email       | String  | 否   | 邮箱          |
| phone       | String  | 否   | 手机号         |

**请求示例：**

```json
{
  "id": 2,
  "nickname": "新茶友",
  "avatar": "https://example.com/new-avatar.jpg",
  "gender": 1,
  "email": "new@example.com",
  "phone": "13900139000"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 2.5 修改密码

**接口标识：** SRS-INT-API-005

### 接口信息

- **路径：** `PUT /api/v1/users/password`
- **描述：** 修改用户登录密码
- **权限：** 需要认证

### 请求参数

**查询参数：**

| 参数       | 类型      | 必填  | 说明                   |
| -------- | ------- | --- | -------------------- |
| userId   | Long    | 是   | 用户ID                 |

**请求体（JSON）：**

| 字段          | 类型     | 必填  | 说明  |
| ----------- | ------ | --- | --- |
| oldPassword | String | 是   | 原密码 |
| newPassword | String | 是   | 新密码 |

**请求示例：**

```
PUT /api/v1/users/password?userId=2
```

```json
{
  "oldPassword": "Old123456",
  "newPassword": "New123456"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "密码修改成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

# 3. 收货地址模块 (Address)

## 3.1 获取收货地址列表

**接口标识：** SRS-INT-API-006

### 接口信息

- **路径：** `GET /api/v1/addresses`
- **描述：** 获取当前用户的所有收货地址
- **权限：** 需要认证

### 请求参数

```json
{
    "userId": 2
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "receiverName": "张三",
      "receiverPhone": "13800138000",
      "province": "广东省",
      "city": "深圳市",
      "district": "南山区",
      "detailAddress": "科技园南区 A 栋 101",
      "isDefault": true
    }
  ],
  "timestamp": 1711872000000
}
```

---

## 3.2 添加收货地址

**接口标识：** SRS-INT-API-007

### 接口信息

- **路径：** `POST /api/v1/addresses`
- **描述：** 添加新的收货地址
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段            | 类型      | 必填  | 说明     |
| ------------- | ------- | --- | ------ |
| receiverName  | String  | 是   | 收件人姓名  |
| receiverPhone | String  | 是   | 收件人电话  |
| province      | String  | 是   | 省份     |
| city          | String  | 是   | 城市     |
| district      | String  | 是   | 区县     |
| detailAddress | String  | 是   | 详细地址   |
| isDefault     | Boolean | 否   | 是否默认地址 |

**请求示例：**

```json
请求参数
{
    "userId": 2
}
```

```json
请求体
{
  "receiverName": "李四",
  "receiverPhone": "13900139000",
  "province": "广东省",
  "city": "广州市",
  "district": "天河区",
  "detailAddress": "珠江新城 B 栋 202",
  "isDefault": false
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "添加成功",
  "data": {
    "id": 2
  },
  "timestamp": 1711872000000
}
```

---

## 3.3 更新收货地址

**接口标识：** SRS-INT-API-008

### 接口信息

- **路径：** `PUT /api/v1/addresses/{id}`
- **描述：** 更新指定收货地址
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：地址 ID

**请求体（JSON）：** 同添加地址

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 3.4 删除收货地址

**接口标识：** SRS-INT-API-009

### 接口信息

- **路径：** `DELETE /api/v1/addresses/{id}`
- **描述：** 删除指定收货地址
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：地址 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

# 4. 商品管理模块 (Product)

## 4.1 获取商品分类列表

**接口标识：** SRS-INT-API-010

### 接口信息

- **路径：** `GET /api/v1/categories`
- **描述：** 获取所有商品分类
- **权限：** 公开接口

### 请求参数

无

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "绿茶",
      "icon": "https://example.com/icons/green-tea.png",
      "sort": 1
    },
    {
      "id": 2,
      "name": "红茶",
      "icon": "https://example.com/icons/black-tea.png",
      "sort": 2
    }
  ],
  "timestamp": 1711872000000
}
```

---

## 4.2 获取商品列表

**接口标识：** SRS-INT-API-011

### 接口信息

- **路径：** `GET /api/v1/products`
- **描述：** 获取商品列表，支持筛选和排序
- **权限：** 公开接口

### 请求参数

**查询参数：**

| 参数         | 类型         | 必填  | 说明                                     |
| ---------- | ---------- | --- | -------------------------------------- |
| categoryId | Long       | 否   | 分类 ID                                  |
| keyword    | String     | 否   | 搜索关键词                                  |
| minPrice   | BigDecimal | 否   | 最低价格                                   |
| maxPrice   | BigDecimal | 否   | 最高价格                                   |
| sort       | String     | 否   | 排序字段（sales：销量，price：价格，create_time：时间） |
| order      | String     | 否   | 排序方式（asc：升序，desc：降序）                   |
| pageNum    | Integer    | 否   | 页码（默认 1）                               |
| pageSize   | Integer    | 否   | 每页数量（默认 10）                            |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "pageNum": 1,
    "pageSize": 10,
    "list": [
      {
        "id": 1,
        "name": "西湖龙井",
        "description": "明前特级西湖龙井",
        "price": 588.00,
        "imageUrl": "https://example.com/products/1.jpg",
        "sales": 1000,
        "stock": 500
      }
    ]
  },
  "timestamp": 1711872000000
}
```

---

## 4.3 获取商品详情

**接口标识：** SRS-INT-API-012

### 接口信息

- **路径：** `GET /api/v1/products/{id}`
- **描述：** 获取商品详细信息
- **权限：** 公开接口

### 请求参数

**路径参数：**

- id：商品 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "西湖龙井",
    "description": "明前特级西湖龙井，产自杭州西湖区",
    "detail": "详细介绍内容...",
    "categoryId": 1,
    "categoryName": "绿茶",
    "price": 588.00,
    "originalPrice": 688.00,
    "imageUrl": "https://example.com/products/1.jpg",
    "images": [
      "https://example.com/products/1-1.jpg",
      "https://example.com/products/1-2.jpg"
    ],
    "specifications": [
      {"name": "规格", "value": "100g"},
      {"name": "产地", "value": "浙江杭州"}
    ],
    "sales": 1000,
    "stock": 500,
    "rating": 4.8,
    "reviewCount": 356
  },
  "timestamp": 1711872000000
}
```

---

# 5. 购物车模块 (Cart)

## 5.1 获取购物车列表

**接口标识：** SRS-INT-API-013

### 接口信息

- **路径：** `GET /api/v1/cart`
- **描述：** 获取当前用户的购物车商品列表
- **权限：** 需要认证

### 请求参数

无

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "productId": 1,
      "productName": "西湖龙井",
      "productImage": "https://example.com/products/1.jpg",
      "price": 588.00,
      "quantity": 2,
      "selected": true,
      "subtotal": 1176.00
    }
  ],
  "timestamp": 1711872000000
}
```

---

## 5.2 添加到购物车

**接口标识：** SRS-INT-API-014

### 接口信息

- **路径：** `POST /api/v1/cart`
- **描述：** 将商品添加到购物车
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段        | 类型      | 必填  | 说明       |
| --------- | ------- | --- | -------- |
| productId | Long    | 是   | 商品 ID    |
| quantity  | Integer | 是   | 数量（默认 1） |

**请求示例：**

```json
{
  "productId": 1,
  "quantity": 2
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "添加成功",
  "data": {
    "cartItemId": 1,
    "quantity": 2
  },
  "timestamp": 1711872000000
}
```

---

## 5.3 更新购物车商品数量

**接口标识：** SRS-INT-API-015

### 接口信息

- **路径：** `PUT /api/v1/cart/{id}`
- **描述：** 更新购物车中指定商品的数量
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：购物车项 ID

**请求体（JSON）：**

| 字段       | 类型      | 必填  | 说明  |
| -------- | ------- | --- | --- |
| quantity | Integer | 是   | 新数量 |

**请求示例：**

```json
{
  "quantity": 5
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 5.4 删除购物车商品

**接口标识：** SRS-INT-API-016

### 接口信息

- **路径：** `DELETE /api/v1/cart/{id}`
- **描述：** 从购物车删除指定商品
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：购物车项 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 5.5 批量删除购物车商品

**接口标识：** SRS-INT-API-017

### 接口信息

- **路径：** `DELETE /api/v1/cart/batch`
- **描述：** 批量删除购物车中的商品
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段  | 类型          | 必填  | 说明         |
| --- | ----------- | --- | ---------- |
| ids | Array<Long> | 是   | 购物车项 ID 数组 |

**请求示例：**

```json
{
  "ids": [1, 2, 3]
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

# 6. 订单管理模块 (Order)

## 6.1 创建订单

**接口标识：** SRS-INT-API-018

### 接口信息

- **路径：** `POST /api/v1/orders`
- **描述：** 从购物车选择商品创建订单
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段          | 类型          | 必填  | 说明                |
| ----------- | ----------- | --- | ----------------- |
| userId      | Long        | 是   | 用户ID              |
| addressId   | Long        | 是   | 收货地址 ID           |
| cartItemIds | Array<Long> | 是   | 购物车项 ID 数组         |
| remark      | String      | 否   | 订单备注              |

**请求示例：**

```json
{
  "userId": 1,
  "addressId": 1,
  "cartItemIds": [1, 2],
  "remark": "请尽快发货"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderId": 1
  },
  "timestamp": 1711872000000
}
```

---

## 6.2 获取订单列表

**接口标识：** SRS-INT-API-019

### 接口信息

- **路径：** `GET /api/v1/orders`
- **描述：** 获取当前用户的订单列表
- **权限：** 需要认证

### 请求参数

**查询参数：**

| 参数       | 类型      | 必填  | 说明                            |
| -------- | ------- | --- | ----------------------------- |
| pageNum  | Integer | 否   | 页码（默认1）                       |
| pageSize | Integer | 否   | 每页数量（默认10）                    |
| status   | Integer | 否   | 订单状态（0：待付款，1：待发货，2：待收货，3：已完成，4：已取消） |
| userId   | Long    | 否   | 用户ID（可选）                      |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 20,
    "pageNum": 1,
    "pageSize": 10,
    "list": [
      {
        "orderId": "ORD20260331001",
        "status": 1,
        "statusText": "待发货",
        "totalAmount": 1176.00,
        "payAmount": 1176.00,
        "createTime": "2026-03-31 10:00:00",
        "productList": [
          {
            "productId": 1,
            "productName": "西湖龙井",
            "productImage": "https://example.com/products/1.jpg",
            "quantity": 2,
            "price": 588.00
          }
        ]
      }
    ]
  },
  "timestamp": 1711872000000
}
```

---

## 6.3 获取订单详情

**接口标识：** SRS-INT-API-020

### 接口信息

- **路径：** `GET /api/v1/orders/{id}`
- **描述：** 获取订单详细信息
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：订单 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderId": "ORD20260331001",
    "orderNo": "CC202603310001",
    "status": 1,
    "statusText": "待发货",
    "receiverName": "张三",
    "receiverPhone": "13800138000",
    "receiverAddress": "广东省深圳市南山区科技园南区 A 栋 101",
    "totalAmount": 1176.00,
    "freightAmount": 0.00,
    "payAmount": 1176.00,
    "createTime": "2026-03-31 10:00:00",
    "payTime": null,
    "deliveryTime": null,
    "receiveTime": null,
    "itemList": [
      {
        "productId": 1,
        "productName": "西湖龙井",
        "productImage": "https://example.com/products/1.jpg",
        "quantity": 2,
        "price": 588.00,
        "subtotal": 1176.00
      }
    ],
    "logistics": null
  },
  "timestamp": 1711872000000
}
```

---

## 6.4 取消订单

**接口标识：** SRS-INT-API-021

### 接口信息

- **路径：** `PUT /api/v1/orders/{id}/cancel`
- **描述：** 取消未支付的订单
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：订单 ID

**请求体（JSON）：**

| 字段     | 类型     | 必填  | 说明   |
| ------ | ------ | --- | ---- |
| reason | String | 否   | 取消原因 |

**请求示例：**

```
PUT /api/v1/orders/1/cancel
```

```json
{
  "reason": "不想要了"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "取消成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 6.5 确认支付

**接口标识：** SRS-INT-API-022

### 接口信息

- **路径：** `PUT /api/v1/orders/{id}/pay`
- **描述：** 用户确认支付订单
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：订单 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "支付成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 6.6 确认收货

**接口标识：** SRS-INT-API-023

### 接口信息

- **路径：** `PUT /api/v1/orders/{id}/confirm`
- **描述：** 用户确认收到货物
- **权限：** 需要认证

### 请求参数

**路径参数：**

- id：订单 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "确认收货成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 6.7 管理员发货

**接口标识：** SRS-INT-API-024

### 接口信息

- **路径：** `PUT /api/v1/admin/orders/{id}/ship`
- **描述：** 管理员处理订单发货
- **权限：** 需要管理员认证

### 请求参数

**路径参数：**

- id：订单 ID

**请求体（JSON）：**

| 字段              | 类型     | 必填  | 说明       |
| ---------------- | ------ | --- | -------- |
| logisticsCompany | String | 是   | 物流公司名称    |
| logisticsNo      | String | 是   | 物流单号      |

**请求示例：**

```
PUT /api/v1/admin/orders/1/ship
```

```json
{
  "logisticsCompany": "顺丰速运",
  "logisticsNo": "SF1234567890"
}
```

### 响应示例

**成功响应：**

``json
{
  "code": 200,
  "message": "发货成功",
  "data": null,
  "timestamp": 1711872000000
}
```

**成功响应：**

``json
{
  "code": 200,
  "message": "订单已取消",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 6.5 确认收货

**接口标识：** SRS-INT-API-022

### 接口信息

- **路径：** `PUT /api/v1/orders/{orderId}/confirm`
- **描述：** 确认收到商品
- **权限：** 需要认证

### 请求参数

**路径参数：**

- orderId：订单 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "确认收货成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 6.6 删除订单

**接口标识：** SRS-INT-API-023

### 接口信息

- **路径：** `DELETE /api/v1/orders/{orderId}`
- **描述：** 删除已完成或已取消的订单
- **权限：** 需要认证

### 请求参数

**路径参数：**

- orderId：订单 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

# 7. 支付模块 (Payment)

## 7.1 生成支付二维码

**接口标识：** SRS-INT-API-024

### 接口信息

- **路径：** `POST /api/v1/payment/qrcode`
- **描述：** 生成支付二维码用于扫码支付
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段      | 类型     | 必填  | 说明                         |
| ------- | ------ | --- | -------------------------- |
| orderId | String | 是   | 订单 ID                      |
| payType | String | 是   | 支付方式（WECHAT：微信，ALIPAY：支付宝） |

**请求示例：**

```json
{
  "orderId": "ORD20260331001",
  "payType": "WECHAT"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "qrcodeUrl": "https://example.com/pay/qrcode/abc123.png",
    "qrcodeData": "weixin://wxpay/bizpayurl?pr=abc123",
    "expireTime": 1800
  },
  "timestamp": 1711872000000
}
```

---

## 7.2 查询支付状态

**接口标识：** SRS-INT-API-025

### 接口信息

- **路径：** `GET /api/v1/payment/status/{orderId}`
- **描述：** 查询订单支付状态
- **权限：** 需要认证

### 请求参数

**路径参数：**

- orderId：订单 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderId": "ORD20260331001",
    "payStatus": 1,
    "payStatusText": "已支付",
    "payTime": "2026-03-31 10:05:00",
    "transactionId": "WX202603310001"
  },
  "timestamp": 1711872000000
}
```

---

# 8. 评价模块 (Review)

## 8.1 提交评价

**接口标识：** SRS-INT-API-026

### 接口信息

- **路径：** `POST /api/v1/reviews`
- **描述：** 对已完成的订单商品进行评价
- **权限：** 需要认证

### 请求参数

**请求体（JSON）：**

| 字段        | 类型            | 必填  | 说明          |
| --------- | ------------- | --- | ----------- |
| orderId   | String        | 是   | 订单 ID       |
| productId | Long          | 是   | 商品 ID       |
| rating    | Integer       | 是   | 评分（1-5 星）   |
| content   | String        | 否   | 评价内容        |
| images    | Array<String> | 否   | 评价图片 URL 数组 |

**请求示例：**

```json
{
  "orderId": "ORD20260331001",
  "productId": 1,
  "rating": 5,
  "content": "茶叶品质很好，香气浓郁",
  "images": ["https://example.com/review/1.jpg"]
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "评价提交成功",
  "data": {
    "reviewId": 1
  },
  "timestamp": 1711872000000
}
```

---

## 8.2 获取商品评价列表

**接口标识：** SRS-INT-API-027

### 接口信息

- **路径：** `GET /api/v1/products/{productId}/reviews`
- **描述：** 获取指定商品的评价列表
- **权限：** 公开接口

### 请求参数

**路径参数：**

- productId：商品 ID

**查询参数：**

| 参数       | 类型      | 必填  | 说明   |
| -------- | ------- | --- | ---- |
| rating   | Integer | 否   | 评分筛选 |
| hasImage | Boolean | 否   | 是否有图 |
| pageNum  | Integer | 否   | 页码   |
| pageSize | Integer | 否   | 每页数量 |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "averageRating": 4.8,
    "list": [
      {
        "id": 1,
        "userId": 1001,
        "username": "茶***友",
        "rating": 5,
        "content": "茶叶品质很好，香气浓郁",
        "images": ["https://example.com/review/1.jpg"],
        "createTime": "2026-03-30 15:30:00",
        "reply": "感谢您的好评！"
      }
    ]
  },
  "timestamp": 1711872000000
}
```

---

# 9. 后台管理模块 (Admin)

## 9.1 管理员登录

**接口标识：** SRS-INT-API-028

### 接口信息

- **路径：** `POST /api/v1/admin/login`
- **描述：** 管理员登录后台管理系统
- **权限：** 公开接口

### 请求参数

**请求体（JSON）：**

| 字段       | 类型     | 必填  | 说明    |
| -------- | ------ | --- | ----- |
| username | String | 是   | 管理员账号 |
| password | String | 是   | 密码    |

**请求示例：**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "admin",
    "realName": "系统管理员",
    "role": 1,
    "status": 1,
    "lastLoginTime": null,
    "lastLoginIp": null,
    "createTime": "2026-01-01T10:00:00",
    "updateTime": "2026-01-01T10:00:00"
  },
  "timestamp": 1711872000000
}
```

---

## 9.2 商品管理 - 创建商品

**接口标识：** SRS-INT-API-029

### 接口信息

- **路径：** `POST /api/v1/admin/products`
- **描述：** 添加新商品
- **权限：** 需要管理员权限

### 请求参数

**请求体（JSON）：**

| 字段             | 类型            | 必填  | 说明     |
| -------------- | ------------- | --- | ------ |
| name           | String        | 是   | 商品名称   |
| categoryId     | Long          | 是   | 分类 ID  |
| description    | String        | 是   | 商品描述   |
| detail         | String        | 是   | 商品详情   |
| price          | BigDecimal    | 是   | 价格     |
| originalPrice  | BigDecimal    | 否   | 原价     |
| stock          | Integer       | 是   | 库存     |
| imageUrl       | String        | 是   | 主图 URL |
| images         | Array<String> | 否   | 图片数组   |
| specifications | Array<Object> | 否   | 规格参数   |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "商品添加成功",
  "data": {
    "productId": 101
  },
  "timestamp": 1711872000000
}
```

---

## 9.3 商品管理 - 更新商品

**接口标识：** SRS-INT-API-030

### 接口信息

- **路径：** `PUT /api/v1/admin/products/{id}`
- **描述：** 更新商品信息
- **权限：** 需要管理员权限

### 请求参数

**路径参数：**

- id：商品 ID

**请求体：** 同创建商品

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 9.4 商品管理 - 删除商品

**接口标识：** SRS-INT-API-031

### 接口信息

- **路径：** `DELETE /api/v1/admin/products/{id}`
- **描述：** 删除商品
- **权限：** 需要管理员权限

### 请求参数

**路径参数：**

- id：商品 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 9.5 订单管理 - 发货

**接口标识：** SRS-INT-API-032

### 接口信息

- **路径：** `PUT /api/v1/admin/orders/{orderId}/ship`
- **描述：** 订单发货
- **权限：** 需要管理员权限

### 请求参数

**路径参数：**

- orderId：订单 ID

**请求体（JSON）：**

| 字段               | 类型     | 必填  | 说明   |
| ---------------- | ------ | --- | ---- |
| logisticsCompany | String | 是   | 物流公司 |
| logisticsNo      | String | 是   | 物流单号 |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "发货成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 9.6 获取统计数据

**接口标识：** SRS-INT-API-033

### 接口信息

- **路径：** `GET /api/v1/admin/statistics`
- **描述：** 获取后台统计数据
- **权限：** 需要管理员权限

### 请求参数

无

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalUsers": 100,
    "totalProducts": 50,
    "totalOrders": 200,
    "totalSales": 50000.00
  },
  "timestamp": 1711872000000
}
```

---

# 10. 轮播图模块 (Banner)

## 10.1 获取轮播图列表

**接口标识：** SRS-INT-API-034

### 接口信息

- **路径：** `GET /api/v1/banners`
- **描述：** 获取启用状态的轮播图列表（用于首页轮播）
- **权限：** 公开接口

### 请求参数

无

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "春季新品",
      "imageUrl": "https://example.com/banner/spring.jpg",
      "sortOrder": 1,
      "create_time": "2026-03-01 10:00:00"
    },
    {
      "id": 2,
      "name": "会员专享",
      "imageUrl": "https://example.com/banner/member.jpg",
      "sortOrder": 2,
      "create_time": "2026-03-02 10:00:00"
    }
  ],
  "timestamp": 1711872000000
}
```

---

## 10.2 获取广告图列表

**接口标识：** SRS-INT-API-035

### 接口信息

- **路径：** `GET /api/v1/banners/ad`
- **描述：** 获取广告图列表（用于首页广告位）
- **权限：** 公开接口

### 请求参数

无

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 101,
      "name": "满减活动",
      "imageUrl": "https://example.com/ad/discount.jpg",
      "sortOrder": 1,
      "create_time": "2026-03-15 10:00:00"
    }
  ],
  "timestamp": 1711872000000
}
```

---

# 11. 后台管理 - 轮播图管理

## 11.1 获取轮播图列表（管理员）

**接口标识：** SRS-INT-API-036

### 接口信息

- **路径：** `GET /api/v1/admin/banners`
- **描述：** 获取所有轮播图（含禁用状态）
- **权限：** 需要管理员权限

### 请求参数

无

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "春季新品",
      "imageUrl": "https://example.com/banner/spring.jpg",
      "type": "BANNER",
      "sortOrder": 1,
      "status": 1,
      "create_time": "2026-03-01 10:00:00",
      "update_time": "2026-03-01 10:00:00"
    },
    {
      "id": 2,
      "name": "会员专享",
      "imageUrl": "https://example.com/banner/member.jpg",
      "type": "BANNER",
      "sortOrder": 2,
      "status": 1,
      "create_time": "2026-03-02 10:00:00",
      "update_time": "2026-03-02 10:00:00"
    }
  ],
  "timestamp": 1711872000000
}
```

---

## 11.2 添加轮播图

**接口标识：** SRS-INT-API-037

### 接口信息

- **路径：** `POST /api/v1/admin/banners`
- **描述：** 添加新的轮播图
- **权限：** 需要管理员权限

### 请求参数

**请求体（JSON）：**

| 字段        | 类型     | 必填  | 说明       |
| --------- | ------ | --- | -------- |
| name      | String | 是   | 图片名称     |
| imageUrl  | String | 是   | 图片 URL   |
| type      | String | 是   | 图片类型（BANNER/AD） |
| sortOrder | Integer | 是   | 排序号（越小越靠前） |
| status    | Integer | 是   | 状态（0：禁用，1：启用） |

**请求示例：**

```json
{
  "name": "新品上市",
  "imageUrl": "https://example.com/banner/new.jpg",
  "type": "BANNER",
  "sortOrder": 3,
  "status": 1
}
```

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "添加成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 11.3 更新轮播图

**接口标识：** SRS-INT-API-038

### 接口信息

- **路径：** `PUT /api/v1/admin/banners/{id}`
- **描述：** 更新轮播图信息
- **权限：** 需要管理员权限

### 请求参数

**路径参数：**

- id：轮播图 ID

**请求体（JSON）：** 同添加轮播图

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 11.4 删除轮播图

**接口标识：** SRS-INT-API-039

### 接口信息

- **路径：** `DELETE /api/v1/admin/banners/{id}`
- **描述：** 删除轮播图
- **权限：** 需要管理员权限

### 请求参数

**路径参数：**

- id：轮播图 ID

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1711872000000
}
```

---

## 11.5 上传轮播图

**接口标识：** SRS-INT-API-040

### 接口信息

- **路径：** `POST /api/v1/admin/banners/upload`
- **描述：** 上传轮播图文件到 OSS 存储
- **权限：** 需要管理员权限

### 请求参数

**表单数据：**

| 字段  | 类型     | 必填  | 说明   |
| --- | ------ | --- | ---- |
| file | File   | 是   | 图片文件 |

### 响应示例

**成功响应：**

```json
{
  "code": 200,
  "message": "上传成功",
  "data": "https://example.com/banner/uploaded.jpg",
  "timestamp": 1711872000000
}
```

---

# 12. 错误码说明

## 12.1 通用错误码

| 错误码 | 说明        |
| --- | --------- |
| 200 | 成功        |
| 400 | 请求参数错误    |
| 401 | 未授权，请先登录  |
| 403 | 拒绝访问，权限不足 |
| 404 | 请求的资源不存在  |
| 500 | 服务器内部错误   |

## 12.2 业务错误码

| 错误码  | 说明         |
| ---- | ---------- |
| 1001 | 用户名或密码错误   |
| 1002 | 用户已被禁用     |
| 2001 | 商品已下架      |
| 2002 | 商品库存不足     |
| 3001 | 购物车为空      |
| 3002 | 购物车商品不存在   |
| 4001 | 订单不存在      |
| 4002 | 订单状态不允许此操作 |
| 4003 | 订单已取消      |
| 4004 | 订单已支付      |
| 5001 | 支付失败       |
| 6001 | 收货地址不存在    |

---

# 13. 附录

## 13.1 数据字典

### 订单状态

| 状态值 | 状态名称 | 说明            |
| --- | ---- | ------------- |
| 0   | 待付款  | 订单已创建，等待支付    |
| 1   | 待发货  | 支付成功，等待发货     |
| 2   | 待收货  | 已发货，等待确认收货    |
| 3   | 已完成  | 已确认收货，交易完成    |
| 4   | 已取消  | 订单已取消         |

### 支付方式

| 支付类型   | 说明   |
| ------ | ---- |
| 1 | 微信支付 |
| 2 | 支付宝  |

### 用户性别

| 值 | 说明 |
|----|------|
| 0 | 未知 |
| 1 | 男 |
| 2 | 女 |

### 商品状态

| 值 | 说明 |
|----|------|
| 0 | 下架 |
| 1 | 上架 |

### 管理员角色

| 值 | 说明 |
|----|------|
| 1 | 超级管理员 |
| 2 | 普通管理员 |

---

**文档结束**
