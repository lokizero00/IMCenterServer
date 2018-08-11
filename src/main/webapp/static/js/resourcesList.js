//项目根目录
var path = $("#contextPath").val();
//定义存储
var storage = window.localStorage;
$(document).ready(function() {
	$('#table_resourcesList').bootstrapTable({
        class: 'table table-hover table-bordered',
        url:path + 's/resources/resourcesList.do',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        sidePagination: 'server',
        pagination: false,
        treeView: true,
        treeId: "id",
        treeField: "name",
        columns : [ 
    		{
    			title : '资源名称',
    			field : 'name',
    		}, {
    			title : '模块',
    			field : 'model',
    		}, {
    			title : '路径',
    			field : 'url',
    		}, {
    			title : '类型',
    			field : 'type',
    		}, {
    			title : '状态',
    			field : 'satus',
    			formatter : function(value, row, index) {  
                   if(row.status=="on"){
                	   return "启用";
                   }
                   return "禁用";
                }  
    		},{
    			title : '描述',
    			field : 'description',
    		}, {
    			title : '创建时间',
    			field : 'createTime',
    			sortable : true,
    			sortName : 'createTime'
    		}, {
    			title : '创建人',
    			field : 'adminCreatorName',
    		}, {
    			title : '更新时间',
    			field : 'updateTime',
    			sortable : true,
    			sortName : 'updateTime'
    		}, {
    			title : '更新人',
    			field : 'adminUpdaterName',
    		}, {
    			title : '操作',
    			field : 'pic',
    			width : '12%',
    			formatter : function(value,row,index){
    				var operate = ' <a class="edit" href="'
    						+ (path + 's/resources/resourcesEditPage?id=')
    						+ row.id
    						+ '" title="编辑"><button type="button" class="btn btn-success">编辑</button></a>';
    				operate += ' <button type="button" onclick="delResources('+row.id+')" class="btn btn-info">删除</button>';
    				return operate;
    			}
    		} ]
    });
	$("#expandAllTree").on('click',function(){
        $('#table_resourcesList').bootstrapTable("expandAllTree")
    });
    $("#collapseAllTree").on('click',function(){
        $('#table_resourcesList').bootstrapTable("collapseAllTree")
    });
	$("#btn_add").click(function() {
		window.location.href = path + 's/resources/resourcesAddPage';
	});
});

function delResources(id){
	Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
		if(!e){
			return;
		}
		$.ajax({
			type:'POST',
			url:path+"s/resources/resourcesDel.do",
			data:{'id':id},
			dataType:'json',
			success:function(result){
				if(result.isError==false){
					toastr.success('删除数据成功！');
				}else{
					toastr.error('删除数据失败！');
				}
				$('#table_resourcesList').bootstrapTable('refresh');
			},
			error:function(){
				toastr.error('系统错误！');
			}
		});
		
	});
}