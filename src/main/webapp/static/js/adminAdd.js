//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/admin/adminList.do',
		"dataType" : "json",
		"success" : function(data) {
			var data_list = data;
			var opts = "<option value=''>请选择角色</option>";
			$(data).each(function(index,value){
				opts += "<option value='" + data.id + "' >"+data.name+"</option>";
			});
//			for (var data_index = 0; data_index < data_list.length; data_index++) {
//				var data = data_list[data_index];
//				opts += "<option value='" + data.name + "'";
//				if (getFromStorage(elementName) == data.name) {
//					opts += " selected='selected'>";
//				} else {
//					opts += " >";
//				}
//				opts += data.value + "</option>";
//			}
			// 查询界面
			$("adminName").append(opts);
		}
	});
	
	
	$("#btnSubmit").click(function() {
		if(!$('#userName').val() && !$('#password').val() && !$('#repwd').val()){
			toastr.warning('填写内容不正确！');
			return;
		}
		if($('#repwd').val() != $('#password').val()){
			toastr.warning('两次密码填写不一致！');
			return;
		}
		Ewin.confirm({ message: "确认要提交添加的数据吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.userName=$('#userName').val();
			param.password=$('#password').val();
			param.roleId=$('#roleName').val();
			param.status=$('#status').val();
			param.superAdmin=$('#superAdmin:checked');
			$.ajax({
				type : 'POST',
				url : path + 's/admin/adminAdd.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/admin/adminListPage';
					}else{
						toastr.error('添加失败！');
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

})