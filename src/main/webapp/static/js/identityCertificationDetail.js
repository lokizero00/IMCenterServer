//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$("#identityCertificationDetail_verifyResult").change(function(){
		var currentValue=$(this).children('option:selected').val();
		if(currentValue==='verify_refuse'){
			$("#div_identityCertificationRefuseReason").show();
		}else{
			$("#div_identityCertificationRefuseReason").hide();
		}
	});
});

function identityCertificationVerify(){
	var param = {};
	param.id=paramId;
	param.verifyResult=$("#identityCertificationDetail_verifyResult").val();
	param.refuseReason=$("#ta_identityCertificationRefuseReason").val();
	$.ajax({
		"type" : 'post',
		"url" : path + 's/certification/verifyIdentityCertification.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError===true){
				alert('发生错误：'+data.errorMsg);
			}else{
				alert('执行成功');
				window.location = path+'s/certification/identityCertificationListPage';
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