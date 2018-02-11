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
<body>
	<input type="hidden" id="contextPath" value="${contextPath }" />
	<table class="table">
		<caption>通知详情</caption>
		<tbody id="tb_noticeDetail">
		</tbody>
	</table>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/notice.js"></script>
</body>
</html>
