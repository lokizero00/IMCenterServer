<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = "//" + request.getHeader("host")+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加管理员</title>
    <link href="images/favicon.png" rel="icon" />
    
	<script type="text/javascript">
	function addAdmin(){
		var form = document.forms[0];
		form.action = "<%=basePath%>s/admin/addAdmin";
		form.method="post";
		form.submit();
	}
</script>

  </head>
  
  <body>
    <h1>添加管理员</h1>
	<form action="" name="adminForm">
		用户名：<input type="text" name="userName">
		密码：<input type="text" name="password">
		超级管理员：<input type="text" name="superAdmin" value="0">
		状态：<input type="text" name="status" value="on">
		<input type="button" value="添加" onclick="addAdmin()">
	</form>
  </body>
</html>
