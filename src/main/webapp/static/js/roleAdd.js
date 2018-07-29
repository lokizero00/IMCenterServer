//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$("#btnSubmit").click(function() {
		if(!$('#name').val()){
			toastr.warning('填写内容不正确！');
			return;
		}
		Ewin.confirm({ message: "确认要提交添加的数据吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.name=$('#name').val();
			param.description=$('#description').val();
			$.ajax({
				type : 'POST',
				url : path + 's/role/roleAdd.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/role/roleListPage';
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