<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%=basePath%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新建文章</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=basePath%>static/summernote/css/summernote.css"
	rel="stylesheet">
</head>

<body>
	<input type="hidden" id="contextPath" value="${contextPath}" />
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-10">
				<h1>新建文章</h1>
			</div>
		</div>
		<div class="form-group">
			<label for="articleAdd_title" class="col-sm-2 control-label">标题</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="articleAdd_title"
					placeholder="请输入标题">
			</div>
		</div>
		<form class="form-horizontal required-validate" action="#"
			enctype="multipart/form-data" method="post"
			onsubmit="return iframeCallback(this, pageAjaxDone)">
			<div class="form-group">
				<label for="articleAdd_content" class="col-sm-2 control-label">内容</label>
				<div class="col-sm-8">
					<div class="summernote" id="articleAdd_content"
						placeholder="请输入文章的正文内容" action="<%=basePath%>s/io/uploadImage"></div>
				</div>
			</div>
		</form>
		<div class="form-group">
			<label for="articleAdd_status" class="col-sm-2 control-label">状态</label>
			<div class="col-sm-8">
				<select class="form-control" id="articleAdd_status">
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary" id="btnSubmit">提交</button>
				<button type="button" id="btnBack" onclick="window.history.go(-1);"
					class="btn btn-primary">返回</button>
			</div>
		</div>
	</div>

	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote-zh-CN.js"></script>
	<script src="<%=basePath%>static/js/articleAdd.js"></script>
</body>
</html>
