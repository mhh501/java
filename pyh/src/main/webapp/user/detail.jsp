<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="jakarta.tags.core" %><%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>${cloth.name} - 票映汇衣橱</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script>
function updateSize(el) {
  document.querySelectorAll('.size-option').forEach(function(e) { e.classList.remove('selected'); });
  el.parentElement.classList.add('selected');
  var val = el.value;
  var pipeIdx = val.indexOf('|');
  if (pipeIdx > 0) {
    document.getElementById('sizePrice').textContent = val.substring(pipeIdx + 1);
  }
}
</script>
</head><body>
<aside class="sidebar"><div class="sidebar-logo">票映汇<span>网上衣橱</span></div><ul class="sidebar-nav"><li><a href="${pageContext.request.contextPath}/clothes"><span class="icon">🏠</span>首页</a></li><c:if test="${not empty sessionScope.loginUser}"><li><a href="${pageContext.request.contextPath}/cart"><span class="icon">🛒</span>购物车</a></li><li><a href="${pageContext.request.contextPath}/order"><span class="icon">📦</span>我的订单</a></li><li><a href="${pageContext.request.contextPath}/profile"><span class="icon">👤</span>个人中心</a></li></c:if></ul></aside>
<div class="main-wrap"><div class="content"><a href="${pageContext.request.contextPath}/clothes" style="color:#8a7e72;font-size:13px;margin-bottom:16px;display:inline-block">&larr; 返回列表</a><div class="detail-layout"><div class="detail-img"><c:if test="${not empty cloth.imageUrl}"><img src="${pageContext.request.contextPath}/${cloth.imageUrl}"></c:if><c:if test="${empty cloth.imageUrl}"><span style="font-size:80px">👕</span></c:if></div><div class="detail-info"><h2>${cloth.name}</h2><div class="meta-row"><span>${cloth.category}</span><span>${cloth.style}</span><span>库存: ${cloth.stock}</span></div><div class="detail-price">&yen;<span id="sizePrice">${cloth.price}</span></div><p class="detail-desc">${cloth.description}</p><c:if test="${not empty sessionScope.loginUser}"><form action="${pageContext.request.contextPath}/cart" method="post"><input type="hidden" name="action" value="add"><input type="hidden" name="clothesId" value="${cloth.id}"><div class="form-group"><label>选择尺码</label><div class="size-picker"><c:forEach items="${sizeList}" var="sz"><c:set var="parts" value="${fn:split(sz, '|')}"/><label class="size-option"><input type="radio" name="size" value="${sz}" style="display:none" onclick="updateSize(this);"><span class="size-label">${parts[0]}</span>&nbsp;<span class="size-price">&yen;${parts[1]}</span></label></c:forEach></div></div><div class="quantity-ctrl"><span>数量:</span><input type="number" name="quantity" value="1" min="1" max="${cloth.stock}"></div><button type="submit" class="btn btn-primary btn-lg">加入购物车</button></form></c:if><c:if test="${empty sessionScope.loginUser}"><a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary btn-lg">登录后购买</a></c:if></div></div></div></div></body></html>
