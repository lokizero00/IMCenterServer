//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/adv/advDetail.do?id='
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
			str+="<td>状态</td><td>";
			str+=data.state+"</td>";
			str+="<td>点击量</td><td>";
			str+=data.clickCount+"</td>";
			str+="</tr>";
			if(data.previewUrl){
				str+="<tr>";
				str+="<td>缩略图</td><td colspan='3'>";
				str+= "<img src="+path + "s/io/getImage?name="+data.previewUrl+" width='360px' height='145px'></td>";
				str+="</tr>";
			}
			str+="<tr>";
			str+="<td>内容</td><td colspan='3'>";
			str+=data.content+"</td>";
			str+="</tr>";
			
			str+="<tr>";
			str+="<td>链接化：</td><td>";
			str+=data.linkable+"</td>";
			str+="<td>跳转链接</td><td>";
			str+=data.linkUrl+"</td>";
			str+="</tr>";
			$("#tb_advDetail").html(str);
		}
	});
});