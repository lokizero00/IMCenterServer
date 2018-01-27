<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="error" tagdir="/WEB-INF/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<script type="text/javascript">
		function tradeStatusSelectChanged(){
			var tradeStatusSelect=document.getElementById("tradeStatusSelect");
			if(tradeStatusSelect.selectedIndex==0){
				//删除文本框
				$('div#div2').remove();
			}else{
				//添加文本框
				$('div#div1').append('<div id="div2">拒绝原因：<input type="text" id="refuseReasonInput" name="refuseReason" /></div>');
			}
		}
	</script> 
	<script type="text/javascript">
	function tradeVerify(){
		var form = document.forms['tradeVerifyForm'];
		form.action = "<%=basePath%>s/trade/tradeVerify";
		form.method="post";
		form.submit();
	}
</script>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>
    <title>贸易审核</title>
  </head>
  
  <body>
    <h1>贸易详情</h1>
	<c:if test="${trade.type == 'trade_demand'}">
		<jsp:include   page="tradeDemand.jsp" flush="true"/>
	</c:if>
	<c:if test="${trade.type == 'trade_supply'}">
		<jsp:include   page="tradeSupply.jsp" flush="true"/>
	</c:if>
	<error:error/>
	<form action="" name="tradeVerifyForm">	
		<input type="hidden" name="tradeId" value="${trade.id }"/>
		审核结果：
		<select name="tradeStatus" id="tradeStatusSelect" onchange="tradeStatusSelectChanged()">
			<option value="trade_tendering">通过</option>
			<option value="trade_under_carriage">不通过</option>
		</select>
		<div id="div1" style="border:1px solid #ccc">
		</div>
		<input type="button" value="提交" onclick="tradeVerify()"/>
		<input type="button" value="返回" onclick="window.history.back()"/>
	</form>
  </body>
  
</html>
