<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = "//" + request.getHeader("host")+ path + "/";
%>
<html>
<body>
<img src="<%=basePath%>images/welcome.jpg" width="500" height="374" /> 
<h2>都说了，只有后台接口，看什么看！！！</h2>
</body>
</html>
