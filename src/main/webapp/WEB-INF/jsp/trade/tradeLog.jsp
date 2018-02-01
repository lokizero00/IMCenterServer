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
<body>
	<div id="div_tradeLog" >
		<table class="table">
			<caption>历史日志</caption>
			<thead>
				<tr>
					<th>日志</th>
					<th>时间</th>
				</tr>
			</thead>
			<tbody id="tb_tradeLog">
			</tbody>
		</table>
	</div>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/tradeLog.js"></script>
</body>
</html>
