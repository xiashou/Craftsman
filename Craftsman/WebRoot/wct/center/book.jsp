<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>预约服务</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/jquery-weui/dist/lib/weui.css">
	<link rel="stylesheet" href="/wct/jquery-weui/dist/css/jquery-weui.css">
    <link rel="stylesheet" href="/wct/dist/style/css.css?v=5"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-cells__title title_bg">
    			<div class="text">预约服务</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">预约时间</label></div>
	                <div class="weui-cell__bd">
	                    <input id="time" class="weui-input" type="datetime-local" formtarget="Y/m/d" value="" placeholder="请输入预约时间">
	                </div>
	            </div>
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">联系电话</label></div>
	                <div class="weui-cell__bd">
	                    <input id="tel" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入正确的手机号码"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">车牌号</label></div>
	                <div class="weui-cell__bd">
	                	<select class="weui-select tsm-bing-select" id="carShort">
	                    </select>
	                    <input id="carNum" class="weui-input tsm-bing-input" type="text" placeholder="请输入车牌号"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">预约项目</label></div>
	                <div class="weui-cell__bd">
	                    <a href="javascript:;" onclick="choiceService(this);" class="weui-btn weui-btn_mini weui-btn_default">洗车</a>
	                    <a href="javascript:;" onclick="choiceService(this);" class="weui-btn weui-btn_mini weui-btn_default">保养</a>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__bd">
	                	<textarea id="service" class="weui-textarea" placeholder="请输入想要预约的服务" rows="3"></textarea>
	                </div>
	            </div>
	        </div>
	        <div class="weui-cells__tips">温馨提示：<br><s:property value="bookTips.tips" /></div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:" id="confirm">立即预约</a>
	        </div>
    	</div>
    </div>
    <div id="toast" style="opacity: 0; display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-icon-success-no-circle weui-icon_toast"></i>
            <p class="weui-toast__content">预约成功</p>
        </div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="/wct/jquery-weui/dist/lib/jquery-2.1.4.js"></script>
	<script src="/wct/jquery-weui/dist/js/jquery-weui.js"></script>
    <script type="text/javascript">
    $(function(){

    	$("#confirm").click(function() {
	        var time = $('#time').val().replace("T", " ");
	        var tel = $('#tel').val();
	        var carNum = $('#carNum').val();
	        var carShort = $('#carShort').val();
	        var service = $('#service').val();
	       	if(!tel || !/1[3|4|5|7|8]\d{9}/.test(tel)) $.toptip('请输入正确的手机号');
	       	else if(!time) $.toptip('请输入预约时间');
	       	else if(!carNum) $.toptip('请输入车牌');
	       	else if(!service) $.toptip('请输入预约服务');
	       	else {
	        	$.ajax({
	                type:'post',
	                data: {
	        			'book.bookTime' : time,
	        			'book.phone' : tel,
	        			'book.carNumber' : carShort + carNum,
	        			'book.service' : service
	        		},
	                url:"/mall/insertMemberBook.atc",
	                dataType: 'json',
	                success: function(data){
	                	if(data.success){
	                		var $toast = $('#toast');
	                		$.toast("预约成功");
	                        setTimeout(function () {
	                            window.location.reload();
	                        }, 2000);
	                	}
	                },
	                error: function(xhr, type){
	                	$.alert(type);
	                }
	        	});
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
   		
    });
    
    function choiceService(obj){
    	if(obj.style.color == "rgb(255, 255, 255)"){
    		obj.style.color = "#000000";
        	obj.style.backgroundColor = "#F8F8F8";
        	$("#service").val($("#service").val().replace(obj.innerHTML + ",", ""));
    	} else {
    		obj.style.color = "#FFFFFF";
        	obj.style.backgroundColor = "#1AAD19";
        	$("#service").val($("#service").val() + obj.innerHTML + ",");
    	}
    }
    
	</script>
  </body>
</html>
