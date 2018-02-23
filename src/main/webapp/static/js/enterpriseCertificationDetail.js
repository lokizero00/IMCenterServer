//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$("#enterpriseCertificationDetail_verifyResult").change(function(){
		var currentValue=$(this).children('option:selected').val();
		if(currentValue==='verify_refuse'){
			$("#div_enterpriseCertificationRefuseReason").show();
		}else{
			$("#div_enterpriseCertificationRefuseReason").hide();
		}
	});
});

function enterpriseCertificationVerify(){
	var param = {};
	param.id=paramId;
	param.verifyResult=$("#enterpriseCertificationDetail_verifyResult").val();
	param.refuseReason=$("#ta_enterpriseCertificationRefuseReason").val();
	$.ajax({
		"type" : 'post',
		"url" : path + 's/certification/verifyEnterpriseCertification.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError===true){
				alert('发生错误：'+data.errorMsg);
			}else{
				alert('执行成功');
				window.location = path+'s/certification/enterpriseCertificationListPage';
			}
		},
		error : function(data) {
			if (data.statusText == 'OK') {
				alert('您没有相关权限');
			} else {
				alert(data.statusText);
			}
		} 
	});
}