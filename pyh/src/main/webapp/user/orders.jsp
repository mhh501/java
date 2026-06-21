<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单 - 票映汇衣橱</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<aside class="sidebar">
    <div class="sidebar-logo">票映汇<span>网上衣橱</span></div>
    <ul class="sidebar-nav">
        <li>
            <a href="${pageContext.request.contextPath}/clothes">
                <span class="icon">🏠</span>首页
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/cart">
                <span class="icon">🛒</span>购物车
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/order">
                <span class="icon">📦</span>我的订单
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/profile">
                <span class="icon">👤</span>个人中心
            </a>
        </li>
        <%-- 管理员后台入口 --%>
        <c:if test="${sessionScope.userRole == 'admin'}">
            <li>
                <a href="${pageContext.request.contextPath}/admin/dashboard">
                    <span class="icon">⚙️</span>后台管理
                </a>
            </li>
        </c:if>
    </ul>
    <div class="sidebar-user">
        <span class="name">${sessionScope.loginUser}</span>
        <a href="${pageContext.request.contextPath}/login.jsp">退出</a>
    </div>
</aside>

<div class="main-wrap">
    <div class="top-strip">
        <h1>📦 我的订单</h1>
    </div>

    <div class="content">
        <c:if test="${empty orders}">
            <div class="empty-state">
                <div class="icon">📦</div>
                <p>暂无订单</p>
            </div>
        </c:if>

        <div class="order-list">
            <c:forEach items="${orders}" var="o">
                <div class="order-card">
                    <div class="order-top">
                        <span>订单号: ${o.orderNo}</span>
                        <span>${o.createTime}</span>
                        <span class="tag tag-${o.status}">${o.statusText}</span>
                    </div>

                    <div class="order-body">
                        <div>
                                <%-- 循环渲染订单内的衣服子项 --%>
                            <c:forEach items="${o.items}" var="oi">
                                <p>${oi.clothesName} x${oi.quantity} (${oi.size}) &yen;${oi.price}</p>
                            </c:forEach>
                        </div>
                        <span style="font-size: 18px; font-weight: 700; color: #c0392b;">
                                合计: &yen;${o.totalPrice}
                            </span>
                    </div>

                    <div class="order-foot">
                            <%-- 待支付状态 --%>
                        <c:if test="${o.status == 'unpaid'}">
                            <a href="${pageContext.request.contextPath}/order?action=pay&id=${o.id}"
                               class="btn btn-success btn-sm"
                               onclick="return confirm('确认支付？')">支付</a>
                        </c:if>
                            <%-- 已发货状态 --%>
                        <c:if test="${o.status == 'shipped'}">
                            <a href="${pageContext.request.contextPath}/order?action=receive&id=${o.id}"
                               class="btn btn-success btn-sm"
                               onclick="return confirm('确认收货？')">确认收货</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</div>

</body>
</html>