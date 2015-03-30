<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="customfn" uri="http://neusoft.com/authority/tags/customfn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
  <title></title>
  <link rel="stylesheet" href="${ctx}/static/css/css.css"/>
  <link rel="stylesheet" href="${ctx}/static/JQuery%20zTree%20v3.5.15/css/zTreeStyle/zTreeStyle.css">
  <style>
    ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width: 220px;height: 200px;overflow-y: scroll;overflow-x: auto;}
  </style>
</head>
<body>
<form:form method="post" commandName="role">
  <form:hidden path="id" />
  <form:hidden path="available" />

  <div class="form-group">
    <form:label path="role">角色名：</form:label>
    <form:input path="role" />
  </div>

  <div class="form-group">
    <form:label path="description">角色描述：</form:label>
    <form:input path="description" />
  </div>

  <div class="form-group">
    <form:label path="resourceIds">拥有的资源列表：</form:label>
    <form:hidden path="resourceIds" />
    <input type="text" id="resourceName" name="resourceName" value="${customfn:resourceNames(role.resourceIds)}" readonly>
    <a id="menuBtn" href="#">选择</a>
  </div>

  <form:button>${op}</form:button>
</form:form>

<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
  <ul id="tree" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>

<script src="${ctx}/static/js/jquery-1.11.0.min.js"></script>
<script src="${ctx}/static/JQuery%20zTree%20v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
<script>
  $(function() {
    var setting = {
      check: {
        enable: true,
        chkboxType: {"Y": "ps", "N": "ps"}
      },
      view: {
        dblClickExpand: false
      },
      data: {
        simpleData: {
          enable: true
        }
      },
      callback: {
        onCheck: onCheck
      }
    }

    var zNodes = [
      <c:forEach items="${resourceList}" var="r">
      <c:if test="${not r.rootNode}">
      {id: ${r.id}, pId: ${r.parentId}, name:"${r.name}", checked:${customfn:in(role.resourceIds, r.id) || customfn:descendantsOfGivenResources(role.resourceIds, r.id)}},
      </c:if>
      </c:forEach>
    ];

    function onCheck(e, treeId, treeNode) {
      var zTree = $.fn.zTree.getZTreeObj("tree"),
              nodes = zTree.getCheckedNodes(true),
              id = "",
              name = "";

      nodes.sort(function compare(a, b) {return a.id - b.id;});
      $.each(nodes,function(i, node){
        // 只提交全选的节点（如果父节点全选，不不提交其子节点）
        if (node.check_Child_State == 2 || node.check_Child_State == -1) {
          if (node.level == 0 || node.getParentNode().check_Child_State != 2) {
            if (id == '') {
              id += (node.id == 0 ? '' : node.id);
              name += node.name;
            } else {
              id += (node.id == 0 ? '' : "," + node.id);
              name += "," + node.name;
            }
          }
        }
      });

      $("#resourceIds").val(id);
      $("#resourceName").val(name);
    }

    function showMenu() {
      var cityObj = $("#resourceName");
      var cityOffset = cityObj.offset();
      $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
      $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
      $("#menuContent").fadeOut("fast");
      $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
      if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
      }
    }

    $.fn.zTree.init($("#tree"), setting, zNodes);
    $("#menuBtn").click(showMenu);
  });
</script>
</body>
<body>

</body>
</html>
