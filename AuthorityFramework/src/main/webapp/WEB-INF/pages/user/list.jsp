<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="customfn" uri="http://neusoft.com/authority/tags/customfn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${ctx}/static/css/css.css">
</head>
<body>
<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>

<shiro:hasPermission name="user:create">
  <a href="${ctx}/user/create.do">用户新增</a>
</shiro:hasPermission>

<table class="table">
  <thead>
    <tr>
      <th>用户名</th>
      <th>所属组织</th>
      <th>角色列表</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${userList}" var="user">
      <tr>
        <td>${user.username}</td>
        <td>${customfn:organizationName(user.organizationId)}</td>
        <td>${customfn:roleNames(user.roleIds)}</td>
        <td>
          <shiro:hasPermission name="user:update">
            <a href="${ctx}/user/${user.id}/update.do">修改</a>
          </shiro:hasPermission>

          <shiro:hasPermission name="user:delete">
            <a href="${ctx}/user/${user.id}/delete.do">删除</a>
          </shiro:hasPermission>

          <shiro:hasPermission name="user:update">
            <a href="${ctx}/user/${user.id}/changePassword.do">改密</a>
          </shiro:hasPermission>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

</body>
</html>
