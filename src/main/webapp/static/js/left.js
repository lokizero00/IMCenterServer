//项目根目录
var path = $("#contextPath").val();
$(document).ready(function() {
	$.ajax({
		"type" : 'get',
		"url" : path + 's/session/getArray.do?sessionKey=menuList',
		"dataType" : "json",
		"success" : function(data) {
			var str='';
			for(var i=0;i<data.length;i++){
				var menu=data[i];
				
				if(menu.parentId == 0){
					str+='<li> ';
					str+='<a data-toggle="collapse" href="#collapseOne'+menu.id+'" ><span class="glyphicon glyphicon-chevron-right"></span> '+menu.name+'</a>';
					str+='<div id="collapseOne'+menu.id+'" class="collapse"> ';
					str+='<ul class="nav navbar-nav" style="margin-left:10px;">';
					for(var j=0;j<data.length;j++){
						var menu_child=data[j];
						if(menu_child.parentId==menu.id){
							str+='<li><a href="'+path+menu_child.url+'" target="mainFrame" >'+menu_child.name+'</a></li>';
						}
					}
					str+='</ul>';
					str+='</div> ';
					str+='</li> ';
				}
			}
			$("#ul_leftMenu").html(str);
		}
	});
});

$(function(){
    $(".nav li ul li").click(function(){
        $('.nav li ul li').removeClass("active");
        $(this).addClass("active");
    });
});