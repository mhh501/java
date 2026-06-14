<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册 - 网上衣橱</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>网上衣橱</h1>
        <p>创建你的专属账户</p>
    </div>
    <div class="login-box">
        <h2>用户注册</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/auth" method="post" onsubmit="return validateRegister()">
            <input type="hidden" name="action" value="register">
            <div class="form-group">
                <label>用户名 <span class="required">*</span></label>
                <input type="text" name="username" id="username" placeholder="请输入用户名（4-20位字母或数字）">
            </div>
            <div class="form-group">
                <label>密码 <span class="required">*</span></label>
                <input type="password" name="password" id="password" placeholder="请输入密码（6-20位）">
            </div>
            <div class="form-group">
                <label>确认密码 <span class="required">*</span></label>
                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="请再次输入密码">
            </div>
            <div class="form-group">
                <label>手机号 <span class="required">*</span></label>
                <input type="text" name="phone" id="phone" placeholder="请输入11位手机号">
            </div>
            <div class="form-group">
                <label>收货地址</label>
                <input type="text" name="address" placeholder="请输入收货地址">
            </div>
            <button type="submit" class="btn btn-primary btn-block">注 册</button>
        </form>
        <p class="text-center">已有账号？<a href="${pageContext.request.contextPath}/login">立即登录</a></p>
    </div>
</div>
<script>
function validateRegister() {
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value;
    var confirm = document.getElementById("confirmPassword").value;
    var phone = document.getElementById("phone").value.trim();
    if (username.length < 4 || username.length > 20) {
        alert("用户名需4-20位"); return false;
    }
    if (password.length < 6) {
        alert("密码至少6位"); return false;
    }
    if (password !== confirm) {
        alert("两次密码不一致"); return false;
    }
    if (!/^1[3-9]\d{9}$/.test(phone)) {
        alert("手机号格式不正确"); return false;
    }
    return true;
}
</script>
</body>
</html>