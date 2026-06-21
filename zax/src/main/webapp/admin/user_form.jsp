<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>${empty editUser?'添加用户':'编辑用户'} - 智安行衣橱</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css"></head><body>
<header class="admin-topbar"><h2>后台管理</h2><ul class="top-links"><li><a href="${pageContext.request.contextPath}/admin/dashboard">控制面板</a></li><li><a href="${pageContext.request.contextPath}/admin/clothes">服装管理</a></li><li><a href="${pageContext.request.contextPath}/admin/orders">订单管理</a></li><li><a href="${pageContext.request.contextPath}/admin/users" class="active">用户管理</a></li><li><a href="${pageContext.request.contextPath}/clothes">返回前台</a></li></ul></header>
<div class="admin-main"><h1>${empty editUser?'添加用户':'编辑用户'}</h1><a href="${pageContext.request.contextPath}/admin/users" style="color:#7f8c9b;font-size:13px">&larr; 返回用户列表</a>
<div class="form-panel" style="margin-top:18px"><form action="${pageContext.request.contextPath}/admin/users" method="post">
<input type="hidden" name="action" value="${empty editUser?'add':'update'}">
<c:if test="${not empty editUser}"><input type="hidden" name="id" value="${editUser.id}"></c:if>
<div class="form-row"><div class="form-group"><label>用户名</label><input type="text" name="username" value="${editUser.username}" required></div><div class="form-group"><label>密码 ${empty editUser?'*':'（留空不修改）'}</label><input type="password" name="password" ${empty editUser?'required':''}></div></div>
<div class="form-group"><label>手机号</label><input type="text" name="phone" value="${editUser.phone}" required></div>
<div class="form-group"><label>地址</label><input type="text" name="address" value="${editUser.address}"></div>
<button type="submit" class="btn btn-primary btn-block">${empty editUser?'添加用户':'保存修改'}</button>
</form></div></div></body></html>