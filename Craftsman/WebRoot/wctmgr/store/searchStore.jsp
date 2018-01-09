<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>库存查询</title>
    <jsp:include page="/wctmgr/include.jsp" flush="true" />
    <script src="/wctmgr/dist/lib/Chart.min.js"></script>
</head>

<body ontouchstart>

    <div class="weui-search-bar">
    	<div class="tsm-link"></div>
    	<div class="tsm-shopsel">
    		<select id="shop_picker">
    			<option value="">所有</option>
    			<s:iterator value="deptList" var="dept" >
    				<option value="<s:property value="deptCode" />"><s:property value="deptName" escape="false" /></option>
    			</s:iterator>
    		</select>
    	</div>
	</div>
	
	<div class="weui-panel weui-panel_access tsm-nomargintop">
		<div class="weui-panel__bd">
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="tsm-searchbar">
		      		<div class="title">输入商品名称或编码查询</div>
		      		<div class="input">
		      			<input id="keyword" type="text"><button id="search">查询</button>
		      		</div>
		      	</div>
      		</div>
	  	</div>
	</div>
	<div class="weui-flex tsm-flex-head">
	  	<div class="weui-flex__item tsm-long">名称/编码</div>
	  	<div class="weui-flex__item">类型</div>
		<!-- <div class="weui-flex__item">进货价</div> -->
	  	<div class="weui-flex__item">数量</div>
	</div>
	<div id="data">
	</div>

<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
  	$(function() {
    	FastClick.attach(document.body);
    	
    	$("#search").click(function(){
    		loadStock($("#keyword").val());
    	});
  	});
  	function loadStock(keyword){
  		$.ajax({
			url:'/boss/queryStock.atc',
		    type:'POST',
		    async:false,
		    data:{
		    	deptCode: $("#shop_picker").val(),
		    	keyword: keyword
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success){
		    		$("#data").html('');
		    		if(data.stockList.length > 0) {
		    			$.each(data.stockList, function(n, value) {
			    			var div = $("<div class=\"weui-flex tsm-flex-item\"></div>")
			    			div.append("<div class=\"weui-flex__item tsm-long\">" + value.name + "/" + value.code + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + value.typeName + "</div>");
			    			//div.append("<div class=\"weui-flex__item\">" + value.inPrice + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + value.number + "</div>");
			    			$("#data").append(div);
			    	  	});
		    		} else 
			    		$("#data").append("<div class=\"weui-flex tsm-flex-item\"><div class=\"weui-flex__item\">暂无数据</div></div>");
		    	}
		    }
		});
  	}

</script>
</body>
</html>
