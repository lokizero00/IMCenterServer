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
<body>
	<input type="hidden" id="contextPath" value="${contextPath }" />
	<table class="tb_detail">
		<tbody id="tb_enterpriseCertificationDetail">
		</tbody>
	</table>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/enterpriseCertification.js"></script>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="viewEnterprisePicModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">查看附件</h4>
				</div>
				<div class="modal-body">
					<div id="myCarousel" class="carousel slide">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>
