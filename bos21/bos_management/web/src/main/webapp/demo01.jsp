<%--
  Created by IntelliJ IDEA.
  User: John
  Date: 2017/11/14
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<body>
<!-- 判断当前用户是否已通过认证,认证成功才可以看到标签中的内容 -->
<shiro:authenticated>
    您已通过认证
</shiro:authenticated>
<hr />
<!-- 判断当前用户是否用于对应的权限,如有则可以看到标签中的内容 -->
<shiro:hasPermission name="area1">
    您拥有area权限
</shiro:hasPermission>
<hr />
<!-- 判断当前用户是否是某个角色,如是则可以看到标签中的内容 -->
<shiro:hasRole name="zs">
    您是管理员角色
</shiro:hasRole>
</body>
</body>
</html>
