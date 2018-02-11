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
    
    <title>编辑管理员</title>
    
	<script type="text/javascript">
	function updateAdmin(){
		var form = document.forms[0];
		form.action = "<%=basePath%>s/admin/updateAdmin";
		form.method="post";
		form.submit();
	}
</script>

  </head>
  
  <body>
    <h1>编辑管理员</h1>
	<form action="" name="adminForm">
		<input type="hidden" name="id" value="${admin.id }"/>
		用户名：<input type="text" name="userName" value="${admin.userName }">
		密码：<input type="text" name="password" value="${admin.password }">
		超级管理员：<input type="text" name="superAdmin" value="${admin.superAdmin }">
		状态：<input type="text" name="status" value="${admin.status }">
		<input type="button" value="编辑" onclick="updateAdmin()"/>
	</form>
  </body>
  
</html>
