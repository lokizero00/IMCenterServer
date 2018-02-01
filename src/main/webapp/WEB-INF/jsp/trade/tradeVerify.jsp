<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="<%=basePath%>"></c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>贸易审核</title>
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/bootstrap-table.css"
	rel="stylesheet">
</head>

<body>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<jsp:include page="trade.jsp" />
	<jsp:include page="tradeLog.jsp" />

	<div class="form-group">
		<div>
			<label for="name">请选择审核结果</label> <select class="form-control"
				id="select_verifyResult"></select>
		</div>
		<div class="form-group" id="div_tradeRefuseReason" style="display:none;">
			<label for="name">拒绝原因</label>
			<textarea class="form-control" id="ta_tradeRefuseReason" rows="3"></textarea>
		</div>
		<div>
			<button type="button" id="btnVerify" class="btn btn-success">提交</button>
			<button type="button" id="btnBack" class="btn btn-primary">返回</button>
		</div>
	</div>
	<script src="<%=basePath%>static/js/tradeVerify.js"></script>
</body>
</html>
