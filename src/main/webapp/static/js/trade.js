//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/trade/tradeDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			str+="<tr>";
			str+="<td>贸易编号</td><td>";
			str+=data.sn+"</td>";
			str+="<td>用户</td><td>";
			str+=data.userNickName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>创建时间</td><td>";
			str+=data.createTime+"</td>";
			str+="<td>更新时间</td><td>";
			str+=data.updateTime+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>标题</td><td colspan='3'>";
			str+=data.title+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>类型</td><td>";
			str+=data.typeName+"</td>";
			str+="<td>地区</td><td>";
			str+=data.provinceName+"-"+data.cityName+"-"+data.townName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>内容</td><td colspan='3'>";
			str+=data.content+"</td>";
			str+="</tr>";
			if(data.type==='trade_supply'){
				str+="<tr>";
				str+="<td>供应资源</td><td>";
				str+=data.resourceName+"</td>";
				str+="<td>产能</td><td>";
				str+=data.capacity+"</td>";
				str+="</tr>";
			}
			if(data.type==='trade_demand'){
				str+="<tr>";
				str+="<td>需求数</td><td>";
				str+=data.quantity+"</td>";
				str+="<td>交付时间</td><td>";
				str+=data.deliveryTime+"</td>";
				str+="</tr>";
				str+="<tr>";
				str+="<td>预算</td><td>";
				str+=data.budget+"</td>";
				str+="<td></td><td>";
				str+="</td>";
				str+="</tr>";
			}
			str+="<tr>";
			str+="<td>意向金</td><td>";
			str+=data.intention+"</td>";
			str+="<td>状态</td><td>";
			str+=data.statusName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>所属行业</td><td>";
			str+=data.industryName+"</td>";
			str+="<td>发票要求</td><td>";
			str+=data.invoiceName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>支付方式</td><td>";
			str+=data.paycodeName+"</td>";
			str+="<td></td><td>";
			str+="</td>";
			str+="</tr>";
			$("#tb_tradeDetail").html(str);
		}
	});
});