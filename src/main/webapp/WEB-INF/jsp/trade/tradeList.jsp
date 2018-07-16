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
<title>贸易列表</title>
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
	       	<h3 class="panel-title"><span class="glyphicon glyphicon-book"></span> 贸易管理</h3>
	    </div>
	    <div class="panel-body">
			       	 <table class="tbInfo">
						<tr>
							<td>标 题：</td>
							<td><input type="text" id="tradeList_queryTitle"
								name="tradeList_queryTitle" value=""></td>
							<td>类 型：</td>
							<td><select name="tradeList_queryType" id="tradeList_queryType" class="serchSelect">
							</select></td>
							<td>编 号：</td>
							<td><input type="text" id="tradeList_querySn"
								name="tradeList_querySn" value=""></td>
							<td>状 态：</td>
							<td><select name="tradeList_queryStatus"
								id="tradeList_queryStatus" class="serchSelect">
							</select></td>
						</tr>
						<tr>
							<td>行 业：</td>
							<td><select name="tradeList_queryIndustryCode"
								id="tradeList_queryIndustryCode" class="serchSelect">
							</select></td>
							<td>发票要求：</td>
							<td><select name="tradeList_queryInvoiceCode"
								id="tradeList_queryInvoiceCode" class="serchSelect">
							</select></td>
							<td>支付方式：</td>
							<td><select name="tradeList_queryPayCode"
								id="tradeList_queryPayCode" class="serchSelect">
							</select></td>
							<td>
								
							</td>
							<td>
								
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
	</div>
			
			<div class="panel panel-default">
			    <div class="panel-heading" >
			       	<span class="glyphicon glyphicon-list-alt"></span> 贸易信息列表
			    </div>
			    <div class="panel-body" style="padding-top: 1px;">
			       	 <input type="hidden" id="contextPath" value="${contextPath}" />
			       	 <div id="toolbar" class="btn-group">
							<button id="btn_add" type="button" class="btn btn-primary">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新 增
							</button>
						</div>
						<table id="table_tradeList" class="tb_list"></table>
						
			    </div>
			</div>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap-table.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap-table-zh-CN.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/tradeList.js"></script>
</body>
</html>