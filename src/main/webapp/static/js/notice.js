//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/notice/noticeDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			str+="<tr>";
			str+="<td>标题</td><td colspan='3'>";
			str+=data.title+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>创建时间</td><td>";
			str+=data.createTime+"</td>";
			str+="<td>创建人</td><td>";
			str+=data.adminCreatorName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>更新时间</td><td>";
			str+=data.updateTime+"</td>";
			str+="<td>更新人</td><td>";
			str+=data.adminUpdaterName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>通知类型</td><td>";
			str+=data.relationTypeName+"</td>";
			str+="<td>关联数据</td><td>";
			str+="<a href='#'>跳转查看</a></td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>内容</td><td colspan='3'>";
			str+=data.content+"</td>";
			str+="</tr>";
			$("#tb_noticeDetail").html(str);
		}
	});
});