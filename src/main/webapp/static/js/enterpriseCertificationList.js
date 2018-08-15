//项目根目录
var path = $("#contextPath").val();
// 定义存储
var storage = window.localStorage;
$(document).ready(function() {
	// 表格 - 操作 - 事件
	window.operateEvents = {
		'click .delete' : function(e, value, row, index) {
			// 删除操作
		}
	};

	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 填充文本框
	showInput('#enterpriseCertificationList_queryCreateTimeStart');
	showInput('#enterpriseCertificationList_queryCreateTimeEnd');
	
	//填充下拉框
	showSel("#enterpriseCertificationList_queryStatus", "enterprise_certification_status");

	$("#queryButton").click(function() {
		saveSearchParam();
		refreshTable('#table_enterpriseCertificationList');
	});
	$("#resetButton").click(function() {
		clearSearchParam();
		refreshTable('#table_enterpriseCertificationList');
	});

	$('.form_datetime').datetimepicker({
		// language: 'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1
	});
	$('.form_date').datetimepicker({
		language : 'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
	$('.form_time').datetimepicker({
		language : 'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 1,
		minView : 0,
		maxView : 1,
		forceParse : 0
	});
});

function saveSearchParam() {
	saveInStorage("#enterpriseCertificationList_queryStatus", $("#enterpriseCertificationList_queryStatus").val());
	saveInStorage("#enterpriseCertificationList_queryCreateTimeStart", $(
			"#enterpriseCertificationList_queryCreateTimeStart").val());
	saveInStorage("#enterpriseCertificationList_queryCreateTimeEnd", $(
			"#enterpriseCertificationList_queryCreateTimeEnd").val());
}
function clearSearchParam() {
	storage.clear();
	$("#enterpriseCertificationList_queryStatus").val('');
	$("#enterpriseCertificationList_queryCreateTimeStart").val('');
	$("#enterpriseCertificationList_queryCreateTimeEnd").val('');
}

function getFromStorage(elementName) {
	return storage.getItem(elementName);
}

function saveInStorage(elementName, elementValue) {
	return storage.setItem(elementName, elementValue);
}

// 配置下拉框
function showSel(elementName, elementType) {
	$
			.ajax({
				"type" : 'get',
				"url" : path + 's/dictionaries/dictionariesList.do?type='
						+ elementType,
				"dataType" : "json",
				"success" : function(data) {
					var data_list = data;
					var opts = "<option value=''>全部</option>";
					for (var data_index = 0; data_index < data_list.length; data_index++) {
						var data = data_list[data_index];
						opts += "<option value='" + data.code + "'";
						if (getFromStorage(elementName) == data.code) {
							opts += " selected='selected'>";
						} else {
							opts += " >";
						}
						opts += data.value + "</option>";
					}
					// 查询界面
					$(elementName).append(opts);
				}
			});
}

// 配置文本框
function showInput(elementName) {
	$(elementName).val(getFromStorage(elementName));
}

var TableInit = function() {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function() {
		$('#table_enterpriseCertificationList').bootstrapTable({
			url : path + 's/certification/enterpriseCertificationList.do', // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : true, // 是否启用排序
			sortName : "id",
			sortOrder : "desc", // 排序方式
			queryParams : oTableInit.queryParams,// 传递参数（*）
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
			columns : [ {
				checkbox : true
			}, {
				title : '序号',// 标题
				formatter : function(value, row, index) {  
	                //return index + 1;  
	                var page = $('#table_enterpriseCertificationList').bootstrapTable("getPage");  
	                return page.pageSize * (page.pageNumber - 1) + index + 1;  
	            }  
			}, {
				title : '用户',
				field : 'userNickName',
				sortable : false
			}, {
				title : '创建时间',
				field : 'createTime',
				sortable : true,
				sortName : 'create_time',
				formatter : operateDateFormatter
			}, {
				title : '企业名称',
				field : 'enterpriseName',
				sortable : true,
				sortName : 'enterprise_name'
			}, {
				title : '职位',
				field : 'position',
				sortable : true,
				sortName : 'position'
			}, {
				title : '状态',
				field : 'statusName',
				sortable : true,
				sortName : 'status',
				formatter:function(value, row, index){
					if(row.statusName=="待认证"){
						return "<span style='color:red'>"+value+"</span>";
					}
					return value;
				}
			}, {
				title : '审核时间',
				field : 'verifyTime',
				sortable : true,
				sortName : 'verify_time',
				formatter : operateDateFormatter
			}, {
				title : '审核人',
				field : 'adminVerifierName',
				sortable : true,
				sortName : 'admin_verifier_id'
			}, {
				title : '当前关联',
				field : 'deleted',
				sortable : true,
				sortName : 'deleted',
				formatter : function(value, row, index) { 
					return value? '否':'是';
	            }  
			}, {
				title : '操作',
				field : 'operate',
				width : '5%',
				events : operateEvents,
				formatter : operateFormatter
			} ]
		});
	};

	// 得到查询的参数
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			pageSize : params.pageSize, // 页面大小
			pageNo : params.pageNumber, // 页码
			sortName : params.sortName, // 排序列名
			sortOrder : params.sortOrder, // 排序方式
			status : getFromStorage('#enterpriseCertificationList_queryStatus'),
			createTimeStart_str : getFromStorage('#enterpriseCertificationList_queryCreateTimeStart'),
			createTimeEnd_str : getFromStorage('#enterpriseCertificationList_queryCreateTimeEnd')
		};
		return temp;
	};
	return oTableInit;

	// value: 所在collumn的当前显示值，
	// row:整个行的数据 ，对象化，可通过.获取
	// 表格-操作 - 格式化
	function operateFormatter(value, row, index) {
		var operate = '<a class="view" href="'
				+ (path + 's/certification/enterpriseCertificationDetailPage?id=')
				+ row.id
				+ '" title="查看"><button type="button" class="btn btn-primary">查看</button></a>';
		return operate;
	}
};