//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/session/getArray.do?sessionKey=menuList',
		"dataType" : "json",
		"success" : function(data) {
			var str="";
			for(var i=0;i<data.length;i++){
				var menu=data[i];
				str+='<li class="active"><a href="'+path+menu.url+'" target="mainFrame" >'+menu.name+'</a></li>';
			}
			$("#ul_leftMenu").html(str);
		}
	});
});

$(function(){
    $(".nav li").click(function(){
        $('.nav li').removeClass("active");
        $(this).addClass("active");
    });
});