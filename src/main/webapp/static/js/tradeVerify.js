//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/dictionaries/dictionariesList.do?type=verify_operate',
		"dataType" : "json",
		"success" : function(data) {
			var data_list = data;
			var opts = "<option value=''>请选择</option>";
			for (var data_index = 0; data_index < data_list.length; data_index++) {
				var data = data_list[data_index];
				opts += "<option value='" + data.code + "'>"+data.value+"</option>";
			}
			// 查询界面
			$("#select_verifyResult").append(opts);
		}
	});
	
	$("#select_verifyResult").change(function(){
		var currentValue=$(this).children('option:selected').val();
		if(currentValue==='verify_refuse'){
			$("#div_tradeRefuseReason").show();
		}else{
			$("#div_tradeRefuseReason").hide();
		}
	});
	
	$("#btnVerify").click(function(){
		var r=confirm("确定要执行？");
		if(r===true){
			var verifyResult=$("#select_verifyResult").val();
			if(verifyResult!=''){
				var param={};
				param.tradeId=paramId;
				param.verifyResult=verifyResult;
				param.refuseReason=$("#ta_tradeRefuseReason").val();
				$.ajax({
					"type" : 'post',
					"url" : path + 's/trade/tradeVerify.do',
					"dataType":"json",
					"data":param,
					"success" : function(data) {
						if(data.isError===true){
							alert('发生错误：'+data.errorMsg);
						}else{
							alert('执行成功');
							window.location = path+'s/trade/';
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
			}else{
				alert("请选择一个审核结果");
			}
		}
	});
	
	$("#btnBack").click(function(){
		window.history.go(-1);
	});
});