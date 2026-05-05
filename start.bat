@echo off
chcp 65001 >nul
echo ==========================================
echo    潮茶商城系统 - 一键启动
echo ==========================================
echo.
echo 正在检查环境...

java -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未检测到 JDK 17，请先安装 JDK 17
    echo 下载地址：https://adoptium.net/
    pause
    exit /b 1
)

echo [OK] Java 环境已检测到
echo.
echo 正在启动潮茶商城系统...
echo 启动成功后，请在浏览器访问：http://localhost:8080
echo.
echo 管理员后台：http://localhost:8080/admin/login
echo 账号：admin / admin123
echo.
echo 普通用户：testuser / 123456
echo.
echo ==========================================
echo 按 Ctrl+C 可停止运行
echo ==========================================
echo.

java -jar tea-shopping-0.0.1-SNAPSHOT.jar

pause
