<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = "//" + request.getHeader("host")+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>
    <title>管理员列表</title>
    <link href="images/favicon.png" rel="icon" />
    
<script type="text/javascript">
	function del(id){
		$.get("<%=basePath%>s/admin/delAdmin?id=" + id,function(data){
			if("success" == data.result){
				alert("删除成功");
				window.location.reload();
			}else{
				alert("删除失败");
			}
		});
	}
</script>
  </head>
  
  <body>
    <h6><a href="<%=basePath%>s/admin/toAddAdmin">添加管理员</a></h6>
    <h6><a href="<%=basePath%>login/adminLoginOut">注销</a></h6>
	<table border="1">
		<tbody>
			<tr>
				<th>ID</th>
				<th>创建时间</th>
				<th>创建人id</th>
				<th>更新时间</th>
				<th>更新人id</th>
				<th>用户名</th>
				<th>密码</th>
				<th>登录时间</th>
				<th>登录次数</th>
				<th>超级管理员</th>
				<th>状态</th>
			</tr>
			<c:if test="${!empty adminList }">
				<c:forEach items="${adminList}" var="admin">
					<tr>
						<td>${admin.id }</td>
						<td>${admin.createTime }</td>
						<td>${admin.adminCreatorId }</td>
						<td>${admin.updateTime }</td>
						<td>${admin.adminUpdaterId }</td>
						<td>${admin.userName }</td>
						<td>${admin.password }</td>
						<td>${admin.loginTime }</td>
						<td>${admin.loginCount }</td>
						<td>${admin.superAdmin }</td>
						<td>${admin.status }</td>
						<td>
							<a href="<%=basePath%>s/admin/getAdmin?id=${admin.id}">编辑</a>
							<a href="javascript:del('${admin.id }')">删除</a>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
  </body>
</html>
