<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>销售数据</title>
    <jsp:include page="/wctmgr/include.jsp" flush="true" />
</head>

<body ontouchstart>

    <div class="weui-search-bar">
    	<div class="tsm-link">
    		<a class="current" href="#" onclick="changeTab(this, 'cDay', 'oDay');">今日</a>
    		<a href="#" onclick="changeTab(this, 'cMonth', 'oMonth');">本月</a>
    		<a href="#" onclick="changeTab(this, 'cYear', 'oYear');">本年</a>
    	</div>
    	<div class="tsm-shopsel">
    		<select id="shop_picker" onchange="changeSelect();" >
    			<option value="">所有</option>
    			<s:iterator value="deptList" var="dept" >
    				<option value="<s:property value="deptCode" />"><s:property value="deptName" /></option>
    			</s:iterator>
    		</select>
    	</div>
	</div>
	
	<div class="weui-panel weui-panel_access tsm-nomargintop">
		<div class="weui-panel__bd">
	    	<a href="javascript:void(0);" class="weui-media-box_appmsg">
	      		<div class="weui-media-box__bd tsm-panelhead">
			      	<div class="today">
			      		<div class="title">今日营业额</div>
			      		<div class="data"></div>
			      	</div>
			      	<div class="yesterday">
			      		<div class="title">昨日营业额</div>
			      		<div class="data"></div>
			      	</div>
	      		</div>
	    	</a>
	  	</div>
	</div>
	
	<div class="weui-flex tsm-flex-head">
		<div class="weui-flex__item">类型</div>
	  	<div class="weui-flex__item">实收金额（元）</div>
	</div>
	<div id="data">
	</div>

<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
	var current = 'cDay', previous = 'oDay';
  	$(function() {
    	FastClick.attach(document.body);
    	$.showLoading();
    	
    	loadSales(cDay);
    	loadPreSales(oDay);
    	loadSalesGroup(cDay);
    	
        $.hideLoading();
  	});
  	
  	function changeTab(obj, cparam, oparam) {
  		$(".tsm-link a").removeClass("current");
  		$(obj).addClass("current");
  		
  		current = cparam;
  		previous = oparam;
  		$.showLoading();
  		if(current == 'cDay')
  			$(".today .title").html("今日营业额");
  		else if(current == 'cMonth')
  			$(".today .title").html("本月营业额");
  		else
  			$(".today .title").html("本年营业额");
  		
  		if(previous == 'oDay')
  			$(".yesterday .title").html("昨日营业额");
  		else if(previous == 'oMonth')
  			$(".yesterday .title").html("上月营业额");
  		else
  			$(".yesterday .title").html("去年营业额");
  		
  		loadSales(current == 'cDay' ? cDay : (current == 'cMonth' ? cMonth : cYear), $("#shop_picker").val());
  		loadPreSales(previous == 'oDay' ? oDay : (previous == 'oMonth' ? oMonth : oYear), $("#shop_picker").val());
  		loadSalesGroup(current == 'cDay' ? cDay : (current == 'cMonth' ? cMonth : cYear), $("#shop_picker").val());
  		$.hideLoading();
  	}
  	
  	function changeSelect() {
  		$.showLoading();
  		loadSales(current == 'cDay' ? cDay : (current == 'cMonth' ? cMonth : cYear), $("#shop_picker").val());
  		loadPreSales(previous == 'oDay' ? oDay : (previous == 'oMonth' ? oMonth : oYear), $("#shop_picker").val());
  		loadSalesGroup(current == 'cDay' ? cDay : (current == 'cMonth' ? cMonth : cYear), $("#shop_picker").val());
  		$.hideLoading();
  	}
  	
  	function loadSales(date, deptCode){
  		$.ajax({
			url:'/boss/queryDayTurnover.atc',
		    type:'POST',
		    data:{
		    	companyId: AA,
		    	date: date,
		    	deptCode: deptCode
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success && data.price != null)
		    		$(".today .data").html("¥" + data.price);
		    	else
		    		$(".today .data").html("¥0.00");
		    }
		});
  	}
  	
  	function loadPreSales(date, deptCode){
  		$.ajax({
			url:'/boss/queryDayTurnover.atc',
		    type:'POST',
		    async:false,
		    data:{
		    	companyId: AA,
		    	date: date,
		    	deptCode: deptCode
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success && data.price != null)
		    		$(".yesterday .data").html("¥" + data.price);
		    	else
		    		$(".yesterday .data").html("¥0.00");
		    }
		});
  	}
  	
  	function loadSalesGroup(date, deptCode){
  		$.ajax({
			url:'/boss/querySalesGroupType.atc',
		    type:'POST',
		    async:false,
		    data:{
		    	companyId: AA,
		    	date: date,
		    	deptCode: deptCode
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success){
		    		$("#data").html('');
		    		if(data.orderList.length > 0) {
		    			$.each(data.orderList, function(n, value) {
			    			var div = $("<div class=\"weui-flex tsm-flex-item\"></div>")
			    			div.append("<div class=\"weui-flex__item\">" + value.orderType + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + Number(value.aprice).toFixed(2) + "</div>");
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
