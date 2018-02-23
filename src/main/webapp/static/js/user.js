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
			refreshUserData(data);
		}
	});
});

function rebindPhone(){
	var param = {};
	param.id=paramId;
	param.newPhone=$("#rpm_newPhone").val();
	$.ajax({
		"type" : 'post',
		"url" : path + 's/user/rebindPhone.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError){
				alert("修改失败，原因："+data.errorMsg);
			}else{
				alert("修改成功");
			}
			refreshUserData(data);
			$('#rebindPhoneModal').modal('hide');
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

function changePassword(){
	var param = {};
	param.id=paramId;
	param.newPassword=$("#cpm_newPassword").val();
	$.ajax({
		"type" : 'post',
		"url" : path + 's/user/changePassword.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError){
				alert("修改失败，原因："+data.errorMsg);
			}else{
				alert("修改成功");
			}
			$('#changePasswordModal').modal('hide');
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

function changePayPwd(){
	var param = {};
	param.id=paramId;
	param.newPayPwd=$("#cppm_newPayPwd").val();
	$.ajax({
		"type" : 'post',
		"url" : path + 's/user/changePayPwd.do',
		"data" : param,
		"dataType" : "json",
		"success" : function(data) {
			if(data.isError){
				alert("修改失败，原因："+data.errorMsg);
			}else{
				alert("修改成功");
			}
			$('#changePayPwdModal').modal('hide');
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

function clearModalInput(){
	$("#cpm_newPassword").val('');
	$("#rpm_newPhone").val('');
	$("#cppm_newPayPwd").val('');
}

function refreshUserData(data){
	var str="";
	str+="<tr>";
	str+="<td>用户名</td><td>";
	str+=data.userName+"</td>";
	str+="<td>昵称</td><td>";
	str+=data.nickName+"</td>";
	str+="</tr>";
	str+="<td>登录密码</td><td>";
	str+="******** <button type='button' id='btnChangePassword' data-toggle='modal' data-target='#changePasswordModal' onclick='clearModalInput();' class='btn btn-primary'>修改</button></td>";
	str+="<td>支付密码</td><td>";
	if(data.payPwd!=""){
		str+="******** <button type='button' id='btnChangepPayPwd' data-toggle='modal' data-target='#changePayPwdModal' onclick='clearModalInput();' class='btn btn-primary'>修改</button></td>";
	}else{
		str+="未设置</td>";
	}
	str+="</tr>";
	str+="<tr>";
	str+="<td>手机号</td><td>";
	str+=data.phone+" <button type='button' id='btnRebindPhone' data-toggle='modal' data-target='#rebindPhoneModal' onclick='clearModalInput();' class='btn btn-primary'>换绑</button></td>";
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
	if(data.avatarUrl!=""){
		str+="<img id='userAvatar' src='"+data.avatarUrl+"' class='img-circle'/></td>";
	}else{
		str+="未设置</td>";
	}
	str+="</tr>";
	str+="<tr>";
	str+="<td>注册时间</td><td>";
	str+=data.registTime+"</td>";
	str+="<td>注册ip</td><td>";
	str+=data.registIp+"</td>";
	str+="</tr>";
	str+="<tr>";
	str+="<td>实名认证</td><td>";
	var identity_href="#";
	if(data.identityId>0){
		identity_href=path + "s/certification/identityCertificationDetailPage?id="+data.identityId;
	}
	str+="<a class='view' href='"+identity_href+"' title='点击查看'><span class='glyphicon glyphicon-info-sign'></span>"+data.identityStatusName+"</a>";
	str+="</td>";
	str+="<td>企业认证</td><td>";
	var enterprise_href="#";
	if(data.enterpriseId>0){
		enterprise_href=path + "s/certification/enterpriseCertificationDetailPage?id="+data.enterpriseId;
	}
	str+="<a class='view' href='"+enterprise_href+"' title='点击查看'><span class='glyphicon glyphicon-info-sign'></span>"+data.enterpriseStatusName+"</a>";
	str+="</td>";
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
	
	if(data.status=='us_on'){
		$('#btnVerify').attr('disabled',"true");
		$("#btnStatus").html('停用');
		$('#btnStatus').removeAttr("disabled");
	}else if(data.status=='us_off'){
		$('#btnVerify').attr('disabled',"true");
		$("#btnStatus").html('启用');
		$('#btnStatus').removeAttr("disabled");
	}else{
		$('#btnVerify').removeAttr("disabled");
		$("#btnStatus").html('???');
		$('#btnStatus').attr('disabled',"true");
	}
}