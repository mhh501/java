<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${clothes.name} - 商品详情 - 网上衣橱</title>
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
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <div class="detail-container">
        <div class="detail-image">
            <c:choose>
                <c:when test="${not empty clothes.image}">
                    <img src="${pageContext.request.contextPath}/uploads/${clothes.image}" alt="${clothes.name}">
                </c:when>
                <c:otherwise>
                    <div class="clothes-img placeholder large">暂无图片</div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="detail-info">
            <h1>${clothes.name}</h1>
            <p class="detail-meta">类别：${clothes.category} | 风格：${clothes.style}</p>
            <p class="detail-price">&yen;${clothes.price}</p>
            <p class="detail-stock">库存：${clothes.stock} 件</p>
            <p class="detail-desc">${clothes.description}</p>
            <c:if test="${not empty sessionScope.user && sessionScope.user.role == 'user'}">
                <form action="${pageContext.request.contextPath}/cart" method="post" onsubmit="return checkAddCart()">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="clothesId" value="${clothes.id}">
                    <div class="form-group">
                        <label>选择尺码</label>
                        <select name="size" id="sizeSelect">
                            <c:forTokens items="${clothes.size}" delims="," var="s">
                                <option value="${s}">${s}</option>
                            </c:forTokens>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>数量</label>
                        <input type="number" name="quantity" id="quantity" value="1" min="1" max="${clothes.stock}">
                    </div>
                    <button type="submit" class="btn btn-primary btn-lg">加入购物车</button>
                </form>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <p><a href="${pageContext.request.contextPath}/login">请先登录</a>再进行购买</p>
            </c:if>
        </div>
    </div>
</div>
<script>
function checkAddCart() {
    var qty = parseInt(document.getElementById('quantity').value);
    var max = ${clothes.stock};
    if (qty < 1) { alert('数量至少为1'); return false; }
    if (qty > max) { alert('库存不足'); return false; }
    return true;
}
</script>
</body>
</html>