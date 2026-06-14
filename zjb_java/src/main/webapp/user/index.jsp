<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>服装列表 - 网上衣橱</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="top-bar">
        <div class="logo"><a href="${pageContext.request.contextPath}/index">网上衣橱</a></div>
        <div class="nav">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/cart">购物车</a>
                    <a href="${pageContext.request.contextPath}/order">我的订单</a>
                    <a href="${pageContext.request.contextPath}/profile">个人中心</a>
                    <c:if test="${sessionScope.user.role == 'admin'}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard">后台管理</a>
                    </c:if>
                    <span>欢迎，${sessionScope.user.username}</span>
                    <a href="${pageContext.request.contextPath}/auth?action=logout">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">登录</a>
                    <a href="${pageContext.request.contextPath}/register">注册</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Search bar -->
    <div class="search-bar">
        <form action="${pageContext.request.contextPath}/clothes" method="get">
            <input type="text" name="keyword" value="${keyword}" placeholder="搜索你喜欢的服装...">
            <select name="category">
                <option value="">全部类别</option>
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat}" ${selectedCategory eq cat ? 'selected' : ''}>${cat}</option>
                </c:forEach>
            </select>
            <select name="style">
                <option value="">全部风格</option>
                <c:forEach items="${styles}" var="st">
                    <option value="${st}" ${selectedStyle eq st ? 'selected' : ''}>${st}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">搜索</button>
        </form>
    </div>

    <!-- Clothes list -->
    <div class="clothes-grid">
        <c:forEach items="${clothesList}" var="c">
            <div class="clothes-card">
                <c:choose>
                    <c:when test="${not empty c.image}">
                        <img src="${pageContext.request.contextPath}/uploads/${c.image}" alt="${c.name}" class="clothes-img">
                    </c:when>
                    <c:otherwise>
                        <div class="clothes-img placeholder">暂无图片</div>
                    </c:otherwise>
                </c:choose>
                <div class="clothes-info">
                    <h3>${c.name}</h3>
                    <p class="clothes-meta">${c.category} | ${c.style}</p>
                    <p class="clothes-price">&yen;${c.price}</p>
                    <p class="clothes-stock">库存：${c.stock}</p>
                    <a href="${pageContext.request.contextPath}/detail?id=${c.id}" class="btn btn-primary btn-sm">查看详情</a>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty clothesList}">
            <p class="text-center empty">暂无服装信息</p>
        </c:if>
    </div>
</div>
</body>
</html>
