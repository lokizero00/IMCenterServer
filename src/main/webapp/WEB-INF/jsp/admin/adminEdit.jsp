<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = "//" + request.getHeader("host") + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%=basePath%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>管理员修改</title>
<link href="images/favicon.png" rel="icon" />
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath%>static/summernote/css/summernote.css" rel="stylesheet">
<link href="<%=basePath%>static/css/toastr.min.css" rel="stylesheet" />
</head>
<body>
<div class="container">
     <div class="row" style="padding: 20px 0">
         <h3>管理员修改</h3>
         <input type="hidden" id="contextPath" value="${contextPath}" />
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">用户名</label>
          <div class="col-lg-5 col-md-6">
              <input class="form-control" name="userName" id="userName" type="text">
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">角色</label>
          <div class="col-lg-5 col-md-6">
              <select id="roleName" class="form-control">
					
				</select>
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">状态</label>
          <div class="col-lg-5 col-md-6">
              <select id="status" class="form-control">
					<option value="on">启用</option>
					<option value="off">禁用</option>
				</select>
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name"></label>
          <div class="col-lg-5 col-md-6">
              <input name="superAdmin" id="superAdmin" type="checkbox"> 超级管理员
          </div>
     </div>
  	<div class="row">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn btn-primary" id="btnSubmit">提交</button>
			<button type="button" id="btnBack" onclick="window.history.go(-1);" class="btn btn-primary">返回</button>
		</div>
	</div>
</div>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/toastr.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote-zh-CN.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/ewin.js"></script>
	<script type="text/javascript">
         toastr.options.positionClass = 'toast-center-center';
 	</script>
	<script src="<%=basePath%>static/js/adminEdit.js"></script>
</body>
</html>