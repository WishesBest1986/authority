<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${ctx}/static/css/css.css">
</head>
<body>
  <form method="post">
    <div class="form-group">
      <label for="newPassword">新密码</label>
      <input type="text" id="newPassword" name="newPassword" />
      <input type="submit" value="${op}" />
    </div>
  </form>

</body>
</html>
