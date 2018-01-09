<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>
<%
	String carId = request.getParameter("carId");
	String type = request.getParameter("type");
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-年检</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-cells__title title_bg">
    			<div class="text">年检到期设定</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label for="" class="weui-label">年检时间</label></div>
	                <div class="weui-cell__bd">
	                    <input id="carInspection" class="weui-input" type="date" value=""/>
	                </div>
	            </div>
	        </div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:save();" id="showTooltips">确认提交</a>
	        </div>
	        <div class="weui-cells__tips">温馨提示：千万不要填错哦！</div>
    	</div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    	var carId = '<%=carId %>';
    	var type = '<%=type %>';
    	
    	$(function(){
    		//初始化车辆信息
	        initCarInfo();
    		
    	});
    	
    	function initCarInfo() {
        	if(type == 'edit') {//修改，如果进行修改则初始化被修改车辆信息
    	     	//根据车辆ID获取车辆信息
    	     	$.ajax({
    	    	    type: 'POST',
    	 	   	    data: {
    	 	 			'car.id' : carId
    	 	 		},
    	    	    url: '/open/member/queryCarByCarId.atc',
    	    	    success: function(data){
    	    	    	var date = data.car.carInspection.replace(/\//g, "-");
    	    	    	$("#carInspection").val(date);
    	    	    },
    	            error: function(xhr, type){
    	             	alert('Ajax error!')
    	            }
    	    	});
        	}
        }
    	
	    function save() {
	    	$.showLoading("正在提交...");
	    	$.ajax({
	    	    type: 'POST',
	 	   	    data: {
	 	   	    	'car.id' : carId,
	 	   	    	'car.carInspection' : $("#carInspection").val()
	 	 		},
	    	    url: '/open/member/updateCarS.atc',
	    	    success: function(data){
	    	    	$.toast("提交成功", function() {
	    	    		$.hideLoading();
	    	    		location.href = "/open/wechat/biz/auth/initCenter.atc?sid=" + '<%=sid %>';
	    	    	});
	    	    },
	            error: function(xhr, type){
	             	alert('Ajax error!')
	            }
	    	});
	    }
	</script>
  </body>
</html>
