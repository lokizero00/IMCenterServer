//项目根目录
var path = $("#contextPath").val();
var paramId=getQueryString('id');
$(document).ready(function() {
//	https://blog.csdn.net/qing_gee/article/details/73291345?ref=myread
	//发送异步请求加载所有的模块信息   https://blog.csdn.net/javareact/article/details/74330776  
    $.ajax({
        type:"post",
        dataType:"json",
        url:path+"s/resources/resourcesListTree.do?id="+paramId,
        success:function(defaultData){ 
        	
        	$.each(defaultData,function(index,value){
        		value["state"] = {};
        		if(value.nodes.length==0 && value.selectable){
        			value["state"]["checked"] = true;
        			value["selectable"] = false;
        		}else{
        			var num1=0;
        			$.each(value.nodes,function(index,value1){
            			value1["state"] = {};
                		if(value1.nodes.length==0 && value1.selectable){
                			value1["state"]["checked"] = true;
                			value1["selectable"] = false;
                			num1++;
                		}else{
                			var num2=0;
                			$.each(value1.nodes,function(index,value2){
                				value2["state"] = {};
                				value2["selectable"] = false;
                        		if(value2.selectable){
                        			value2["state"]["checked"] = true;
                        			num2++;
                        		}
                        	});
                			if(num2>0){
                				if(value1.nodes.length==num2){
                    				value1["state"]["checked"] = true;
                    				num1++;
                    			}
                				value1["state"]["selected"] = true;
                			}
                		}
                	});
        			value["state"] = {};
        			if(num1>0){
        				if(value.nodes.length==num1){
            				value["state"]["checked"] = true;
            			}
        				value["state"]["selected"] = true;
        			}
        		}
        	});    	 
        	var silentByChild = true;
            $('#tree').treeview({
                data: defaultData,//数据源参数
                color:"#000000",
                backColor:"#FFFFFF",
                showBorder: true,
                highlightSelected : false,
                showCheckbox:true, 
                multiSelect : false,
                levels:1,
                selectable:true,
                onNodeChecked :function(event,node){
                	 if (node.nodes.length != 0) {
                		 $.each(node.nodes, function(index, value) {
                         	$('#tree').treeview('checkNode', value.nodeId, {
                                 silent : true
                             });
                             $.each(value.nodes, function(index, value1) {
                             	$('#tree').treeview('checkNode', value1.nodeId, {
                                      silent : true
                                  });
                             	$.each(value1.nodes, function(index, value2) {
                                 	$('#tree').treeview('checkNode', value2.nodeId, {
                                          silent : true
                                      });
                                 });
                             });
                         });
                	 }else{
                		 var parentNode = $('#tree').treeview('getParent', node.nodeId);
                		 if(parentNode.nodes!=null){
                			 var isAllchecked = true; 
                    		 var siblings = $('#tree').treeview('getSiblings', node.nodeId);
    	            	     for ( var i in siblings) {
    	            	          if (!siblings[i].state.checked) {
    	            	              isAllchecked = false;
    	            	              break;
    	            	          }
    	            	      }
                    	      if (isAllchecked) {
                    	    	  $('#tree').treeview('checkNode', parentNode.nodeId, {
                    	               silent : true
                    	           });
                    	      } else {
                    	           $('#tree').treeview('selectNode', parentNode.nodeId, {
                    	                silent : true
                    	           });
                    	      } 
                		 }else{
                			$('#tree').treeview('checkNode', node.nodeId, {
              	               silent : true
              	           	});
                			$('#tree').treeview('selectNode', node.nodeId, {
            	                silent : true
            	           });
                		 }
                	 }
                },
                onNodeUnchecked :function(event,node){
                	if (node.nodes.length != 0) {
                		if(silentByChild){
                			$.each(node.nodes, function(index, value) {
                                $.each(value.nodes, function(index, value1) {
                                	$.each(value1.nodes, function(index, value2) {
                                    	$('#tree').treeview('uncheckNode', value2.nodeId, {
                                             silent : true
                                         });
                                    	 $('#tree').treeview('unselectNode', value2.nodeId, {
                                             silent : true
                                         });
                                    });
                                	$('#tree').treeview('uncheckNode', value1.nodeId, {
                                        silent : true
                                    });
                                	$('#tree').treeview('unselectNode', value1.nodeId, {
                                        silent : true
                                    });
                                });
                                $('#tree').treeview('unselectNode', value.nodeId, {
                                    silent : true
                                });
                                $('#tree').treeview('uncheckNode', value.nodeId, {
                                    silent : true
                                });
                            });
                		}
	               	 }else{
	               		var parentNode = $('#tree').treeview('getParent', node.nodeId);
	               		if(parentNode.nodes!=null){
	               			var isAllUnchecked = true; 
		                    var siblings = $('#tree').treeview('getSiblings', node.nodeId);
		                    for ( var i in siblings) {
		                        if (siblings[i].state.checked) {
		                            isAllUnchecked = false;
		                            break;
		                        }
		                    }
		                    if (isAllUnchecked) {
		                    	$('#tree').treeview('unselectNode', parentNode.nodeId, {
		                            silent : true,
		                        });
		                        $('#tree').treeview('uncheckNode', parentNode.nodeId, {
		                            silent : true,
		                        });
		                    } else{
		                    	 silentByChild = false;
		                        $('#tree').treeview('selectNode', parentNode.nodeId, {
		                            silent : true,
		                        });
		                        $('#tree').treeview('uncheckNode', parentNode.nodeId, {
		                            silent : true,
		                        });
		                    }
		                    silentByChild = true;
	               		}else{
	               			$('#tree').treeview('unselectNode', node.nodeId, {
	                            silent : true,
	                        });
	               			$('#tree').treeview('uncheckNode', node.nodeId, {
	                            silent : true,
	                        });
	               		}
	               	 }
                	
                },
                onNodeSelected: function(event, node) {
                	if(node.nodes.length==0){
                		$('#tree').treeview('selectNode', node.nodeId, {
                            silent : false
                         });
                	}
            		
                },
                onNodeUnselected: function (event, node) {
                	if(node.nodes.length==0){
                		$('#tree').treeview('selectNode', node.nodeId, {
                            silent : false
                         });
                	}
                }
              });
        },error:function(){
            alert("加载树异常！");
        }
    });
    
    $("#btnSubmit").click(function() {
    	
		Ewin.confirm({ message: "确认授予该角色权限吗？" }).on(function (e) {
			if(!e){
				return;
			}
			var authJson ={};
			authJson.resourcesId=getSelectedItem($('#tree').treeview("getSelected"));
			authJson.roleId=paramId;
			
			$.ajax({
				type : 'POST',
				url : path + 's/role/roleAuthorize.do',
				dataType : "json",
				data:authJson,
				success : function(data) {
					if(data.isError==false){
						window.location = path + 's/role/roleListPage';
					}else{
						toastr.error('授权失败！');
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
    
})

function getSelectedItem(data){
	var resourceId="";
	if(data.length>0){
		$.each(data,function(index,value){
			if(value.nodes.length==0 && value.state.checked){
				resourceId+=value.id+",";
			}else{
				var num=0;
				$.each(value.nodes,function(index1,value1){
					if(value1.nodes.length==0 && value1.state.checked){
						resourceId+=value1.id+",";
						num++;
					}else if(value1.state.checked){
						var num2=0;
						$.each(value1.nodes,function(index2,value2){
							if(value2.state.checked){
								resourceId+=value2.id+",";
								num2++;
							}
						});
						if(num2>0){
							resourceId+=value1.id+",";
						}
					}
				});
				if(num>0){
					resourceId+=value.id+",";
				}
			}
		});
	}
	return resourceId;
}

function uncheckedParentNode(node){
	var parentNode = $('#tree').treeview('getParent', node.nodeId);
		var isAllUnchecked = true; // 是否全部取消选中
		// 市级节点有一个选中，那么就不是全部取消选中
    var siblings = $('#tree').treeview('getSiblings', node.nodeId);
    for ( var i in siblings) {
        if (siblings[i].state.checked) {
            isAllUnchecked = false;
            break;
        }
    }
     // 全部取消选中，那么省级节点恢复到默认状态
    if (isAllUnchecked) {
    	$('#tree').treeview('unselectNode', parentNode.nodeId, {
            silent : true,
        });
        $('#tree').treeview('uncheckNode', parentNode.nodeId, {
            silent : true,
        });
    } else{
    	 silentByChild = false;
        $('#tree').treeview('selectNode', parentNode.nodeId, {
            silent : true,
        });
        $('#tree').treeview('uncheckNode', parentNode.nodeId, {
            silent : true,
        });
    }
    silentByChild = true;
}

