//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	$("#btnSubmit").click(function() {
		var r = confirm("确定要执行？");
		if (r === true) {
			var param = {};
			param.id=paramId;
			param.title = $("#articleEdit_title").val();
			param.content = $("#articleEdit_content").summernote('code');
			param.status = $("#articleEdit_status").val();
			$.ajax({
				"type" : 'post',
				"url" : path + 's/article/articleEdit.do',
				"dataType" : "json",
				"data" : param,
				"success" : function(data) {
					if (data.isError === true) {
						alert('发生错误：' + data.errorMsg);
					} else {
						window.location = path + 's/article/articleListPage';
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
	
	$('div.summernote').each(function() {
        var $this = $(this);
        var placeholder = $this.attr("placeholder") || '';
        var url = $this.attr("action") || '';
        $this.summernote({
            lang : 'zh-CN',
            placeholder : placeholder,
            minHeight : 300,
            dialogsFade : true,// Add fade effect on dialogs
            dialogsInBody : true,// Dialogs can be placed in body, not in
            // summernote.
            disableDragAndDrop : false,// default false You can disable drag
            // and drop
            callbacks: {
        	    onImageUpload: function(files) {
        	    	var $files = $(files);
        	        // 通过each方法遍历每个file
        	        $files.each(function() {
        	            var file = this;
        	            // FormData，新的form表单封装。具体可百度，但其有用法非常easy，例如以下
        	            var data = new FormData();
        	            // 将文件添加到file中，后端可获得到參数名为“file”
        	            data.append("file", file);
        	            // ajax上传
        	            $.ajax({
        	                data : data,
        	                type : "POST",
        	                url : url,// div上的action
        	                cache : false,
        	                contentType : false,
        	                processData : false,
        	                // 成功时调用方法，后端返回json数据
        	                success : function(response) {
        	                		if(response.isError){
        	                			alert(response.errorMsg);
        	                		}else{
        	                			var imageUrl = response.requestFileUrl;
        	                			$this.summernote('insertImage', imageUrl, function($image) {
        	                                // todo，兴许能够对image对象添加新的css式样等等，这里默认
        	                         });
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
        	        });
        	    }
        	  }
        });
    });
	
	$.ajax({
		"type" : 'get',
		"url" : path + 's/article/articleDetail.do?id='
				+ paramId,
		"dataType" : "json",
		"success" : function(data) {
			$("#articleEdit_title").val(data.title);
			$('#articleEdit_content').summernote('code', data.content);
			showSel("#articleEdit_status", "article_status",data.status);
		}
	});
});

// 配置下拉框
function showSel(elementName, elementType,currentValue) {
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
						opts += "<option value='" + data.code + "'";
						if (currentValue == data.code) {
							opts += " selected='selected'>";
						} else {
							opts += " >";
						}
						opts += data.value + "</option>";
					}
					// 查询界面
					$(elementName).append(opts);
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