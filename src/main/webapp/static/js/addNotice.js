//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	showSel("#noticeAdd_relationType", "notice_type");

	$("#btnSubmit").click(function() {
		var r = confirm("确定要执行？");
		if (r === true) {
			var param = {};
			param.title = $("#noticeAdd_title").val();
			param.content = $("#noticeAdd_content").val();
			param.relationType = $("#noticeAdd_relationType").val();
			param.relationId = 0;
			param.send=$('#noticeAdd_send').prop('checked');
			$.ajax({
				"type" : 'post',
				"url" : path + 's/notice/addNotice.do',
				"dataType" : "json",
				"data" : param,
				"success" : function(data) {
					if (data.isError === true) {
						alert('发生错误：' + data.errorMsg);
					} else {
						window.location = path + 's/notice/noticeList';
					}
				},
				error : function(data) {
					if (data.statusText == 'OK') {
						alert('您没有相关权限');
					} else {
						alert(data.statusText);
					}
				}
			});
		}
	});
});

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
					var opts = "";
					for (var data_index = 0; data_index < data_list.length; data_index++) {
						var data = data_list[data_index];
						opts += "<option value='" + data.code + "' >";
						opts += data.value + "</option>";
					}
					// 查询界面
					$(elementName).append(opts);
				}
			});
}