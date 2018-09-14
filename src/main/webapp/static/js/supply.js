$(document).ready(function(){
	function getUrlVal(key) {
		// 获取参数 
		var url = window.location.search; 
		// 正则筛选地址栏 
		var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)"); 
		// 匹配目标参数 
		var result = url.substr(1).match(reg); 
		//返回参数值 
		return result ? decodeURIComponent(result[2]) : null; 
	}
	var tradeId = getUrlVal("id");
	if(tradeId!=""){
		$.ajax({
			type : 'get',
			url :"http://localhost:8080/IMCenterServer/api/common/showTrade?tradeId="+ tradeId,
			dataType : "json",
			jsonp:"callback", 
			success : function(data) {
				if(data.isError==false){
					var obj=data.resultObj;
					$('#title').html(obj.title);
					$('#sn').html(obj.sn);
					$('#intention').html(obj.intention+"元");
					$('#content').html(obj.content);
					$('#industryName').html(obj.industryName);
					$('#address').html(obj.provinceName+obj.cityName+obj.townName);
					$('#resourceName').html(obj.resourceName);
					$('#capacity').html(obj.capacity);
					$('#paycodeName').html(obj.paycodeName);
					$('#invoiceName').html(obj.invoiceName);
					var str="";
					$.each(obj.tradeAttachmentList,function(index,value){
						str+=" <li><img src='https://www.bestimade.com/s/io/getImage?name="+value.name+"'></li>";
					});
					$('#tradeImg').html(str);
					var scrollImg = $.mggScrollImg('.imgbox ul',{
				        loop : true,//循环切换
				        auto : true,//自动切换
				        auto_wait_time:3000,//轮播间隔
				        scroll_time:300,//滚动时长
				        callback : function(ind){//这里传过来的是索引值
//				            $('#page').text(ind+1);
				        }
				    });
					
					$('#quantity').html(obj.quantity);
					$('#deliveryTime').html(obj.deliveryTime);
					$('#budget').html(obj.budget);
					
					$.ajax({
						type : 'get',
						url :"http://localhost:8080/IMCenterServer/api/common/getPersonalData?userId="+obj.userId,
						dataType : "json",
						success : function(data) {
							if(data.isError==false){
								var user=data.resultObj;
								$('#enterpriseName').html(user.enterpriseName);
								$('#trueName').html(user.trueName);
								$('#position').html(user.position);
								$('#phone').html(user.phone);
							}
						}
					});
				}
			}
		});
	}
	$('.btn').click(function(){
        window.location.href = 'appDownload.html';
	});
	
 });
 
			 