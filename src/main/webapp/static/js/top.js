//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/session/getObject.do?sessionKey=clientAdmin',
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			str+="<li><a href='#'> <span class='glyphicon glyphicon-user'></span> "+data.userName+"</a></li>";
			str+="<li><a href='#'> <span class='glyphicon glyphicon-fire'></span> "+data.roleName+"</a></li>";
			str+="<li><a target='_parent' href='"+path+"login/adminLoginOut'> <span class='glyphicon glyphicon-log-out'></span> 注销</a></li>";
			$("#ul_adminInfo").html(str);
		}
	});
});