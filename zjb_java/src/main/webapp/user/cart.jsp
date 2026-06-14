<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车 - 网上衣橱</title>
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
    <h2>我的购物车</h2>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>
    <c:choose>
        <c:when test="${not empty cartList}">
            <table class="table">
                <thead>
                    <tr>
                        <th>图片</th><th>商品名称</th><th>尺码</th><th>单价</th><th>数量</th><th>小计</th><th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cartList}" var="item">
                        <tr>
                            <td>
                                <c:if test="${not empty item.clothesImage}">
                                    <img src="${pageContext.request.contextPath}/uploads/${item.clothesImage}" width="60">
                                </c:if>
                            </td>
                            <td>${item.clothesName}</td>
                            <td>${item.size}</td>
                            <td>&yen;${item.clothesPrice}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post" style="display:inline">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${item.id}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" style="width:50px" onchange="this.form.submit()">
                                </form>
                            </td>
                            <td>&yen;${item.subtotal}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post" onsubmit="return confirm('确定删除？')">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${item.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">删除</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="cart-total">
                <c:set var="total" value="0"/>
                <c:forEach items="${cartList}" var="it">
                    <c:set var="total" value="${total + it.subtotal}"/>
                </c:forEach>
                <p>合计：<span class="price">&yen;${total}</span></p>
                <form action="${pageContext.request.contextPath}/order" method="post" onsubmit="return confirm('确认生成订单？')">
                    <input type="hidden" name="action" value="create">
                    <button type="submit" class="btn btn-success btn-lg">批量结算</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-center empty">购物车空空如也，去<a href="${pageContext.request.contextPath}/index">逛逛</a>吧</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>