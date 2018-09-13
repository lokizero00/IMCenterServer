//项目根目录
var path = $("#contextPath").val();
//定义存储
var storage = window.localStorage;
$(document).ready(function() {
	$('#table_intentionRefundList').bootstrapTable({
		url : path + 's/intentionRefund/intentionRefundList.do', // 请求后台的URL（*）
		method : 'get', // 请求方式（*）
		toolbar : '#toolbar', // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : true, // 是否显示分页（*）
		sortable : true, // 是否启用排序
		sortName : "id",
		sortOrder : "desc", // 排序方式
		queryParams : queryParams,// 传递参数（*）
		queryParamsType : '*',
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 15, 20, 50 ], // 可供选择的每页的行数（*）
		search : false, // 是否显示表格搜索
		strictSearch : false,
		showColumns : true, // 是否显示所有的列
		showRefresh : true, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		// height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId : "id", // 每一行的唯一标识，一般为主键列
		showToggle : true, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false, // 是否显示父子表
		contentType : "application/x-www-form-urlencoded", // 解决POST提交问题
		columns : [ 
		{
			checkbox : true
		}, 
		{
			title : '序号',// 标题
			formatter : function(value, row, index) {  
                //return index + 1;  
                var page = $('#table_intentionRefundList').bootstrapTable("getPage");  
                return page.pageSize * (page.pageNumber - 1) + index + 1;  
            }  
		}, {
			title : '用户',
			field : 'userName',
		}, {
			title : '用户电话',
			field : 'phone',
		}, {
			title : '申请时间',
			field : 'requestTime',
			sortable : true,
			sortName : 'requestTime',
		}, {
			title : '提现项目',
			field : 'refundItem',
			formatter : function(value,row,index){
				if(row.refundItem==0){
					return "意向金";
				}
			}
		}, {
			title : '金额',
			field : 'amount',
			sortable : true,
			sortName : 'amount'
		}, {
			title : '支付方式',
			field : 'refundChannel',
			formatter : function(value,row,index){
				if(row.refundChannel==0){
					return "微信";
				}else if(row.refundChannel==1){
					return "支付宝";
				}
			}
		}, {
			title : '提现方式',
			field : 'refundChannel',
			formatter : function(value,row,index){
				if(row.refundType==0){
					return "普通提现";
				}else if(row.refundType==1){
					return "转账提现";
				}
			}
		}, {
			title : '提现时间',
			field : 'finishTime',
			sortable : true,
			sortName : 'finishTime',
		}, {
			title : '退款单号',
			field : 'outRequestNo',
		}, {
			title : '备注',
			field : 'errorMsg',
			formatter : function(value,row,index){
				if(row.errorMsg.length>12){
					
					return '<p class="tooltip-options" ><a data-toggle="tooltip" title='+row.errorMsg+'>查看</a></p>';
				}else{
					return row.errorMsg;
				}
			}
		},{
			title : '提现状态',
			width : '12%',
			formatter : function(value,row,index){
				var operate='';
				if(row.state==0){
					operate += ' <button type="button" onclick="passIntention('+row.id+')" class="btn btn-success">申请通过</button>';
					operate += ' <button type="button" onclick="notPassIntention('+row.id+')" class="btn btn-danger">不通过</button>';
				}else if(row.state==1){
					operate="提现成功";
				}else if(row.state==2){
					operate="提现失败";
				}
				return operate;
			}
		} ],
		 onLoadSuccess: function(){
			 $(".tooltip-options a").tooltip({html : true });
		 }
		
	});
	
	//查询参数
	function queryParams(params){
		return { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				pageSize : params.pageSize, // 页面大小
				pageNo : params.pageNumber, // 页码
				sortName : params.sortName, // 排序列名
				sortOrder : params.sortOrder, // 排序方式
				'phone' : $('#phone').val()
		}
	}
	
	$("#queryButton").click(function() {
		$('#table_intentionRefundList').bootstrapTable('refresh');
	});
	
});
//申请通过
function passIntention(id){
	Ewin.confirm({ message: "确认要申请通过吗？" }).on(function (e) {
		if(!e){
			return;
		}
		$.ajax({
			type:'POST',
			url:path+"s/intentionRefund/passIntentionCash.do",
			data:{'intentionRefundId':id},
			dataType:'json',
			success:function(result){
				if(result.isError==false){
					toastr.success('提交成功！');
				}else{
					toastr.error('提交失败！');
				}
				$('#table_intentionRefundList').bootstrapTable('refresh');
			},
			error:function(){
				toastr.error('系统错误！');
			}
		});
		
	});
}
//不通过
function notPassIntention(id){
	Ewin.confirm({ message: "确认要不通过吗？" }).on(function (e) {
		if(!e){
			return;
		}
		$.ajax({
			type:'POST',
			url:path+"s/intentionRefund/notPassIntentionCash.do",
			data:{'intentionRefundId':id},
			dataType:'json',
			success:function(result){
				if(result.isError==false){
					toastr.success('提交成功！');
				}else{
					toastr.error('提交失败！');
				}
				$('#table_intentionRefundList').bootstrapTable('refresh');
			},
			error:function(){
				toastr.error('系统错误！');
			}
		});
		
	});
}