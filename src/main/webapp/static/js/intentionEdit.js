//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	console.log(path);
	$.ajax({
		type : 'get',
		url : path + 's/intention/intentionDetail.do?id='+ paramId,
		dataType : "json",
		success : function(data) {
			$("#username").val(data.realName);
			$('#phone').val(data.phone);
			$("#total").val(data.total);
			$("#available").val(data.available);
			$("#freeze").val(data.freeze);
		}
	});
	
	$("#btnSubmit").click(function() {
		Ewin.confirm({ message: "确认要提交修改的数据吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.id=paramId;
			param.total=$('#total').val();
			param.available=$('#available').val();
			param.freeze=$('#freeze').val();
			$.ajax({
				type : 'POST',
				url : path + 's/intention/intentionEdit.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/intention/intentionListPage';
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
			})
		})
	});
	

})
