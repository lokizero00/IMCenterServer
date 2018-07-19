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
<title>新建通知</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
	<input type="hidden" id="contextPath" value="${contextPath}" />
	<div class="panel panel-default">
	    <div class="panel-heading">
	       	<span class="glyphicon glyphicon-tags"></span> 新建通知
	    </div>
	    <div class="panel-body">
	        <div class="form-horizontal">
		
		<div class="form-group">
			<label for="noticeAdd_title" class="col-sm-2 control-label">标 题：</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" id="noticeAdd_title"
					placeholder="请输入标题">
			</div>
		</div>
		<div class="form-group">
			<label for="noticeAdd_relationType" class="col-sm-2 control-label">消息类型：</label>
			<div class="col-sm-8">
				<select class="form-control" id="noticeAdd_relationType"
					disabled="disabled">
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="noticeAdd_content" class="col-sm-2 control-label">内 容：</label>
			<div class="col-sm-8">
				<textarea class="form-control" rows="5" id="noticeAdd_content"
					placeholder="请输入内容"></textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-8">
				<div class="checkbox">
					<label> <input type="checkbox" id="noticeAdd_send">下发通知
					</label>
				</div>
			</div>
		</div>

		<div class="form-group">
			<div  align="center">
				<button type="submit" class="btn btn-primary" id="btnSubmit">提 交</button>
				
				<button type="button" id="btnBack" onclick="window.history.go(-1);"
					class="btn btn-primary">返 回</button>
			</div>
		</div>
	</div>
	    </div>
	</div>
	
	
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/addNotice.js"></script>
</body>
</html>
