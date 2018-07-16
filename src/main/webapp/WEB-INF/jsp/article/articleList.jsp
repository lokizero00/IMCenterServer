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
<title>文章列表</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/bootstrap-table.css"
	rel="stylesheet">
<link
	href="<%=basePath%>static/bootstrap/css/bootstrap-datetimepicker.css"
	rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/style.css"
	rel="stylesheet">
</head>
<body>
	<div class="panel panel-default">
	    <div class="panel-heading" >
	       	<h3 class="panel-title"><span class="glyphicon glyphicon-book"></span> 文章管理</h3>
	    </div>
	    <div class="panel-body">
	       	 <table class="tbInfo">
				<tr>
					<td>标 题：</td>
					<td>
						<input type="text" id="articleList_queryTitle" name="articleList_queryTitle" value="">
					</td>
					<td>创建时间：从</td>
					<td>
						<div class="input-group date form_date col-md-5" data-date=""
								todayBtn="linked" data-date-format="yyyy-mm-dd"
								data-link-field="createTimeStart" data-link-format="yyyy-mm-dd">
								<input class="form-control" style="width: 200px; height: 29px" id="articleList_queryCreateTimeStart"
									size="16" type="text" value="" readonly> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-remove"></span></span> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
							<input type="hidden" id="articleList_hi_createTimeStart" value="" />
					</td>
					<td>到：</td>
					<td>
						<div class="input-group date form_date col-md-5" data-date=""
								data-date-format="yyyy-mm-dd" data-link-field="createTimeEnd"
								data-link-format="yyyy-mm-dd">
								<input class="form-control" style="width: 200px; height: 29px" id="articleList_queryCreateTimeEnd"
									size="16" type="text" value="" readonly> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-remove"></span></span> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
							<input type="hidden" id="articleList_hi_createTimeEnd" value="" />
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<button id="queryButton" class="btn btn-primary" type="button">查 询</button>
						<button id="resetButton" class="btn btn-default" type="button">清 空</button>
					</td>
				</tr>
			</table>
			
	    </div>
	</div>
	<div class="panel panel-default">
	    <div class="panel-heading" >
	       	<span class="glyphicon glyphicon-list-alt"></span> 文章信息列表
	    </div>
	    <div class="panel-body" style="padding-top: 1px;">
	       	 <input type="hidden" id="contextPath" value="${contextPath}" />
			 <div id="toolbar" class="btn-group">
				<button id="btn_add" type="button" class="btn btn-primary">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
			</div>
			<table id="table_articleList" class="tb_list"></table>
	    </div>
	</div>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap-table.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap-table-zh-CN.js"></script>
	<script
		src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.js"></script>
	<script
		src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/articleList.js"></script>

</body>
</html>