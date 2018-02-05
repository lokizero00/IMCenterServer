<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%=basePath%>"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登陆</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	margin-left: auto;
	margin-right: auto;
	margin-TOP: 100PX;
	width: 20em;
}
</style>
</head>

<body>
	<form >
		<input type="hidden" id="contextPath" value="${contextPath}" />
		<!--下面是用户名输入框-->
		<div class="input-group">
			<span class="input-group-addon" id="basic-addon1">@</span> <input
				id="userName" type="text" class="form-control" placeholder="用户名"
				aria-describedby="basic-addon1">
		</div>
		<br>
		<!--下面是密码输入框-->
		<div class="input-group">
			<span class="input-group-addon" id="basic-addon1">@</span> <input
				id="password" type="text" class="form-control" placeholder="密码"
				aria-describedby="basic-addon1">
		</div>
		<br>
		<!--下面是登陆按钮,包括颜色控制-->
		<button type="button" style="width: 280px;" id="btn_login" class="btn btn-default">登
			录</button>
	</form>

	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/login.js"></script>
</body>
</html>
