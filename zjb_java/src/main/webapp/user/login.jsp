<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录 - 网上衣橱</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>网上衣橱</h1>
        <p>发现你的专属时尚</p>
    </div>
    <div class="login-box">
        <h2>用户登录</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/auth" method="post" onsubmit="return validateLogin()">
            <input type="hidden" name="action" value="login">
            <div class="login-tabs">
                <button type="button" class="tab-btn active" onclick="switchTab('username')">用户名登录</button>
                <button type="button" class="tab-btn" onclick="switchTab('phone')">手机号登录</button>
            </div>
            <input type="hidden" name="loginType" id="loginType" value="username">
            <div id="usernameFields">
                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" name="username" placeholder="请输入用户名">
                </div>
            </div>
            <div id="phoneFields" style="display:none">
                <div class="form-group">
                    <label>手机号</label>
                    <input type="text" name="phone" placeholder="请输入手机号">
                </div>
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" placeholder="请输入密码">
            </div>
            <button type="submit" class="btn btn-primary btn-block">登 录</button>
        </form>
        <p class="text-center">还没有账号？<a href="${pageContext.request.contextPath}/register">立即注册</a></p>
    </div>
</div>
<script>
function switchTab(type) {
    document.getElementById("loginType").value = type;
    document.querySelectorAll(".tab-btn").forEach(function(b) { b.classList.remove("active"); });
    if (type === "username") {
        document.getElementById("usernameFields").style.display = "block";
        document.getElementById("phoneFields").style.display = "none";
        document.querySelectorAll(".tab-btn")[0].classList.add("active");
    } else {
        document.getElementById("usernameFields").style.display = "none";
        document.getElementById("phoneFields").style.display = "block";
        document.querySelectorAll(".tab-btn")[1].classList.add("active");
    }
}
function validateLogin() {
    var loginType = document.getElementById("loginType").value;
    if (loginType === "username") {
        var u = document.querySelector('input[name="username"]').value.trim();
        if (u === "") { alert("请输入用户名"); return false; }
    } else {
        var p = document.querySelector('input[name="phone"]').value.trim();
        if (p === "") { alert("请输入手机号"); return false; }
    }
    var pw = document.querySelector('input[name="password"]').value.trim();
    if (pw === "") { alert("请输入密码"); return false; }
    return true;
}
</script>
</body>
</html>