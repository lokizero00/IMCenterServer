//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$("#btnVerify").click(function(){
		Ewin.confirm({ message: "确认要审核通过吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.id=paramId;
			param.verifyResult="verify_pass";
			identityCertificationVerify(param);
		});
	});
	$("#btnVerifyRefuse").click(function(){
		$("#icVerifyModal").modal('show');
	});
	
	$("#submit").click(function(){
		var refuseReason=$("#ta_identityCertificationRefuseReason").val();
		if(!refuseReason){
			toastr.warning('填写内容不正确！');
			return;
		}
		var param = {};
		param.id=paramId;
		param.verifyResult="verify_refuse";
		param.refuseReason=refuseReason;
		identityCertificationVerify(param);
	});
});

function identityCertificationVerify(param){
	$.ajax({
		"type" : 'post',
		"url" : path + 's/certification/verifyIdentityCertification.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError===true){
				toastr.error('发生错误：'+data.errorMsg);
			}else{
				window.location = path+'s/certification/identityCertificationListPage';
			}
		},
		error : function(data) {
			if (data.statusText == 'OK') {
				toastr.error('您没有相关权限');
			} else {
				toastr.error(data.statusText);
			}
		} 
	});
}