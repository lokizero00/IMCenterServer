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
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>实名认证详情</title>
<link href="images/favicon.png" rel="icon" />
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/style.css" rel="stylesheet">
<link href="<%=basePath%>static/css/toastr.min.css" rel="stylesheet" />
</head>

<body>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/toastr.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/ewin.js"></script>
	<script type="text/javascript">
         toastr.options.positionClass = 'toast-center-center';
 	</script>
	<script src="<%=basePath%>static/js/identityCertificationDetail.js"></script>
	
	<div class="panel panel-default">
	    <div class="panel-heading">
	       <h3 class="panel-title"><span class="glyphicon glyphicon-book"></span> 实名认证管理</h3>
	    </div>
	    <div class="panel-body">
	    	<jsp:include page="identityCertification.jsp" />
	    </div>
	    <div class="form-group">
			<div align="center">
				<button type="button" id="btnVerify" class="btn btn-success">审核通过</button>
				<button type="button" id="btnVerifyRefuse" class="btn btn-warning">审核不通过</button>
				<button type="button" id="btnBack" onclick="window.history.go(-1);" class="btn btn-primary">返回</button>
			</div>
		</div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="icVerifyModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">实名认证审核</h4>
				</div>
				<div class="modal-body">
					<div>
						<label for="name">请选择审核结果 : 不通过</label> 
					</div>
					<div class="form-group">
						<label for="name">拒绝原因</label>
						<textarea class="form-control" id="ta_identityCertificationRefuseReason" rows="3"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="submit" class="btn btn-primary">提交</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>
