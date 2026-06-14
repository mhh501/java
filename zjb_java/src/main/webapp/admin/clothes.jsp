<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>服装管理 - 后台管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <div class="admin-sidebar">
        <h2>后台管理</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">控制面板</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/clothes" class="active">服装管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/orders">订单管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">用户管理</a></li>
        </ul>
        <div class="sidebar-footer">
            <p>管理员：${sessionScope.user.username}</p>
            <a href="${pageContext.request.contextPath}/auth?action=logout" class="btn btn-danger btn-sm">退出</a>
        </div>
    </div>
    <div class="admin-main">
        <h1>服装管理</h1>
        <form action="${pageContext.request.contextPath}/admin/clothes" method="get" class="search-form">
            <input type="text" name="keyword" placeholder="搜索服装名称" value="${param.keyword}">
            <select name="category">
                <option value="">全部类别</option>
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat}" ${param.category eq cat ? 'selected' : ''}>${cat}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">搜索</button>
        </form>
        <div class="form-panel">
            <h3>${not empty clothes ? '编辑服装' : '上架新服装'}</h3>
            <form action="${pageContext.request.contextPath}/admin/clothes" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="${not empty clothes ? 'update' : 'add'}">
                <c:if test="${not empty clothes}">
                    <input type="hidden" name="id" value="${clothes.id}">
                    <input type="hidden" name="existingImage" value="${clothes.image}">
                </c:if>
                <div class="form-row">
                    <div class="form-group">
                        <label>服装名称</label>
                        <input type="text" name="name" value="${clothes.name}" required>
                    </div>
                    <div class="form-group">
                        <label>风格</label>
                        <input type="text" name="style" value="${clothes.style}" placeholder="如：休闲、商务、运动" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>类别</label>
                        <input type="text" name="category" value="${clothes.category}" placeholder="如：上衣、裤子、裙装" required>
                    </div>
                    <div class="form-group">
                        <label>可选尺码（逗号分隔）</label>
                        <input type="text" name="size" value="${clothes.size}" placeholder="如：S,M,L,XL" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>价格</label>
                        <input type="number" name="price" value="${clothes.price}" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label>库存</label>
                        <input type="number" name="stock" value="${clothes.stock}" min="0" required>
                    </div>
                </div>
                <div class="form-group">
                    <label>描述</label>
                    <textarea name="description" rows="3">${clothes.description}</textarea>
                </div>
                <div class="form-group">
                    <label>图片上传</label>
                    <input type="file" name="imageFile" accept="image/*">
                    <c:if test="${not empty clothes.image}">
                        <p>当前图片：<img src="${pageContext.request.contextPath}/uploads/${clothes.image}" width="100"></p>
                    </c:if>
                </div>
                <button type="submit" class="btn btn-primary">${not empty clothes ? '保存修改' : '上架服装'}</button>
                <c:if test="${not empty clothes}">
                    <a href="${pageContext.request.contextPath}/admin/clothes" class="btn">取消编辑</a>
                </c:if>
            </form>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th><th>图片</th><th>名称</th><th>类别</th><th>风格</th><th>尺码</th><th>价格</th><th>库存</th><th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${clothesList}" var="c">
                    <tr>
                        <td>${c.id}</td>
                        <td>
                            <c:if test="${not empty c.image}">
                                <img src="${pageContext.request.contextPath}/uploads/${c.image}" width="50">
                            </c:if>
                        </td>
                        <td>${c.name}</td>
                        <td>${c.category}</td>
                        <td>${c.style}</td>
                        <td>${c.size}</td>
                        <td>&yen;${c.price}</td>
                        <td>${c.stock}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/clothes?action=edit&id=${c.id}" class="btn btn-sm">编辑</a>
                            <a href="${pageContext.request.contextPath}/admin/clothes?action=delete&id=${c.id}" class="btn btn-danger btn-sm" onclick="return confirm('确定删除？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>