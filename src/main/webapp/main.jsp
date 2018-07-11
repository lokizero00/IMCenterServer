<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = "//" + request.getHeader("host")+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>互联制造</title>
<style>
	body{
		font-size:15px;
		}
</style>
</head>
<frameset rows="50,*" cols="*" frameborder="yes" border="1"
	framespacing="1">
	<frame src="top.jsp" name="topFrame" scrolling="No" noresize="noresize"
		id="topFrame" title="topFrame" />    
    <frameset cols="160,*" frameborder="No" border="0" framespacing="1">    
        <frame src="left.jsp" name="leftFrame" scrolling="No"
			noresize="noresize" id="leftFrame" title="leftFrame" />    
        <frame src="<%=basePath%>s/trade/tradeList" name="mainFrame" noresize="noresize"
			id="mainFrame" title="mainFrame" />    
    </frameset>    
</frameset> 
</html>