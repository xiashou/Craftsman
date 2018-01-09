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
    		<select id="shop_picker" onchange="changeSelect();" >
    			<option value="">所有</option>
    			<s:iterator value="deptList" var="dept" >
    				<option value="<s:property value="deptCode" />"><s:property value="deptName" escape="false" /></option>
    			</s:iterator>
    		</select>
    	</div>
	</div>
	
	<div class="weui-panel weui-panel_access tsm-nomargintop">
		<div class="weui-panel__bd">
	    	<a href="javascript:void(0);" class="weui-media-box_appmsg">
	      		<div class="weui-media-box__bd tsm-panelhead">
			      	<div class="today">
			      		<div class="title">库存品类总数</div>
			      		<div class="data"><s:property value="%{formatInteger(rList[0])}" /></div>
			      	</div>
			      	<div class="yesterday">
			      		<div class="title">库存总成本</div>
			      		<div class="data">¥<s:property value="%{formatDouble(rList[1])}" /></div>
			      	</div>
	      		</div>
	    	</a>
	  	</div>
	</div>
	
	<canvas id="chart-area" />
	<button id="randomizeData">Randomize Data</button>
    <button id="addDataset">Add Dataset</button>
    <button id="removeDataset">Remove Dataset</button>

<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
  	$(function() {
    	FastClick.attach(document.body);
  	});
  	function changeSelect() {
  		$.showLoading();
  		loadStock($("#shop_picker").val());
  		$.hideLoading();
  	}
  	function loadStock(deptCode){
  		$.ajax({
			url:'/boss/queryStock.atc',
		    type:'POST',
		    data:{
		    	deptCode: deptCode
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success){
		    		$("#data").html('');
		    		if(data.rList.length > 0) {
		    			$(".today .data").html(Number(data.rList[0]).toFixed(0));
		    			$(".yesterday .data").html("¥" + Number(data.rList[1]).toFixed(2));
		    		}
		    		if(data.stockList.length > 0) {
		    			var datas = new Array();
		    			var labels = new Array();
		    			$.each(data.stockList, function(n, value) {
		    				console.log(n);
		    				datas[n]= value.inPrice;
		    				labels[n]= value.typeName;
			    	  	});
		    		}
		    		var config = {
		    		        type: 'pie',
		    		        data: {
		    		            datasets: [{
		    		                data: datas,
		    		                backgroundColor: [
		    		                    "#F38630",
		    		                    "#E0E4CC",
		    		                    "#69D2E7",
		    		                    "#4D5360",
		    		                    "#F7464A",
		    		                    "#990033",
		    		                    "#663300",
		    		                    "#CC99CC",
		    		                    "#B45B3E",
		    		                    "#E8FFE8",
		    		                    "#F0DAD2",
		    		                    "#00B271",
		    		                    "#D5F3F4",
		    		                    "#66CCCC",
		    		                    "#479AC7",
		    		                    "#D7FFF0",
		    		                    "#FFFFCC",
		    		                ],
		    		                label: 'Dataset 1'
		    		            }],
		    		            labels: labels
		    		        },
		    		        options: {
		    		            responsive: true
		    		        }
		    		    };
		    		var ctx = document.getElementById("chart-area").getContext("2d");
		    		window.myPie = new Chart(ctx, config);
		    	}
		    }
		});
  	}
  	
  	
  	
  	
  	var randomScalingFactor = function() {
        return Math.round(Math.random() * 100);
    };
    
    window.chartColors = {
   		red: 'rgb(255, 99, 132)',
   		orange: 'rgb(255, 159, 64)',
   		yellow: 'rgb(255, 205, 86)',
   		green: 'rgb(75, 192, 192)',
   		blue: 'rgb(54, 162, 235)',
   		purple: 'rgb(153, 102, 255)',
   		grey: 'rgb(201, 203, 207)'
   	};

    var config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [
				<s:iterator value="stockList" var="stock">
				<s:property value="inPrice" />,
				</s:iterator>
                ],
                backgroundColor: [
                    "#F38630",
                    "#E0E4CC",
                    "#69D2E7",
                    "#4D5360",
                    "#F7464A",
                    "#990033",
                    "#663300",
                    "#CC99CC",
                    "#B45B3E",
                    "#E8FFE8",
                    "#F0DAD2",
                    "#00B271",
                    "#D5F3F4",
                    "#66CCCC",
                    "#479AC7",
                    "#D7FFF0",
                    "#FFFFCC",
                ],
                label: 'Dataset 1'
            }],
            labels: [
			<s:iterator value="stockList" var="stock">
				"<s:property value="typeName" escape="false" />",
			</s:iterator>
            ]
        },
        options: {
            responsive: true
        }
    };

    window.onload = function() {
        var ctx = document.getElementById("chart-area").getContext("2d");
        window.myPie = new Chart(ctx, config);
    };

</script>
</body>
</html>
