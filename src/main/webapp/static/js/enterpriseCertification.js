//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/certification/enterpriseCertificationDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			str+="<tr>";
			str+="<td>用户</td><td>";
			str+=data.userNickName+"</td>";
			str+="<td></td><td>";
			str+="</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>企业名称</td><td>";
			str+=data.enterpriseName+"</td>";
			str+="<td>职位</td><td>";
			str+=data.position+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>创建时间</td><td>";
			str+=data.createTime+"</td>";
			str+="<td>更新时间</td><td>";
			str+=data.updateTime+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>营业执照附件</td><td>";
			str+="<button type='button' id='btnViewEnterprisePic' data-toggle='modal' data-target='#viewEnterprisePicModal' class='btn btn-primary'>查看</button>";
			str+="</td>";
			str+="<td></td><td>";
			str+="</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>状态</td><td>"; 
			str+=data.statusName+"</td>";
			str+="<td>当前关联</td><td>";
			if(data.deleted){
				str+="否";
			}else{
				str+="是";
			}
			str+="</td>";
			str+="</tr>";
			if(data.status!='ec_verify'){
				str+="<tr>";
				str+="<td>审核时间</td><td>";
				str+=data.verifyTime+"</td>";
				str+="<td>审核人</td><td>";
				str+=data.adminVerifierName+"</td>";
				str+="</tr>";
			}
			if(data.status=='ec_refuse'){
				str+="<tr>";
				str+="<td>拒绝原因</td><td colspan='3'>";
				str+=data.refuseReason+"</td>";
				str+="</tr>";
			}
			$("#tb_enterpriseCertificationDetail").html(str);
			
			var pmStr="";
			if(data.licensePicUrl==""){
				pmStr+="<span class='label label-danger'>无附件图片</span>";
			}else{
				pmStr+="<div class='carousel-inner'>";
				if(data.licensePicUrl!=""){
					pmStr+="<div class='item active'>";
					pmStr+="<img src="+path + "s/io/getImage?name="+data.licensePicUrl+" alt='First slide'>";
					pmStr+="<div class='carousel-caption'>正面</div></div>";
				}
			}
			
			pmStr+="<a class='carousel-control left' href='#myCarousel' data-slide='prev'>&lsaquo;</a> <a class='carousel-control right' href='#myCarousel' data-slide='next'>&rsaquo;</a>";
			$("#myCarousel").html(pmStr);
			
			//控制审核按钮显示与隐藏
			if($('#btnVerify').length>0){
				if(data.status=='ec_verify'){
					$('#btnVerify').removeAttr("disabled");
				}else{
					$('#btnVerify').hide();
					$("#btnVerifyRefuse").hide();
				}
			}
		}
	});
});