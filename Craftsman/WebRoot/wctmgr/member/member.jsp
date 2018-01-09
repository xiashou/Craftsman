<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>会员中心</title>
    <jsp:include page="/wctmgr/include.jsp" flush="true" />
    <script src="/wctmgr/dist/lib/Chart.min.js"></script>
</head>

<body ontouchstart>

	<div class="weui-panel weui-panel_access tsm-nomargintop">
		<div class="weui-panel__bd">
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="today">
		      		<div class="title">今日新增会员数</div>
		      		<div class="data"><s:property value="%{formatInteger(rList[0])}" /></div>
		      	</div>
		      	<div class="yesterday">
		      		<div class="title">本月新增会员数</div>
		      		<div class="data"><s:property value="%{formatInteger(rList[1])}" /></div>
		      	</div>
      		</div>
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="today">
		      		<div class="title">总会员数</div>
		      		<div class="data"><s:property value="%{formatInteger(rList[2])}" /></div>
		      	</div>
		      	<div class="yesterday">
		      		<div class="title">总车辆数</div>
		      		<div class="data"><s:property value="%{formatInteger(rList[3])}" /></div>
		      	</div>
      		</div>
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="today">
		      		<div class="title">会员总积分</div>
		      		<div class="data"><s:property value="%{formatInteger(rList[4])}" /></div>
		      	</div>
		      	<div class="yesterday">
		      		<div class="title">会员总余额</div>
		      		<div class="data">¥<s:property value="rList[5]" /></div>
		      	</div>
      		</div>
	  	</div>
	</div>
	
	<canvas id="canvas"></canvas>
	
<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
  	$(function() {
    	FastClick.attach(document.body);
  	});
  	window.chartColors = {
  		red: 'rgb(255, 99, 132)',
  		orange: 'rgb(255, 159, 64)',
  		yellow: 'rgb(255, 205, 86)',
  		green: 'rgb(75, 192, 192)',
  		blue: 'rgb(54, 162, 235)',
  		purple: 'rgb(153, 102, 255)',
  		grey: 'rgb(201, 203, 207)'
  	};
  	window.randomScalingFactor = function() {
  		return (Math.random() > 0.5 ? 10.0 : 10.0) * Math.round(Math.random() * 100);
  	};
  	
  	var barChartData = {
            labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            datasets: [{
                label: '新增会员数',
                backgroundColor: window.chartColors.green,
                data: [
                    <s:property value="%{formatInteger(rList[6])}" />,
                    <s:property value="%{formatInteger(rList[7])}" />,
                    <s:property value="%{formatInteger(rList[8])}" />,
                    <s:property value="%{formatInteger(rList[9])}" />,
                    <s:property value="%{formatInteger(rList[10])}" />,
                    <s:property value="%{formatInteger(rList[11])}" />,
                    <s:property value="%{formatInteger(rList[12])}" />,
                    <s:property value="%{formatInteger(rList[13])}" />,
                    <s:property value="%{formatInteger(rList[14])}" />,
                    <s:property value="%{formatInteger(rList[15])}" />,
                    <s:property value="%{formatInteger(rList[16])}" />,
                    <s:property value="%{formatInteger(rList[17])}" />
                ]
            }]

        };
        window.onload = function() {
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    title:{
                        display:true,
                        text:"月度新增会员情况表"
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false
                    },
                    responsive: true,
                    scales: {
                        xAxes: [{
                            stacked: true,
                        }],
                        yAxes: [{
                            stacked: true
                        }]
                    }
                }
            });
        };

</script>
</body>
</html>
