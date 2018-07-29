//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	
	$.ajax({
		type : 'get',
		url : path + 's/role/roleDetail.do?id='+ paramId,
		dataType : "json",
		success : function(data) {
			console.log(data);
			$("#name").val(data.name);
			$('#description').val(data.description);
		}
	});
	
	$("#btnSubmit").click(function() {
		console.log(!$('#name').val());
		if(!$('#name').val()){
			toastr.warning('填写内容不正确！');
			return;
		}
		Ewin.confirm({ message: "确认要提交修改的数据吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.id=paramId;
			param.name=$('#name').val();
			param.description=$('#description').val();
			$.ajax({
				type : 'POST',
				url : path + 's/role/roleEdit.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/role/roleListPage';
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