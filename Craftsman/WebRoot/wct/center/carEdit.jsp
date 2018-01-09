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
    <title>个人中心-车辆信息</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <form>
   <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-cells__title title_bg">
    			<div class="text">添加车辆</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">品牌</label></div>
	                <div class="weui-cell__bd">
	                    <select class="weui-select tsm-select" id="carBrand" name="car.carBrand">
	                    </select>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">车型</label></div>
	                <div class="weui-cell__bd">
	                    <input id="carModel" name="" class="weui-input" type="text" placeholder="型号"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">车系</label></div>
	                <div class="weui-cell__bd">
	                    <input id="carSeries" class="weui-input" type="text" placeholder="系列"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">车牌号</label></div>
	                <div class="weui-cell__bd">
	                	<select class="weui-select tsm-bing-select" id="carShort">
	                    </select>
	                    <select class="weui-select tsm-bing-select" id="carCode">
	                    </select>
	                    <input id="carNum" class="weui-input tsm-bing-input" type="text" placeholder="请输入车牌号"/>
	                </div>
	            </div>
	        </div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:saveOrEdit();" id="confirm">保 存</a>
	        </div>
    	</div>
    </div>
    </form>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    var type = '<%=type %>';
    var carId = '<%=carId %>';
    
    $(function(){
    	$.ajax({
   	        type: 'POST',
   	        url: '/base/queryAllCarBrand.atc',
   	        success: function(data){
   	        	if($.isArray(data.carBrandList)){
   	        		$.each(data.carBrandList, function(index, item){
   	        			$('#carBrand').append('<option value=' + item.id + '>' + item.brandName + '</option>');
  	        		});
   	        	}
   	        	
   	        },
           	error: function(xhr, type){
           		alert('Ajax error!')
           	}
   	    });
    	$.ajax({
   	        type: 'POST',
   	        url: '/base/queryAllAreaShort.atc',
   	        success: function(data){
   	        	if($.isArray(data.areaShortList)){
   	        		$.each(data.areaShortList, function(index, item){
   	        			$('#carShort').append('<option value=' + item.areaShort + '>' + item.areaShort + '</option>');
  	        			})
   	        	}
   	        },
           	error: function(xhr, type){
           		alert('Ajax error!')
           	}
   	    });
   		$.ajax({
   	        type: 'POST',
   	        url: '/base/queryAllAreaCode.atc',
   	        success: function(data){
   	        	if($.isArray(data.areaCodeList)){
   	        		$.each(data.areaCodeList, function(index, item){
   	        			$('#carCode').append('<option value=' + item.areaCode + '>' + item.areaCode + '</option>');
  	        			})
   	        	}
   	        	
   	       		//初始化车辆信息
   	        	initCarInfo();
   	        },
            error: function(xhr, type){
            	alert('Ajax error!')
            }
   	    });
   		
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
	    	    	$("#carBrand").val(data.car.carBrand);
	    	    	$("#carModel").val(data.car.carModel);
	       			$("#carSeries").val(data.car.carSeries);
	       			$('#carShort').val(data.car.carShort);
	       			$('#carCode').val(data.car.carCode);
	       			$('#carNum').val(data.car.carNumber);
	    	    },
	            error: function(xhr, type){
	             	alert('Ajax error!')
	            }
	    	});
    	}
    }
    
    function saveOrEdit() {
    	if(type == 'edit') {
    		$.showLoading("正在修改...");
    		$.ajax({
	    	    type: 'POST',
	 	   	    data: {
	 	   	    	'car.id' : carId,
	 	   	    	'car.carBrand' : $("#carBrand").val(),
	 	 			'car.carModel' : $("#carModel").val(),
	 	 			'car.carSeries' : $("#carSeries").val(),
	 	 			'car.carShort' : $("#carShort").val(),
	 	 			'car.carCode' : $("#carCode").val(),
	 	 			'car.carNumber' : $("#carNum").val()
	 	 		},
	    	    url: '/open/member/updateCarS.atc',
	    	    success: function(data){
	    	    	$.toast("修改成功", function() {
	    	    		$.hideLoading();
	    	    		location.href = "/open/wechat/biz/auth/initCenter.atc?sid=" + '<%=sid %>';
	    	    	});
	    	    },
	            error: function(xhr, type){
	             	alert('Ajax error!')
	            }
	    	});
    	}else {//添加
    		$.showLoading("正在添加...");
    		$.ajax({
	    	    type: 'POST',
	 	   	    data: {
	 	   	    	'car.memberId' : '<%=member.getMemId() %>',
	 	   	    	'car.carBrand' : $("#carBrand").val(),
	 	 			'car.carModel' : $("#carModel").val(),
	 	 			'car.carSeries' : $("#carSeries").val(),
	 	 			'car.carShort' : $("#carShort").val(),
	 	 			'car.carCode' : $("#carCode").val(),
	 	 			'car.carNumber' : $("#carNum").val()
	 	 		},
	    	    url: '/open/member/insertCar.atc',
	    	    success: function(data){
	    	    	$.toast("添加成功", function() {
	    	    		$.hideLoading();
	    	    		location.href = "/open/wechat/biz/auth/initCenter.atc?sid=" + '<%=sid %>';
	    	    	});
	    	    },
	            error: function(xhr, type){
	             	alert('Ajax error!')
	            }
	    	});
    	}
    }
  	</script>
  </body>
</html>
