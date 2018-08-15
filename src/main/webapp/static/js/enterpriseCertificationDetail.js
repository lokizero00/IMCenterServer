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
			enterpriseCertificationVerify(param);
		});
	});
	$("#btnVerifyRefuse").click(function(){
		$("#ecVerifyModal").modal('show');
	});
	
	$("#submit").click(function(){
		var refuseReason=$("#ta_enterpriseCertificationRefuseReason").val();
		if(!refuseReason){
			toastr.warning('请填写拒绝原因！');
			return;
		}
		var param = {};
		param.id=paramId;
		param.verifyResult="verify_refuse";
		param.refuseReason=refuseReason;
		enterpriseCertificationVerify(param);
	});
});

function enterpriseCertificationVerify(param){
	$.ajax({
		"type" : 'post',
		"url" : path + 's/certification/verifyEnterpriseCertification.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError===true){
				toastr.error('发生错误：'+data.errorMsg);
			}else{
				window.location = path+'s/certification/enterpriseCertificationListPage';
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