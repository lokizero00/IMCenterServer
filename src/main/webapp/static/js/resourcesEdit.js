//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
	
	
	var parentId=0;
	$.ajax({
		type : 'get',
		url : path + 's/resources/resourcesDetail.do?id='+ paramId,
		dataType : "json",
		success : function(data) {
			$("#name").val(data.name);type
			$('#model').val(data.model);
			$('#type').val(data.type);
			$('#url').val(data.url);
			$('#description').val(data.description);
			$('#status').val(data.status);
			parentId=data.parentId;
			if(data.type=="menu"){
				$('#treeDiv').hide();
			}else{
				$('#treeDiv').show();
			}
		}
	});
	
	
	$('#type').change(function(item){
		var sel=$(this).val();
		if(sel=="menu"){
			$('#treeDiv').hide();
		}else{
			$('#treeDiv').show();
		}
	});
	
	$("#btnSubmit").click(function() {
		var name=$('#name').val();
		if(!name){
			toastr.warning('填写内容不正确！');
			return;
		}
		var model=$('#model').val();
		if(!model){
			toastr.warning('填写内容不正确！');
			return;
		}
		var type=$('#type').val();
		
		if(type!="menu"){
			var parentId=$('#tree').treeview("getChecked");
			if(parentId.length==0){
				toastr.warning('填写内容不正确！');
				return;
			}else{
				parentId=parentId[0].id;
			}
		}
		var url=$('#url').val();
		if(!url){
			toastr.warning('填写内容不正确！');
			return;
		}
		Ewin.confirm({ message: "确认要提交添加的数据吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var param = {};
			param.name=name;
			param.model=model;
			param.type=$('#type').val();
			param.url=url;
			param.description=$('#description').val();
			param.status=$('#status').val();
			param.parentId=parentId;
			$.ajax({
				type : 'POST',
				url : path + 's/resources/resourcesEdit.do',
				dataType : "json",
				data:param,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/resources/resourcesListPage';
					}else{
						toastr.error('添加失败！');
					}
				},error:function(data){
					if (data.statusText == 'OK') {
						toastr.error('您没有相关权限');
					} else {
						toastr.error(data.statusText);
					}
				}
			});
		});
	});
	 
	$.ajax({
        type:"post",
        dataType:"json",
        url:path+"s/resources/resourcesListTree.do?id=0",
        success:function(defaultData){
        	$.each(defaultData,function(index,value){
        		value["state"] = {};
        		if(value.id==61){
        			value["state"]["checked"] = true;
        		}
        		value["selectable"] = false;
    			$.each(value.nodes,function(index,value1){
    				value1["state"] = {};
    				if(value1.id==61){
    					value1["state"]["checked"] = true;
	        		}
    				value1["selectable"] = false;
    				value1.nodes=[];
            	});
        	});
            $('#tree').treeview({
                data: defaultData,//数据源参数
                color:"#000000",
                backColor:"#FFFFFF",
                showBorder: true,
                showCheckbox:true,
                highlightSelected : false,
                multiSelect : false,
                levels:2,
                selectable:true,
                onNodeChecked :function(event,node){
                	var data=$('#tree').treeview("getChecked");
                	$.each(data,function(index,value){
                		if(value.nodeId!=node.nodeId){
                			$('#tree').treeview('uncheckNode', value.nodeId, {
	                            silent : true,
	                         });
                		}
                	});
                }
              });
        },error:function(){
            alert("加载树异常！");
        }
    });
	 

})