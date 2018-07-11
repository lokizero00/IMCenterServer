<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = "//" + request.getHeader("host")+ path + "/";
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
<link href="<%=basePath%>static/bootstrap/css/font-awesome.css"
	rel="stylesheet">
<link href="<%=basePath%>static/login/css/form-elements.css"
	rel="stylesheet">
<link href="<%=basePath%>static/login/css/style.css" rel="stylesheet">

</head>

<body style="background-color: #049EC4;">

	<input type="hidden" id="contextPath" value="${contextPath}" />
	<div class="top-content">

		<div class="inner-bg">
			<div class="container">
				<div class="row">
					<div class="col-sm-5 col-sm-offset-4 form-box">
						<div class="form-top">
							<div class="form-top-left">
								<h3>系统登录</h3>
								<p></p>
							</div>
							<div class="form-top-right">
								<i class="fa fa-key"></i>
							</div>
						</div>
						<div class="form-bottom">
							<form role="form" action="" method="post" class="login-form">
								<div class="form-group">
									<input id="userName" type="text" class="form-control"
										placeholder="用户名" aria-describedby="basic-addon1">
								</div>
								<div class="form-group">
									<input id="password" type="password" class="form-control"
										placeholder="密码" aria-describedby="basic-addon1">
								</div>
								<button type="button" class="btn" id="btn_login">登 录</button>
							</form>
						</div>
					</div>
				</div>

			</div>
		</div>

	</div>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/login.js"></script>
</body>
</html>
