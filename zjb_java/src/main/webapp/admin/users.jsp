<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理 - 后台管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <div class="admin-sidebar">
        <h2>后台管理</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">控制面板</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/clothes">服装管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">订单管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users" class="active">用户管理</a></li>
        </ul>
        <div class="sidebar-footer">
            <p>管理员：${sessionScope.user.username}</p>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-danger btn-sm">退出</a>
        </div>
    </div>
    <div class="admin-main">
        <h1>用户管理</h1>
        <div class="form-panel">
            <h3>${not empty editUser ? '编辑用户' : '添加用户'}</h3>
            <form action="${pageContext.request.contextPath}/admin/users" method="post">
                <input type="hidden" name="action" value="${not empty editUser ? 'update' : 'add'}">
                <c:if test="${not empty editUser}">
                    <input type="hidden" name="id" value="${editUser.id}">
                </c:if>
                <div class="form-row">
                    <div class="form-group">
                        <label>用户名</label>
                        <input type="text" name="username" value="${editUser.username}" required>
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="password" name="password" placeholder="${not empty editUser ? '不修改请留空' : '请输入密码'}" ${empty editUser ? 'required' : ''}>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>手机号</label>
                        <input type="text" name="phone" value="${editUser.phone}">
                    </div>
                    <div class="form-group">
                        <label>地址</label>
                        <input type="text" name="address" value="${editUser.address}">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">${not empty editUser ? '保存修改' : '添加用户'}</button>
                <c:if test="${not empty editUser}">
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn">取消</a>
                </c:if>
            </form>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th><th>用户名</th><th>手机号</th><th>地址</th><th>角色</th><th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="u">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.username}</td>
                        <td>${u.phone}</td>
                        <td>${u.address}</td>
                        <td>${u.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" class="btn btn-sm">编辑</a>
                            <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.id}" class="btn btn-danger btn-sm" onclick="return confirm('确定删除该用户？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>