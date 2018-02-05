<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%=basePath%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<input type="hidden" id="contextPath" value="${contextPath }" />
	<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">互联制造</a>
		</div>
		<ul class="nav navbar-nav navbar-right" id="ul_adminInfo">
			
		</ul>
	</div>
	</nav>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/top.js"></script>
</body>
</html>