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
<title>用户列表</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/bootstrap-table.css"
	rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/style.css"
	rel="stylesheet">
</head>
<body>
	<div class="panel panel-default">
	    <div class="panel-heading" >
	       	<h3 class="panel-title"><span class="glyphicon glyphicon-book"></span> 用户管理</h3>
	    </div>
	    <div class="panel-body">
	       	 <div class="panel panel-info">
			    <div class="panel-heading" >
			       	<span class="glyphicon glyphicon-search"></span> 用户信息查询
			    </div>
			    <div class="panel-body" style="background-color:#F0F8FD;">
			       	 <table width="100%" class="tbInfo">
						<tr>
							<td>手机号：</td>
							<td><input type="text" id="userList_queryPhone" value=""></td>
							<td>类 型：</td>
							<td><select id="userList_queryStatus" class="serchSelect">
							</select></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="8" align="right">
								<button id="queryButton" class="btn btn-primary" type="button">查 询</button>
								<button id="resetButton" class="btn btn-primary" type="button">清 空</button>
							</td>
						</tr>
					</table>
			    </div>
			</div>
			
			<div class="panel panel-info">
			    <div class="panel-heading" >
			       	<span class="glyphicon glyphicon-list-alt"></span> 用户信息列表
			    </div>
			    <div class="panel-body" style="padding-top: 1px;">
			       	 <input type="hidden" id="contextPath" value="${contextPath}" />
					<div id="toolbar" class="btn-group">
						<!-- <button id="btn_add" type="button" class="btn btn-default">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
						</button> -->
					</div>
					<table id="table_userList" class="tb_list"></table>
			    </div>
			</div>
	    </div>
	</div>
	
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap-table.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap-table-zh-CN.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/userList.js"></script>

</body>
</html>