<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>消息列表</title>
<link href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/static/jQuery/jquery-2.1.4.min.js"></script>
<script src="<%=request.getContextPath()%>/static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/static/bootstrap/js/bootstrap-paginator.min.js"></script>
<link href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrapv3.css" rel="stylesheet">
<style type="text/css">
#queryDiv {
 margin-right: auto;
 margin-left: auto;
 width:100%;
 margin-top:50px;
 margin-bottom:20px;
}
#queryButton{
margin-right:20px;
float:right;
}
#textInput {
 margin-top: 10px;
 margin-left:10px;
}
#tableResult {
 margin-right: auto;
 margin-left: auto;
 width:100%;
}
#bottomTab{
margin-right:20px;
float:right;
}

</style>
</head>
<body>
	<div id = "queryDiv">
		标题：<input id = "queryTitle" name="queryTitle" type="text" placeholder="请输入标题"/>
		<button id = "queryButton" class="btn btn-primary" type="button">查询</button>
	</div>
	<form id="form1">
		<table class="table table-bordered" id = 'tableResult'>
			<caption>查询用户结果</caption>
			<thead>
				<tr>
					<th>序号</th>
					<th>标题</th>
					<th>创建时间</th>
					<th>创建人</th>
					<th>更新时间</th>
					<th>更新人</th>
					<th>类型</th>
					<th>通知人数</th>
				</tr>
			</thead>
			<tbody id="tableBody">
			</tbody>
		</table>
		<!-- 底部分页按钮 -->
		<div id="bottomTab"></div>
	</form>
	<script type='text/javascript'>    
	    var PAGESIZE = 10;
        var options = {  
            currentPage: 1,  //当前页数
            totalPages: 10,  //总页数，这里只是暂时的，后头会根据查出来的条件进行更改
            size:"normal",  
            alignment:"center",  
            itemTexts: function (type, page, current) {  
                switch (type) {  
                    case "first":  
                        return "|<";  
                    case "prev":  
                        return "<";  
                    case "next":  
                        return ">";  
                    case "last":  
                        return ">|";  
                    case "page":  
                        return  page;  
                }                 
            },  
            onPageClicked: function (e, originalEvent, type, page) {  
            	buildTable(page,PAGESIZE);//默认每页最多10条
            }  
        }  

        //获取当前项目的路径
        var urlRootContext = (function () {
            var strPath = window.document.location.pathname;
            var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
            return postPath;
        })();
        
       
        //生成表格
        function buildTable(pageNo,pageSize) {
        	 var url =  urlRootContext + "/s/notice/list.do"; //请求的网址
        	 var title=$("#queryTitle").val();
         var reqParams = {'title':title, 'pageNo':pageNo,'pageSize':pageSize};//请求数据
             $(function () {   
             	  $.ajax({
             	        type:"GET",
             	        url:url,
             	        data:reqParams,
             	        async:false,
             	        dataType:"json",
             	        success: function(data){
             	            if(data.isError == false) {
             	           // options.totalPages = data.pages;
             	        var newoptions = {  
                        currentPage: 1,  //当前页数
                        totalPages: data.pages==0?1:data.pages,  //总页数
                        size:"normal",  
                        alignment:"center",  
                        itemTexts: function (type, page, current) {  
                        switch (type) {  
                            case "first":  
                            return "|<";  
                            case "prev":  
                            return "<";  
                            case "next":  
                            return ">";  
                            case "last":  
                            return ">|";  
                        case "page":  
                        return  page;  
                }                 
            },  
            onPageClicked: function (e, originalEvent, type, page) {  
            	buildTable(page,PAGESIZE);//默认每页最多10条
            }  
         }             	           
         $('#bottomTab').bootstrapPaginator("setOptions",newoptions); //重新设置总页面数目
         var dataList = data.dataList;
         $("#tableBody").empty();//清空表格内容
         if (dataList.length > 0 ) {
             $(dataList).each(function(index){//重新生成
             	    $("#tableBody").append('<tr>');
             	    $("#tableBody").append('<th>'+(index+1+((pageNo-1)*10))+"</td>");
                    $("#tableBody").append('<td>' + this.title + '</td>');
                    $("#tableBody").append('<td>' + this.createTime + '</td>');
                    $("#tableBody").append('<td>' + this.adminCreatorName + '</td>');
                    $("#tableBody").append('<td>' + this.updateTime + '</td>');
                    $("#tableBody").append('<td>' + this.adminUpdaterName + '</td>');
                    $("#tableBody").append('<td>' + this.relationTypeName + '</td>');
                    $("#tableBody").append('<td>' + this.userCount + '</td>');
                    $("#tableBody").append('</tr>');
             	    });  
             	    } else {             	            	
             	          $("#tableBody").append('<tr><th colspan ="6"><center>查询无数据</center></th></tr>');
             	    }
             	    }else{
             	          alert(data.errorMsg);
             	            }
             	      },
             	        error: function(e){
             	           alert("查询失败:" + e);
             	        }
             	    });
               });
        }
        
        //渲染完就执行
        $(function() {
        	
        	//生成底部分页栏
            $('#bottomTab').bootstrapPaginator(options);     
        	
        	buildTable(1,10);//默认空白查全部
        	
            $("#queryButton").bind("click",function(){
            	buildTable(1,PAGESIZE);
            });
        });
    </script>
</body>
</html>