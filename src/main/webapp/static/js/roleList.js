//项目根目录
var path = $("#contextPath").val();
//定义存储
var storage = window.localStorage;
$(document).ready(function() {
	$('#table_roleList').bootstrapTable({
		url : path + 's/role/roleList.do', // 请求后台的URL（*）
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
		search : false, // 是否显示表格搜索colspan
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
                var page = $('#table_roleList').bootstrapTable("getPage");  
                return page.pageSize * (page.pageNumber - 1) + index + 1;  
            }  
		}, {
			title : '角色名',
			field : 'name',
			sortable : true,
			sortName : 'name'
		}, {
			title : '描述',
			field : 'description',
			sortable : true,
			sortName : 'description'
		}, {
			title : '创建时间',
			field : 'createTime',
			sortable : true,
			sortName : 'createTime'
		}, {
			title : '更新时间',
			field : 'updateTime',
			sortable : true,
			sortName : 'updateTime'
		}, {
			title : '更新人',
			field : 'adminUpdaterName',
			sortable : true,
			sortName : 'adminUpdaterName'
		}, {
			title : '操作',
			field : 'operate',
			width : '12%',
			formatter : function(value,row,index){
				var operate = ' <a class="authorize" href="'
					+ (path + 's/role/roleAuthorizePage?id=')
					+ row.id
					+ '" title="授权"><button type="button" class="btn btn-primary">授权</button></a>';
				operate += ' <a class="edit" href="'
					+ (path + 's/role/roleEditPage?id=')
					+ row.id
					+ '" title="编辑"><button type="button" class="btn btn-success">编辑</button></a>';
				operate += ' <button type="button" onclick="delRole('+row.id+')" class="btn btn-info">删除</button>';
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
				'name' : $('#name').val()
		}
	}
	
	$("#queryButton").click(function() {
		$('#table_roleList').bootstrapTable('refresh');
	});
	
	$("#btn_add").click(function() {
		window.location.href = path + 's/role/roleAddPage';
	});
	
});

function delRole(id){
	Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
		if(!e){
			return;
		}
		$.ajax({
			type:'POST',
			url:path+"s/role/roleDel.do",
			data:{'id':id},
			dataType:'json',
			success:function(result){
				if(result.isError==false){
					toastr.success('删除数据成功！');
				}else{
					toastr.error('删除数据失败！');
				}
				$('#table_roleList').bootstrapTable('refresh');
			},
			error:function(){
				toastr.error('系统错误！');
			}
		});
		
	});
}