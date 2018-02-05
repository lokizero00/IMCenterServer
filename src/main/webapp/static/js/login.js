//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$("#btn_login").click(function(){
		var param={};
		param.userName=$("#userName").val();
		param.password=$("#password").val();
		$("#btn_login").text("登录中...");
		$('#btn_login').attr('disabled',"true");
		$.ajax({
			"type" : 'post',
			"url" : path + 'login/adminLogin',
			"dataType" : "json",
			"data":param,
			"success" : function(data) {
				if(data.isError===true){
					alert('系统提示：'+data.errorMsg);
					$("#btn_login").text("登录");
					$('#btn_login').removeAttr("disabled");
				}else{
					window.top.location = path+'main.jsp';
				}
			},
			error:function(data){  
		        alert(data.statusText); 
		        $("#btn_login").text("登录");
		        $('#btn_login').removeAttr("disabled");
		    }  
		});
	});
});
