function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function operateDateFormatter(value, row, index) {
	// 设备信息列表格式化时间
	var html = ('<span>' + value.substr(0, 10) + '</span>');
	return html;
}

function operateRegionFormatter(value, row, index) {
	// 格式化地区信息
	var html = ('<span>' + row.provinceName+'-'+row.cityName+'-'+row.townName + '</span>');
	return html;
}

function refreshTable(tableId){
	var $table = $(tableId);
	if($table.bootstrapTable('getData').length>0){
		$table.bootstrapTable('selectPage', 1);
	}else{
		$table.bootstrapTable(('refresh'));
	}
}