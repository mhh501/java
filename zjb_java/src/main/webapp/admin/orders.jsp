<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单管理 - 后台管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <div class="admin-sidebar">
        <h2>后台管理</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">控制面板</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/clothes">服装管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders" class="active">订单管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">用户管理</a></li>
        </ul>
        <div class="sidebar-footer">
            <p>管理员：${sessionScope.user.username}</p>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-danger btn-sm">退出</a>
        </div>
    </div>
    <div class="admin-main">
        <h1>订单管理</h1>
        <form action="${pageContext.request.contextPath}/admin/orders" method="get" class="search-form">
            <input type="text" name="keyword" placeholder="订单号/用户名" value="${keyword}">
            <select name="status">
                <option value="">全部状态</option>
                <option value="unpaid" ${selectedStatus eq 'unpaid' ? 'selected' : ''}>未支付</option>
                <option value="unshipped" ${selectedStatus eq 'unshipped' ? 'selected' : ''}>未发货</option>
                <option value="shipped" ${selectedStatus eq 'shipped' ? 'selected' : ''}>已发货</option>
                <option value="received" ${selectedStatus eq 'received' ? 'selected' : ''}>已收货</option>
            </select>
            <button type="submit" class="btn btn-primary">搜索</button>
        </form>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th><th>订单编号</th><th>用户</th><th>金额</th><th>状态</th><th>下单时间</th><th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${orders}" var="o">
                    <tr>
                        <td>${o.id}</td>
                        <td>${o.orderNo}</td>
                        <td>${o.userName}</td>
                        <td>&yen;${o.totalPrice}</td>
                        <td><span class="status status-${o.status}">${o.statusText}</span></td>
                        <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>
                            <c:if test="${o.status == 'unshipped'}">
                                <a href="${pageContext.request.contextPath}/admin/orders?action=ship&id=${o.id}" class="btn btn-primary btn-sm" onclick="return confirm('确认发货？')">发货</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>