<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>贸易详情</title>
  </head>
  
  <body>
    <h1>贸易详情</h1>
    <c:if test="${trade.type == 'trade_demand'}">
		<jsp:include   page="tradeDemand.jsp" flush="true"/>
	</c:if>
	<c:if test="${trade.type == 'trade_supply'}">
		<jsp:include   page="tradeSupply.jsp" flush="true"/>
	</c:if>
	<input type="button" value="返回" onclick="window.history.back()"/>
  </body>
  
</html>
