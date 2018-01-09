<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String oAuth2UserjsonStr = request.getParameter("json");
	System.out.print(oAuth2UserjsonStr);
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>微信绑定</title>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
    <link rel="stylesheet" href="/wct/jquery-weui/dist/lib/weui.css">
	<link rel="stylesheet" href="/wct/jquery-weui/dist/css/jquery-weui.css">
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-cells__title title_bg">
    			<div class="text">微信绑定</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">手机号码</label></div>
	                <div class="weui-cell__bd">
	                    <input id="tel" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入正确的手机号码"/>
	                </div>
	            </div>
	            <div class="weui-cell weui-cell_vcode">
	                <div class="weui-cell__hd">
	                    <label class="weui-label">验证码</label>
	                </div>
	                <div class="weui-cell__bd">
	                    <input id="code" class="weui-input" type="tel" placeholder="请输入验证码">
	                </div>
	                <div class="weui-cell__ft">
	                    <a href="javascript:;" class="weui-vcode-btn" id="verificationCode">获取验证码</a>
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
	            <a class="weui-btn weui-btn_primary" href="javascript:" id="confirm">确定</a>
	        </div>
    	</div>
    </div>
    <div class="js_dialog" id="iosDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__bd">请输入正确的手机号码！</div>
            <div class="weui-dialog__ft">
                <a href="javascript:$('#iosDialog').hide();" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="/wct/jquery-weui/dist/lib/jquery-2.1.4.js"></script>
	<script src="/wct/jquery-weui/dist/js/jquery-weui.js"></script>
    <script type="text/javascript">
    $(function(){

		var countdown = 60;
		function clickBind() {
			var tel = $('#tel').val();
	        var code = $('#code').val();
	       	if(!tel || !/1[3|4|5|7|8]\d{9}/.test(tel)) 
	       		$.toptip('请输入手机号');
	       	else {
	       		if(countdown == 60) {
	       			setVerification();
	    			//发送验证码
	       			$.ajax({
		                type:'post',
		                data: {
		        			'sid' : '<%=sid %>',
		        			'telphoneNum' : tel
		        		},
		                url:"/open/wechat/sys/initVerificationCode.atc",
		                dataType: 'json',
		                success: function(data){
		                	if(data.verificationCode.errCode != 0) 
		                		$.alert(data.verificationCode.errMsg);
		                	else {
		                		$.alert("验证码为：" + data.verificationCode.code + "。");
		                		if(data.car) {
		                        	initCarInfo(data.car);
		                        }
		                		/*
		                		$.toast("操作成功", function() {
			                        console.log('close');
			                        if(data.car) {
			                        	initCarInfo(data.car);
			                        }
			                    });
		                		*/
		                	}
		                },
		                error: function(xhr, type){
		                	$.alert(type);
		                }
		        	});
				}
	       }
		}
    	$("#verificationCode").bind("click", clickBind);
    	
    	$("#confirm").click(function() {
	        var tel = $('#tel').val();
	        var code = $('#code').val();
	        var carNum = $('#carNum').val();
	        var carShort = $('#carShort').val();
	        var carCode = $('#carCode').val();
	       	if(!tel || !/1[3|4|5|7|8]\d{9}/.test(tel)) $.toptip('请输入手机号');
	       	else if(!code || !/\d{6}/.test(code)) $.toptip('请输入六位手机验证码');
	       	else if(!carNum) $.toptip('请输入车牌');
	       	else {
	        	$.ajax({
	                type:'post',
	                data: {
	        			'sid' : '<%=sid %>',
	        			'telphoneNum' : tel,
	        			'code' : code,
	        			'carShort' : carShort,
	        			'carCode' : carCode,
	        			'carNum' : carNum
	        		},
	                url:"/open/wechat/sys/validateVerificationCode.atc",
	                dataType: 'json',
	                success: function(data){
	                	if(data.verificationCode.errCode != 0) 
	                		$.alert(data.verificationCode.errMsg);
	                	else
	                		location.href = "/open/wechat/biz/auth/initCenter.atc?sid=" + '<%=sid %>';
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
   		$.ajax({
   	        type: 'POST',
   	        url: '/base/queryAllAreaCode.atc',
   	        success: function(data){
   	        	if($.isArray(data.areaCodeList)){
   	        		$.each(data.areaCodeList, function(index, item){
   	        			$('#carCode').append('<option value=' + item.areaCode + '>' + item.areaCode + '</option>');
  	        			})
   	        	}
   	        },
            error: function(xhr, type){
            	alert('Ajax error!')
            }
   	    });
   	    
   	    function setVerification(){
   	    	if (countdown == 0) {
   	    		$("#verificationCode").removeClass("tsm-disabled");
   	    		$("#verificationCode").bind("click", clickBind);
   	    		$("#verificationCode").html("获取验证码");
   	    		countdown = 60;
   	    	} else {
   	    		$("#verificationCode").addClass("tsm-disabled"); 
   	    		$("#verificationCode").unbind("click");
   	    		$("#verificationCode").html(countdown + "s后重新获取");
   	    		countdown--; 
   	    		setTimeout(function() {
   	    			setVerification();
   	    		},1000) 
   	    	} 
   	    }
    });
    
    function initCarInfo(car) {
    	var carShort = car.carShort;
    	var carCode = car.carCode;
    	var carNumber = car.carNumber;
    	$('#carShort').val(carShort);
		$('#carCode').val(carCode);
		$('#carNum').val(carNumber);
    }
	</script>
  </body>
</html>
