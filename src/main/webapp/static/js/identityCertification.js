//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/certification/identityCertificationDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			str+="<tr>";
			str+="<td>真实姓名</td><td>";
			str+=data.trueName+"</td>";
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
			str+="<td>身份证号</td><td>";
			str+=data.identityNumber+"</td>";
			str+="<td>附件</td><td>";
			
			str+="<button type='button' id='btnViewIdentityPic' data-toggle='modal' data-target='#viewIdentityPicModal' class='btn btn-primary'>查看</button>";
			
			str+="</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>状态</td><td>";
			str+=data.statusName+"</td>";
			str+="<td></td><td>";
			str+="</td>";
			str+="</tr>";
			if(data.status!='ic_verify'){
				str+="<tr>";
				str+="<td>审核时间</td><td>";
				str+=data.verifyTime+"</td>";
				str+="<td>审核人</td><td>";
				str+=data.adminVerifierName+"</td>";
				str+="</tr>";
			}
			if(data.status=='ic_refuse'){
				str+="<tr>";
				str+="<td>拒绝原因</td><td colspan='3'>";
				str+=data.refuseReason+"</td>";
				str+="</tr>";
			}
			$("#tb_identityCertificationDetail").html(str);
			
			var pmStr="";
			if(data.identityFrontUrl=="" && data.identityBackUrl==""){
				pmStr+="<span class='label label-danger'>无附件图片</span>";
			}else{
				pmStr+="<div class='carousel-inner'>";
				if(data.identityFrontUrl!=""){
					pmStr+="<div class='item active'>";
					pmStr+="<img src='"+data.identityFrontUrl+"' alt='First slide'>";
					pmStr+="<div class='carousel-caption'>正面</div></div>";
				}
				if(data.identityBackUrl!=""){
					if(data.identityFrontUrl!=""){
						pmStr+="<div class='item'>";
					}else{
						pmStr+="<div class='item active'>";
					}
					pmStr+="<img src='"+data.identityBackUrl+"' alt='Second slide'>";
					pmStr+="<div class='carousel-caption'>反面</div></div></div>";
				}
			}
			
			pmStr+="<a class='carousel-control left' href='#myCarousel' data-slide='prev'>&lsaquo;</a> <a class='carousel-control right' href='#myCarousel' data-slide='next'>&rsaquo;</a>";
			$("#myCarousel").html(pmStr);
			
			//控制审核按钮显示与隐藏
			if($('#btnVerify').length>0){
				if(data.status=='ic_verify'){
					$('#btnVerify').removeAttr("disabled");
				}else{
					$('#btnVerify').attr('disabled',"true");
				}
			}
		}
	});
});