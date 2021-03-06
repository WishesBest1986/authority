<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="customfn" uri="http://neusoft.com/authority/tags/customfn" %>
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

  <shiro:hasPermission name="role:create">
    <a href="${ctx}/role/create.do">角色新增</a>
  </shiro:hasPermission>
  <table class="table">
    <thead>
      <tr>
        <th>角色名称</th>
        <th>角色描述</th>
        <th>拥有的资源</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${roleList}" var="role">
        <tr>
          <td>${role.role}</td>
          <td>${role.description}</td>
          <td>${customfn:resourceNames(role.resourceIds)}</td>
          <td>
            <shiro:hasPermission name="role:update">
              <a href="${ctx}/role/${role.id}/update.do">修改</a>
            </shiro:hasPermission>

            <shiro:hasPermission name="role:delete">
              <a href="${ctx}/role/${role.id}/delete.do">删除</a>
            </shiro:hasPermission>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>
