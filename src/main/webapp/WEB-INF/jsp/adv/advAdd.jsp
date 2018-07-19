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
<title>新建广告</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=basePath%>static/summernote/css/summernote.css"
	rel="stylesheet">
</head>

<body>
<form action="<%=basePath%>s/io/uploadImage" method="post" enctype="multipart/form-data">
	<div class="panel panel-default">
	    <div class="panel-heading">
	       	<span class="glyphicon glyphicon-tags"></span> 新建广告
	    </div>
	    <div class="panel-body">
	    	<input type="hidden" id="contextPath" value="${contextPath}" />
				<div class="form-horizontal">
					<div class="form-group">
						<label for="advAdd_title" class="col-sm-2 control-label">标 题：</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="advAdd_title"
								placeholder="请输入标题">
						</div>
					</div>
					<form class="form-horizontal required-validate" action="#"
						enctype="multipart/form-data" method="post"
						onsubmit="return iframeCallback(this, pageAjaxDone)">
						<div class="form-group">
							<label for="advAdd_content" class="col-sm-2 control-label">内 容：</label>
							<div class="col-sm-8">
								<div class="summernote" id="advAdd_content"
									placeholder="请输入广告的正文内容" action="<%=basePath%>s/io/uploadImage"></div>
							</div>
						</div>
					</form>
					<div class="form-group">
						<label for="advAdd_state" class="col-sm-2 control-label">状 态：</label>
						<div class="col-sm-8">
							<select class="form-control" id="advAdd_state">
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="advAdd_linkable" class="col-sm-2 control-label">链接化：</label>
						<div class="col-sm-8">
							<select class="form-control" id="advAdd_linkable">
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="advAdd_linkUrl" class="col-sm-2 control-label">跳转链接：</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="advAdd_linkUrl"
								placeholder="请输入跳转链接">
						</div>
					</div>
					
					<div class="form-group">
						<label for="advAdd_previewUrl" class="col-sm-2 control-label">上传缩略图：</label>
						<div class="col-sm-8">
							  
							    <input type="file" name="file"  id="advAdd_previewUrl">
							
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary" id="btnSubmit">提 交</button>
							<button type="button" id="btnBack" onclick="window.history.go(-1);"
								class="btn btn-primary">返 回</button>
						</div>
					</div>
				</div>
	    </div>
	</div>
	</form>	
	
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote.js"></script>
	<script src="<%=basePath%>static/summernote/js/summernote-zh-CN.js"></script>
	<script src="<%=basePath%>static/js/advAdd.js"></script>
</body>
</html>
