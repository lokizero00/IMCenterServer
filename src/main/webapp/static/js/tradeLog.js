//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/trade/tradeLog.do?tradeId='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			for(var i=0;i<data.length;i++){
				var log=data[i];
				str+="<tr>";
				str+="<td>"+log.content+"</td>";
				str+="<td>"+log.createTime+"</td>";
				str+="</tr>"
			}
			$("#tb_tradeLog").html(str);
		}
	});
});