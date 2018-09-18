//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/session/getObject.do?sessionKey=clientAdmin',
		"dataType" : "json",
		"success" : function(data) {
			$("#userName").val(data.userName);
			$("#id").val(data.id);
		}
	});
	
	$("#btnSubmit").click(function() {
		if(!$('#userName').val()){
			toastr.warning('填写内容不正确！');
			return;
		}
		Ewin.confirm({ message: "确认要提交修改的数据吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.id=$("#id").val();
			param.password=$('#password').val();
			$.ajax({
				type : 'POST',
				url : path + 's/admin/adminChangePassword.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.history.go(-1);
					}else{
						toastr.error('修改失败！');
					}
				},error:function(data){
					if (data.statusText == 'OK') {
						toastr.error('您没有相关权限');
					} else {
						toastr.error(data.statusText);
					}
				}
			});
		});
	});
});