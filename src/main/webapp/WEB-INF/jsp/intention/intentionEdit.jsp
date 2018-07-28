<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = "//" + request.getHeader("host") + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%=basePath%>"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>意向金修改</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/bootstrap/css/style.css" rel="stylesheet">
</head>

<body>
 <div class="container">
     <div class="row" style="padding: 20px 0">
         <h3>修改用户意向金:</h3>
         <input type="hidden" id="contextPath" value="${contextPath}" />
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">用户名称</label>
          <div class="col-lg-5 col-md-6">
              <input class="form-control" name="username" id="username" type="text" disabled="disabled">
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">用户电话</label>
          <div class="col-lg-5 col-md-6">
              <input class="form-control" name="phone" id="phone" type="text" disabled="disabled">
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">总金额</label>
          <div class="col-lg-5 col-md-6">
              <input class="form-control" name="total" id="total" type="text">
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">可用额</label>
          <div class="col-lg-5 col-md-6">
              <input class="form-control" name="available" id="available" type="text">
          </div>
     </div>
     <div class="row form-group">
          <label class="control-label col-lg-1" for="name">冻结额</label>
          <div class="col-lg-5 col-md-6">
              <input class="form-control" name="freeze" id="freeze" type="text">
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote-zh-CN.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/intentionEdit.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/ewin.js"></script>
	<script type="text/javascript">
         toastr.options.positionClass = 'toast-center-center';
 	</script>
</body>
</html>
