//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/role/roleList.do',
		"dataType" : "json",
		"success" : function(data) {
			var data_list = data;
			var opts = "<option value='0'>请选择角色</option>";
			$.each(data.rows,function(index,item){
				opts += "<option value='" + item.id + "' >"+item.name+"</option>";
			});
			$("#roleName").append(opts);
		}
	});
	
	
	$.ajax({
		type : 'get',
		url : path + 's/admin/adminDetail.do?id='+ paramId,
		dataType : "json",
		success : function(data) {
			$("#userName").val(data.userName);
			$('#roleName').val(data.roleId);
			$('#status').val(data.status);
			$('#superAdmin').val(data.status)
			if(data.superAdmin){
				$("#superAdmin").attr("checked", "checked"); 
			}
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
			param.id=paramId;
			param.userName=$('#userName').val();
			param.roleId=parseInt($('#roleName').val());
			param.status=$('#status').val();
			param.superAdmin=$('#superAdmin').is(':checked');
			$.ajax({
				type : 'POST',
				url : path + 's/admin/adminEdit.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/admin/adminListPage';
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
	
})