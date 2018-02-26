//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/intention/intentionDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			refreshUserIntentionData(data);
		}
	});
});

function refreshUserIntentionData(data){
	var str="";
	str+="<tr>";
	str+="<td>用户</td><td>";
	str+=data.userNickName+"</td>";
	str+="<td>总金额</td><td>";
	str+=data.total+"</td>";
	str+="</tr>";
	str+="<td>可用额</td><td>";
	str+=data.available+"</td>";
	str+="<td>冻结额</td><td>";
	str+=data.freeze+"</td>";
	str+="</tr>";
	$("#tb_intentionDetail").html(str);
}