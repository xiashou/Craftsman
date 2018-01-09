<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>
<%
	String carId = request.getParameter("carId");
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-保养</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-cells__title title_bg">
    			<div class="text">保养详情</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label tsm-label">保养日期</label></div>
	                <div class="weui-cell__bd">
	                    <input id="carMaintain" class="weui-input" type="display" value="" placeholder="下次保养日期" readOnly="true"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label tsm-label">保养公里数</label></div>
	                <div class="weui-cell__bd">
	                    <input id="carKilometers" class="weui-input" type="display" pattern="[0-9]*" placeholder="保养里程（公里）" readOnly="true"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label tsm-label">下次保养</label></div>
	                <div class="weui-cell__bd">
	                    <input id="carNextkilo" class="weui-input" type="display" pattern="[0-9]*" placeholder="下次保养公里数"  readOnly="true"/>
	                </div>
	            </div>
	        </div>
    	</div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    	var carId = '<%=carId %>';
	  //根据车辆ID获取车辆信息
	  	$.showLoading("加载中...");
	 	$.ajax({
		    type: 'POST',
		   	    data: {
		 			'car.id' : carId
		 		},
		    url: '/open/member/queryCarByCarId.atc',
		    success: function(data){
		    	$("#carMaintain").val(data.car.carMaintain);
		    	$("#carKilometers").val(data.car.carKilometers);
	   			$("#carNextkilo").val(data.car.carNextkilo);
	   			$.hideLoading();
		    },
	        error: function(xhr, type){
	         	alert('Ajax error!')
	        }
		});
	</script>
  </body>
</html>
