/**
 * 潮茶商城前端公共JS
 */

const API_BASE = '/api/v1';

// 获取存储的token
function getToken() {
    return localStorage.getItem('token') || sessionStorage.getItem('token');
}

// 获取当前用户
function getUser() {
    const userStr = localStorage.getItem('user') || sessionStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
}

// 判断是否登录
function isLoggedIn() {
    return !!getToken();
}

// 判断是否管理员
function isAdmin() {
    const user = getUser();
    return user && user.role === 'SUPER_ADMIN';
}

// 通用请求封装
async function request(url, options = {}) {
    const token = getToken();
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    const response = await fetch(API_BASE + url, {
        ...options,
        headers
    });

    const data = await response.json();

    if (data.code === 401) {
        logout();
        showToast('登录已过期，请重新登录', 'error');
        setTimeout(() => location.href = '/login', 1500);
        return Promise.reject(data);
    }

    if (data.code !== 200) {
        showToast(data.message || '操作失败', 'error');
        return Promise.reject(data);
    }

    return data;
}

// GET请求
function get(url) {
    return request(url, { method: 'GET' });
}

// POST请求
function post(url, body) {
    return request(url, {
        method: 'POST',
        body: JSON.stringify(body)
    });
}

// PUT请求
function put(url, body) {
    return request(url, {
        method: 'PUT',
        body: JSON.stringify(body)
    });
}

// DELETE请求
function del(url) {
    return request(url, { method: 'DELETE' });
}

// 文件上传（Multipart）
async function upload(url, file) {
    const token = getToken();
    const headers = {};
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch(API_BASE + url, {
        method: 'POST',
        headers,
        body: formData
    });

    const data = await response.json();

    if (data.code === 401) {
        logout();
        showToast('登录已过期，请重新登录', 'error');
        setTimeout(() => location.href = '/login', 1500);
        return Promise.reject(data);
    }

    if (data.code !== 200) {
        showToast(data.message || '上传失败', 'error');
        return Promise.reject(data);
    }

    return data;
}

// Toast提示
function showToast(message, type = 'success') {
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = 'toast ' + type;
    toast.textContent = message;
    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
}

// 登出
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
    location.href = '/';
}

// 格式化价格
function formatPrice(price) {
    return '¥' + parseFloat(price).toFixed(2);
}

// 格式化日期
function formatDate(dateStr) {
    if (!dateStr) return '-';
    return dateStr;
}

// 订单状态文本
function orderStatusText(status) {
    const map = {
        0: '待付款',
        1: '待发货',
        2: '待收货',
        3: '已完成',
        4: '已取消',
        5: '已关闭'
    };
    return map[status] || '未知';
}

// 订单状态样式
function orderStatusClass(status) {
    const map = {
        0: 'status-pending',
        1: 'status-paid',
        2: 'status-shipped',
        3: 'status-completed',
        4: 'status-cancelled',
        5: 'status-cancelled'
    };
    return map[status] || '';
}

// 生成星级HTML
function renderStars(rating) {
    let html = '';
    for (let i = 1; i <= 5; i++) {
        if (i <= rating) {
            html += '★';
        } else {
            html += '☆';
        }
    }
    return html;
}

// 更新购物车数量
async function updateCartBadge() {
    const badgeEl = document.querySelector('.cart-badge .badge');
    if (!badgeEl || !isLoggedIn()) {
        if (badgeEl) badgeEl.style.display = 'none';
        return;
    }
    try {
        const user = getUser();
        const data = await get('/cart?userId=' + (user ? user.id : ''));
        const count = data.data ? data.data.length : 0;
        if (count > 0) {
            badgeEl.textContent = count;
            badgeEl.style.display = 'inline-block';
        } else {
            badgeEl.style.display = 'none';
        }
    } catch (e) {
        // ignore
    }
}

// 初始化头部导航
function initHeader() {
    const user = getUser();
    const userNav = document.getElementById('user-nav');
    if (userNav) {
        if (user) {
            userNav.innerHTML = `
                <a href="/user/profile">${user.nickname || user.username}</a>
                <a href="javascript:void(0)" onclick="logout()">退出</a>
            `;
        } else {
            userNav.innerHTML = `
                <a href="/login">登录</a>
                <a href="/register">注册</a>
            `;
        }
    }
    updateCartBadge();
}

// 确认对话框
function confirmDialog(message) {
    return new Promise((resolve) => {
        const overlay = document.createElement('div');
        overlay.className = 'modal-overlay active';
        overlay.innerHTML = `
            <div class="modal-content" style="max-width:360px;">
                <div class="modal-body" style="text-align:center;">
                    <p style="font-size:15px;margin-bottom:20px;">${message}</p>
                    <div style="display:flex;gap:12px;justify-content:center;">
                        <button class="btn btn-secondary" id="confirm-cancel">取消</button>
                        <button class="btn btn-primary" id="confirm-ok">确定</button>
                    </div>
                </div>
            </div>
        `;
        document.body.appendChild(overlay);

        overlay.querySelector('#confirm-cancel').onclick = () => {
            overlay.remove();
            resolve(false);
        };
        overlay.querySelector('#confirm-ok').onclick = () => {
            overlay.remove();
            resolve(true);
        };
        overlay.onclick = (e) => {
            if (e.target === overlay) {
                overlay.remove();
                resolve(false);
            }
        };
    });
}

// 表单验证
function validateForm(formEl) {
    const inputs = formEl.querySelectorAll('[required]');
    let valid = true;
    inputs.forEach(input => {
        if (!input.value.trim()) {
            valid = false;
            input.style.borderColor = '#e74c3c';
        } else {
            input.style.borderColor = '';
        }
    });
    return valid;
}

// 节流函数
function throttle(fn, wait) {
    let last = 0;
    return function(...args) {
        const now = Date.now();
        if (now - last >= wait) {
            last = now;
            fn.apply(this, args);
        }
    };
}

// 防抖函数
function debounce(fn, wait) {
    let timer;
    return function(...args) {
        clearTimeout(timer);
        timer = setTimeout(() => fn.apply(this, args), wait);
    };
}

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', () => {
    initHeader();
});
