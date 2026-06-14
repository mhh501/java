<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理 - 网上衣橱</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <div class="admin-sidebar">
        <h2>后台管理</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="active">控制面板</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/clothes">服装管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">订单管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">用户管理</a></li>
        </ul>
        <div class="sidebar-footer">
            <p>管理员：${sessionScope.user.username}</p>
            <a href="${pageContext.request.contextPath}/index" class="btn btn-sm">返回前台</a>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-danger btn-sm">退出</a>
        </div>
    </div>
    <div class="admin-main">
        <h1>控制面板</h1>
        <p>欢迎使用网上衣橱后台管理系统！请通过左侧菜单进行各项管理操作。</p>
        <div class="dashboard-cards">
            <div class="dash-card">
                <h3>服装管理</h3>
                <p>上架/编辑/删除服装，管理库存</p>
                <a href="${pageContext.request.contextPath}/admin/clothes" class="btn btn-primary">进入</a>
            </div>
            <div class="dash-card">
                <h3>订单管理</h3>
                <p>查看订单，执行发货操作</p>
                <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-primary">进入</a>
            </div>
            <div class="dash-card">
                <h3>用户管理</h3>
                <p>管理普通用户账号</p>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">进入</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>