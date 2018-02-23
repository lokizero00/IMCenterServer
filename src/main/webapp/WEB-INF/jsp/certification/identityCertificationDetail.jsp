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
<link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
	<script src="<%=basePath%>static/jQuery/jquery-2.1.4.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/identityCertificationDetail.js"></script>
	<jsp:include page="identityCertification.jsp" />
	<div class="form-group">
		<div>
			<button type="button" id="btnVerify" data-toggle='modal'
				data-target='#icVerifyModal' class="btn btn-primary">审核</button>
			<button type="button" id="btnBack" onclick="window.history.go(-1);"
				class="btn btn-primary">返回</button>
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
						<label for="name">请选择审核结果</label> <select
							id="identityCertificationDetail_verifyResult">
							<option value="verify_pass">通过</option>
							<option value="verify_refuse">不通过</option>
						</select>
					</div>
					<div class="form-group" id="div_identityCertificationRefuseReason"
						style="display: none;">
						<label for="name">拒绝原因</label>
						<textarea class="form-control"
							id="ta_identityCertificationRefuseReason" rows="3"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" onclick="identityCertificationVerify();"
						class="btn btn-primary">提交</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>
