//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/user/userDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			str+="<tr>";
			str+="<td>用户名</td><td>";
			str+=data.userName+"</td>";
			str+="<td>昵称</td><td>";
			str+=data.nickName+"</td>";
			str+="</tr>";
			str+="<td>登录密码</td><td>";
			str+="********</td>";
			str+="<td>支付密码</td><td>";
			if(data.payPwd!=""){
				str+="********</td>";
			}else{
				str+="未设置</td>";
			}
			str+="</tr>";
			str+="<tr>";
			str+="<td>手机号</td><td>";
			str+=data.phone+"</td>";
			str+="<td>手机绑定</td><td>";
			str+=data.phoneBind+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>电子邮箱</td><td>";
			str+=data.email+"</td>";
			str+="<td>邮箱绑定</td><td>";
			str+=data.emailBind+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>头像</td><td colspan='3'>";
			str+=data.avatar+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>注册时间</td><td>";
			str+=data.registTime+"</td>";
			str+="<td>注册ip</td><td>";
			str+=data.registIp+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>实名认证</td><td>";
			str+=data.identityStatusName+"</td>";
			str+="<td>企业认证</td><td>";
			str+=data.enterpriseStatusName+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>环信id</td><td>";
			str+=data.easeId+"</td>";
			str+="<td>环信密码</td><td>";
			str+=data.easePwd+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>状态</td><td>";
			str+=data.statusName+"</td>";
			str+="<td></td>"
			str+="</tr>";
			$("#tb_userDetail").html(str);
		}
	});
});