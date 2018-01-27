<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  		<h1>历史日志</h1>
		<table border="1" width="100%">
		<tbody>
			<tr>
				<th>日志</th>
				<th>时间</th>
			</tr>
			<c:if test="${!empty tradeLogList }">
				<c:forEach items="${tradeLogList}" var="tradeLog">
					<tr>
						<td>${tradeLog.content }</td>
						<td>${tradeLog.createTime }</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
  </body>
  
</html>
