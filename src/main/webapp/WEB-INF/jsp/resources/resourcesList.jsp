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
<title>资源列表</title>
	<link rel="stylesheet" href="<%=basePath%>static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>static/bootstrap/css/bootstrap-table.css">
    <link href="<%=basePath%>static/css/toastr.min.css" rel="stylesheet" />
    <link href="<%=basePath%>static/bootstrap/css/style.css" rel="stylesheet">
</head>
<body>
 <!--<div class="panel panel-default">
	    <div class="panel-heading" >
	       	<h3 class="panel-title"><span class="glyphicon glyphicon-book"></span> 资源管理</h3>
	    </div>
	    <div class="panel-body">
	       	 <table class="tbInfo">
				<tr>
					<td>资源名称：</td>
					<td>
						<input type="text" id="name" name="name" value="">
					</td>
				</tr>
				<tr>
					<td>类型：</td>
					<td>
					<select id="type" class="form-control">
						<option value="">全部</option>
						<option value="menu">menu</option>
						<option value="page">page</option>
						<option value="action">action</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<select id="status" class="form-control">
							<option value="">全部</option>
							<option value="on">启用</option>
							<option value="off">禁用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="8" align="right">
						<button id="queryButton" class="btn btn-primary" type="button">查 询</button>
						<button id="resetButton" class="btn btn-default" type="button">清 空</button>
					</td>
				</tr>
			</table>
			
	    </div>
	</div> -->
	<div class="panel panel-default">
	    <div class="panel-heading" >
	       	<span class="glyphicon glyphicon-list-alt"></span> 资源列表
	    </div>
	    <div class="panel-body" style="padding-top: 1px;">
	       	 <input type="hidden" id="contextPath" value="${contextPath}" />
	       	 <div id="toolbar" class="btn-group">
				<button id="btn_add" type="button" class="btn btn-primary">
					<span class="glyphicon glyphicon-plus"></span>新增
				</button>
			</div>
			 <input type="button" id="expandAllTree" value="展开所有"  class="btn btn-defaul"/>
        	<input type="button" id="collapseAllTree" value="折叠所有" class="btn btn-defaul"/>
			<table id="table_resourcesList" ></table>
	    </div>
	</div>
	 <script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
    <script src="<%=basePath%>static/bootstrap/js/bootstrap.js"></script>
    <script src="<%=basePath%>static/bootstrap/js/bootstrap-table.js"></script>
    <script src="<%=basePath%>static/bootstrap/js/bootstrap-treegrid.js"></script>
    <script src="<%=basePath%>static/bootstrap/js/ewin.js"></script>
    <script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/toastr.min.js"></script>
	<script type="text/javascript">
         toastr.options.positionClass = 'toast-center-center';
 	</script> 
	<script src="<%=basePath%>static/js/resourcesList.js"></script>
</body>
</html>