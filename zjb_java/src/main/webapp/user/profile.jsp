<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人中心 - 网上衣橱</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="top-bar">
        <div class="logo"><a href="${pageContext.request.contextPath}/index">网上衣橱</a></div>
        <div class="nav">
            <a href="${pageContext.request.contextPath}/index">首页</a>
            <a href="${pageContext.request.contextPath}/cart">购物车</a>
            <a href="${pageContext.request.contextPath}/order">我的订单</a>
            <a href="${pageContext.request.contextPath}/profile">个人中心</a>
            <span>欢迎，${sessionScope.user.username}</span>
            <a href="${pageContext.request.contextPath}/auth?action=logout">退出</a>
        </div>
    </div>
    <h2>个人中心</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <div class="profile-box">
        <form action="${pageContext.request.contextPath}/profile" method="post">
            <input type="hidden" name="action" value="update">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="username" value="${user.username}" required>
            </div>
            <div class="form-group">
                <label>手机号</label>
                <input type="text" name="phone" value="${user.phone}" required>
            </div>
            <div class="form-group">
                <label>收货地址</label>
                <input type="text" name="address" value="${user.address}">
            </div>
            <h3>修改密码（如需修改请填写）</h3>
            <div class="form-group">
                <label>原密码</label>
                <input type="password" name="originalPassword" placeholder="请输入原密码以验证身份">
            </div>
            <div class="form-group">
                <label>新密码</label>
                <input type="password" name="newPassword" placeholder="请输入新密码（留空不修改）">
            </div>
            <button type="submit" class="btn btn-primary">保存修改</button>
        </form>
    </div>
</div>
</body>
</html>