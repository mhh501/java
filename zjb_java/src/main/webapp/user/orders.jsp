<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单 - 网上衣橱</title>
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
    <h2>我的订单</h2>
    <c:choose>
        <c:when test="${not empty orders}">
            <table class="table">
                <thead>
                    <tr>
                        <th>订单编号</th><th>总金额</th><th>状态</th><th>下单时间</th><th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="o">
                        <tr>
                            <td>${o.orderNo}</td>
                            <td>&yen;${o.totalPrice}</td>
                            <td><span class="status status-${o.status}">${o.statusText}</span></td>
                            <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>
                                <c:if test="${o.status == 'unpaid'}">
                                    <a href="${pageContext.request.contextPath}/order?action=pay&id=${o.id}" class="btn btn-primary btn-sm" onclick="return confirm('确认支付？')">支付</a>
                                </c:if>
                                <c:if test="${o.status == 'shipped'}">
                                    <a href="${pageContext.request.contextPath}/order?action=receive&id=${o.id}" class="btn btn-success btn-sm" onclick="return confirm('确认收货？')">确认收货</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="text-center empty">暂无订单</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>