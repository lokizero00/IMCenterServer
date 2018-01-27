<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="error" tagdir="/WEB-INF/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>
    <title>贸易列表</title>
<script type="text/javascript">
	function search(type){
		if(type=='search'){
			saveSearchParam();
		}else{
			clearSearchParam();
		}
		var form = document.forms['tradeSearchForm'];
		form.action = "<%=basePath%>s/trade/getTradeList";
		form.method="get";
		form.submit();
	}
</script> 
<script type="text/javascript">
	function saveSearchParam(){
		var storage = window.localStorage;
		var searchTitle = document.getElementById('search_title').value;
		var searchType = document.getElementById('search_type').selectedIndex;
		var searchSn=document.getElementById('search_sn').value;
		var searchStatus=document.getElementById('search_status').selectedIndex;
		var searchIndustry=document.getElementById('search_industryCode').selectedIndex;
		var searchInvoice=document.getElementById('search_invoiceCode').selectedIndex;
		var searchPay=document.getElementById('search_payCode').selectedIndex;
		storage.setItem("tradeList_search_title",searchTitle);
		storage.setItem("tradeList_search_type",searchType);
		storage.setItem("tradeList_search_sn",searchSn);
		storage.setItem("tradeList_search_status",searchStatus);
		storage.setItem("tradeList_search_industry",searchIndustry);
		storage.setItem("tradeList_search_invoice",searchInvoice);
		storage.setItem("tradeList_search_pay",searchPay);
	}
	function clearSearchParam(){
		var storage = window.localStorage;
		storage.clear();
		document.getElementById('search_title').value='';
		document.getElementById('search_type').options[0].selected = true;
		document.getElementById('search_sn').value='';
		document.getElementById('search_status').options[0].selected = true;
		document.getElementById('search_industryCode').options[0].selected = true;
		document.getElementById('search_invoiceCode').options[0].selected = true;
		document.getElementById('search_payCode').options[0].selected = true;
	}
	window.onload = function(){
		var storage = window.localStorage;
		document.getElementById('search_title').value=storage.getItem("tradeList_search_title");
		document.getElementById('search_type').options[ storage.getItem("tradeList_search_type")].selected = true;
		document.getElementById('search_sn').value=storage.getItem("tradeList_search_sn");
		document.getElementById('search_status').options[ storage.getItem("tradeList_search_status")].selected = true;
		document.getElementById('search_industryCode').options[ storage.getItem("tradeList_search_industry")].selected = true;
		document.getElementById('search_invoiceCode').options[ storage.getItem("tradeList_search_invoice")].selected = true;
		document.getElementById('search_payCode').options[ storage.getItem("tradeList_search_pay")].selected = true;
	}
</script>
  </head>
  
  <body>
    <h6><a href="<%=basePath%>login/adminLoginOut">注销</a></h6>
    <h1>贸易列表</h1>
    <form action="" name="tradeSearchForm">
	    <table border="1" width="100%">
	    		<tbody>
	    			<tr>
	    				<td>标题：</td>
	    				<td>
	    					<input type="text" name="title" id="search_title" value="" >
	    				</td>
	    				<td>类型：</td>
	    				<td>
						<select name="type" id="search_type" >
							<option value=''>全部</option>
							<c:forEach items="${tradeTypeQueryList}" var="tradeType">
								<option value="${tradeType.code}">${tradeType.value}</option>
							</c:forEach>
						</select>
					</td>
	    			</tr>
	    			<tr>
	    				<td>编号：</td>
	    				<td>
	    					<input type="text" id="search_sn" name="sn" value="" >
	    				</td>
	    				<td>状态：</td>
		    			<td>
						<select name="status" id="search_status" >
							<option value=''>全部</option>
							<c:forEach items="${tradeStatusQueryList}" var="tradeStatus">
								<option value="${tradeStatus.code}">${tradeStatus.value}</option>
							</c:forEach>
						</select>
					</td>
	    			</tr>
	    			<tr>
	    				<td>行业：</td>
	    				<td>
	    					<select name="industryCode" id="search_industryCode" >
							<option value=''>全部</option>
							<c:forEach items="${tradeIndustryQueryList}" var="tradeIndustry">
								<option value="${tradeIndustry.code}">${tradeIndustry.value}</option>
							</c:forEach>
						</select>
	    				</td>
	    				<td>发票要求：</td>
		    			<td>
						<select name="invoiceCode" id="search_invoiceCode" >
							<option value=''>全部</option>
							<c:forEach items="${tradeInvoiceQueryList}" var="tradeInvoice">
								<option value="${tradeInvoice.code}">${tradeInvoice.value}</option>
							</c:forEach>
						</select>
					</td>
	    			</tr>
	    			<tr>
		    			<td>支付方式：</td>
		    			<td>
						<select name="payCode" id="search_payCode" >
							<option value=''>全部</option>
							<c:forEach items="${tradePayQueryList}" var="tradePay">
								<option value="${tradePay.code}">${tradePay.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="button" value="搜索" onclick="search('search')"/>
					</td>
					<td>
						<input type="reset" value="清空" onclick="search('')"/>
					</td>
				</tr>
	    		</tbody>
	    </table>
    </form>
    <error:error/>
	<table border="1" width="100%">
		<tbody>
			<tr>
				<th>贸易编号</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>用户</th>
				<th>标题</th>
				<th>类型</th>
				<th>地区</th>
				<th>状态</th>
				<th>意向金</th>
				<th>操作</th>
			</tr>
			<c:if test="${!empty tradeList }">
				<c:forEach items="${tradeList}" var="trade">
					<tr>
						<td>${trade.sn }</td>
						<td>${trade.createTime }</td>
						<td>${trade.updateTime }</td>
						<td>${trade.userNickName }</td>
						<td>${trade.title }</td>
						<td>${trade.typeName }</td>
						<td>${trade.provinceName },${trade.cityName},${trade.townName}</td>
						<td>${trade.statusName }</td>
						<td>${trade.intention }</td>
						<td>
							<a href="<%=basePath%>s/trade/getTrade?id=${trade.id}">查看</a>
							<c:if test="${trade.status=='trade_verify'}">
								<a href="<%=basePath%>s/trade/getTradeVerify?id=${trade.id}">审核</a>
							</c:if>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
  </body>
</html>
