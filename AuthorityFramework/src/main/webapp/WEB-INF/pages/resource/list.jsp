<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${ctx}/static/css/css.css" />
    <link rel="stylesheet" href="${ctx}/static/jquery-treetable/stylesheets/jquery.treetable.css" />
    <link rel="stylesheet" href="${ctx}/static/jquery-treetable/stylesheets/jquery.treetable.theme.default.css" />
    <style>
      #table th, #table td {
        font-size: 14px;
        padding: 8px;
      }
    </style>
</head>
<body>
  <c:if test="${not empty msg}">
    <div class="message">${msg}</div>
  </c:if>

  <table id="table">
    <thead>
      <tr>
        <th>名称</th>
        <th>类型</th>
        <th>URL路径</th>
        <th>权限字符串</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${resourceList}" var="resource">
        <tr data-tt-id="${resource.id}" <c:if test="${not resource.rootNode}">data-tt-parent-id="${resource.parentId}"</c:if>>
          <td>${resource.name}</td>
          <td>${resource.type.info}</td>
          <td>${resource.url}</td>
          <td>${resource.permission}</td>
          <td>
            <shiro:hasPermission name="resource:create">
              <c:if test="${resource.type ne 'button'}">
                <a href="${ctx}/resource/${resource.id}/appendChild.do">添加子节点</a>
              </c:if>
            </shiro:hasPermission>

            <shiro:hasPermission name="resource:update">
              <a href="${ctx}/resource/${resource.id}/update.do">修改</a>
            </shiro:hasPermission>

            <shiro:hasPermission name="resource:delete">
              <a class="deleteBtn" href="#" data-id="${resource.id}">删除</a>
            </shiro:hasPermission>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <script src="${ctx}/static/js/jquery-1.11.0.min.js"></script>
  <script src="${ctx}/static/jquery-treetable/javascripts/src/jquery.treetable.js"></script>
  <script>
    $(function() {
      $("#table").treetable({expandable: true}).treetable("expandNode", 1);
      $(".deleteBtn").click(function() {
        if (confirm("确认删除吗？")) {
          location.href = "${ctx}/resource/" + $(this).data("id") + "/delete.do";
        }
      });
    });
  </script>
</body>
</html>
