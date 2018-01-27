<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
		<table border="1" width="100%">
			<tbody>
				<tr>
					<td>贸易编号</td>
					<td>${trade.sn}</td>
					<td>用户</td>
					<td>${trade.userNickName}</td>
				</tr>
				<tr>
					<td>创建时间</td>
					<td>${trade.createTime}</td>
					<td>更新时间</td>
					<td>${trade.updateTime}</td>
				</tr>
				<tr>
					<td>标题</td>
					<td colspan="3">${trade.title}</td>
				</tr>
				<tr>
					<td>类型</td>
					<td>${trade.typeName}</td>
					<td>地区</td>
					<td>${trade.provinceName },${trade.cityName},${trade.townName}</td>
				</tr>
				<tr>
					<td>内容</td>
					<td colspan="3">${trade.content}</td>
				</tr>
				<tr>
					<td>供应资源</td>
					<td>${trade.resourceName}</td>
					<td>产能</td>
					<td>${trade.capacity}</td>
				</tr>
				<tr>
					<td>意向金</td>
					<td>${trade.intention}</td>
					<td>状态</td>
					<td>${trade.statusName}</td>
				</tr>
				<tr>
					<td>所属行业</td>
					<td>
						${trade.industryName} 
					</td>
					<td>发票要求</td>
					<td>
						${trade.invoiceName} 
					</td>
				</tr>
				<tr>
					<td>支付方式</td>
					<td>
						${trade.paycodeName} 
					</td>
				</tr>
			</tbody>
		</table>
		<c:if test="${!empty tradeLogList }">
			<jsp:include   page="tradeLog.jsp" flush="true"/>
		</c:if>
  </body>
</html>
