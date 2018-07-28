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
	showInput('#tradeList_queryTitle');
	showInput('#tradeList_querySn');
	// 填充界面上的下拉框
	showSel("#tradeList_queryStatus", "trade_status");
	showSel("#tradeList_queryType", "trade_type");
	showSel("#tradeList_queryIndustryCode", "industry");
	showSel("#tradeList_queryInvoiceCode", "invoice");
	showSel("#tradeList_queryPayCode", "paycode");

	// 初始化页面上面的按钮事件
	$("#btn_add").click(function() {

	});
	$("#queryButton").click(function() {
		saveSearchParam();
		var $table = $('#table_tradeList');
		$table.bootstrapTable('selectPage', 1);
	});
	$("#resetButton").click(function() {
		clearSearchParam();
		var $table = $('#table_tradeList');
		$table.bootstrapTable('selectPage', 1);
	});
});

function saveSearchParam() {
	saveInStorage("#tradeList_queryTitle", $("#tradeList_queryTitle").val());
	saveInStorage("#tradeList_queryType", $("#tradeList_queryType").val());
	saveInStorage("#tradeList_querySn", $("#tradeList_querySn").val());
	saveInStorage("#tradeList_queryStatus", $("#tradeList_queryStatus").val());
	saveInStorage("#tradeList_queryIndustryCode", $(
			"#tradeList_queryIndustryCode").val());
	saveInStorage("#tradeList_queryInvoiceCode", $(
			"#tradeList_queryInvoiceCode").val());
	saveInStorage("#tradeList_queryPayCode", $("#tradeList_queryPayCode").val());
}
function clearSearchParam() {
	storage.clear();
	$("#tradeList_queryTitle").val('');
	$("#tradeList_queryType").val('');
	$("#tradeList_querySn").val('');
	$("#tradeList_queryStatus").val('');
	$("#tradeList_queryIndustryCode").val('');
	$("#tradeList_queryInvoiceCode").val('');
	$("#tradeList_queryPayCode").val('');
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
		$('#table_tradeList').bootstrapTable({
			url : path + 's/trade/tradeList.do', // 请求后台的URL（*）
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
	                var page = $('#table_tradeList').bootstrapTable("getPage");  
	                return page.pageSize * (page.pageNumber - 1) + index + 1;  
	            } 
			}, {
				title : '贸易编号',
				field : 'sn',
				sortable : true,
				sortName : 'sn'
			}, {
				title : '创建时间',
				field : 'createTime',
				sortable : true,
				sortName : 'create_time',
				formatter:operateDateFormatter
			}, {
				title : '更新时间',
				field : 'updateTime',
				sortable : true,
				sortName : 'update_time',
				formatter:operateDateFormatter
			}, {
				title : '用户',
				field : 'userNickName',
				sortable : false,
				formatter : function(v, r, i) {
					if (v) {
						return v.substring(0, 3) + "****" + v.substring(7, 4);
					}
					return v;
				}
			}, {
				title : '标题',
				field : 'title',
				sortable : true,
				sortName : 'title'
			}, {
				title : '类型',
				field : 'typeName',
				sortable : true,
				sortName : 'type'
			}, {
				title : '地区',
				field : 'townName',
				sortable : true,
				sortName : 'town_name',
				formatter:operateRegionFormatter
			}, {
				title : '状态',
				field : 'statusName',
				sortable : true,
				sortName : 'status'
			}, {
				title : '意向金',
				field : 'intention',
				sortable : true,
				sortName : 'intention'
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
			title : getFromStorage('#tradeList_queryTitle'),
			type : getFromStorage('#tradeList_queryType'),
			status : getFromStorage('#tradeList_queryStatus'),
			industryCode : getFromStorage('#tradeList_queryIndustryCode'),
			invoiceCode : getFromStorage('#tradeList_queryInvoiceCode'),
			payCode : getFromStorage('#tradeList_queryPayCode'),
			sn : getFromStorage('#tradeList_uerySn')
		};
		return temp;
	};
	return oTableInit;

	// value: 所在collumn的当前显示值，
	// row:整个行的数据 ，对象化，可通过.获取
	// 表格-操作 - 格式化
	function operateFormatter(value, row, index) {
		var operate = '<a class="view" href="'
				+ (path + 's/trade/tradeDetail?id=') + row.id + '" title="查看"><button type="button" class="btn btn-primary">查看</button></a>';
		if (row.status == 'trade_verify') {
			operate += ' <a class="verify" href="'
					+ (path + 's/trade/tradeVerify?id=') + row.id
					+ '" title="审核"><button type="button" class="btn btn-success">审核</button></a>';
		}
		return operate;
	}
};