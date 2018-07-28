//项目根目录
var path = $("#contextPath").val();
//定义存储
var storage = window.localStorage;
$(document).ready(function() {
	$('#table_intentionList').bootstrapTable({
		url : path + 's/intention/intentionList.do', // 请求后台的URL（*）
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
                var page = $('#table_intentionList').bootstrapTable("getPage");  
                return page.pageSize * (page.pageNumber - 1) + index + 1;  
            }  
		}, {
			title : '用户',
			field : 'realName',
			sortable : true,
			sortName : 'realName'
		}, {
			title : '用户电话',
			field : 'phone',
			sortable : true,
			sortName : 'phone'
		}, {
			title : '总金额',
			field : 'total',
			sortable : true,
			sortName : 'total',
		}, {
			title : '可用额',
			field : 'available',
			sortable : true,
			sortName : 'available'
		}, {
			title : '冻结额',
			field : 'freeze',
			sortable : false
		},{
			title : '操作',
			field : 'operate',
			width : '12%',
			formatter : function(value,row,index){
				var operate = '<a class="view" href="'
					+ (path + 's/intention/intentionDetailPage?id=')
					+ row.id
					+ '" title="查看"><button type="button" class="btn btn-primary">查看</button></a>';
				operate += ' <a class="edit" href="'
						+ (path + 's/intention/intentionEditPage?id=')
						+ row.id
						+ '" title="编辑"><button type="button" class="btn btn-success">编辑</button></a>';
				return operate;
			}
		} ],
		
		
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
		$('#table_intentionList').bootstrapTable('refresh');
	});
	
});