<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="<%=basePath %>/wct/activity/signup/css/weui.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/jquery-weui/dist/lib/weui.min.css">
	<link rel="stylesheet" href="<%=basePath %>/wct/jquery-weui/dist/css/jquery-weui.min.css">
    <title>报名</title>
    <style type="text/css">
    .weui-form-preview__ft:after{border-top: 0;}
    .weui-form-preview__btn_primary {border-bottom: 1px solid #D9D9D9;font-size: 12px; }
    .weui-form-preview__ft{line-height: 35px}
    .weui-form-preview__hd{padding: 1px 15px;}
    .weui-cells{margin-top: 0}
    .tsm-margin{margin-top: 15px}
    .weui-media-box{padding: 15px 15px 10px 15px;}
    .weui-form-preview__hd{border-top: 1px solid #D9D9D9;}
    .weui-media-box_appmsg .weui-media-box__bd{margin-top: -20px;}
    </style>
</head>
<body ontouchstart>
	<section id="form">
		<div class="weui-panel__bd">
        	<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                	<input type="hidden" id="actId" value="<s:property value="signup.id" />"/>
                	<input type="hidden" id="orderNo" value="<s:property value="orderNo" />"/>
                    <img class="weui-media-box__thumb" src="<%=basePath %>/upload/wechat/activity/<s:property value="%{imgString(signup.pictures)}" />" alt="">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title"><s:property value="signup.name" escape="false" /></h4>
                    <p class="weui-media-box__desc">x<s:property value="number" /></p>
                </div>
            </a>
        </div>
        <div class="weui-cells weui-cells_checkbox">
        	<s:iterator value="specList" var="spec">
        	<s:if test="%{#request.specIds.indexOf(id.toString())>=0}">
        	<label class="weui-cell weui-check__label" for="<s:property value="id" />">
				<div class="weui-cell__hd">
					<input type="checkbox" class="weui-check" name="checkbox" value="<s:property value="price" />" id="<s:property value="id" />">
				</div>
				<div class="weui-cell__bd">
					<p><s:property value="name" escape="false" /> &nbsp; ¥<s:property value="price" /></p>
				</div>
			</label>
        	</s:if>
			</s:iterator>
		</div>
		<div class="weui-form-preview__hd">
            <div class="weui-form-preview__item">
                <label class="weui-form-preview__label">合计</label>
                <em class="weui-form-preview__value">¥<span id="total"><s:property value="price" escape="false" /></span></em>
            </div>
        </div>
		<div class="weui-cells weui-cells_form tsm-margin">
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="realName" placeholder="请输入姓名"/>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">手机号码</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="number" id="mobile" pattern="[0-9]*" placeholder="请输入手机号"/>
                </div>
            </div>
            <s:if test="signup.fields.indexOf('2') >= 0">
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">年龄</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="number" id="age" pattern="[0-9]*" placeholder="请输入年龄"/>
                </div>
            </div>
            </s:if>
            <s:if test="signup.fields.indexOf('3') >= 0">
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">身份证号</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="cardNo" placeholder="请输入身份证号"/>
                </div>
            </div>
            </s:if>
            <s:if test="signup.fields.indexOf('4') >= 0">
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">车牌号</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="carNumber" placeholder="请输入车牌号"/>
                </div>
            </div>
            </s:if>
            <s:if test="signup.fields.indexOf('1') >= 0">
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">性别</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" id="sex" name="select2">
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </div>
            </div>
            </s:if>
            <!-- 
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">年龄</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="number" id="age" pattern="[0-9]*" placeholder="请输入年龄"/>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">身份证号</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="cardNo" placeholder="请输入身份证号"/>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">车牌号</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="carNumber" placeholder="请输入车牌号"/>
                </div>
            </div>
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">性别</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" id="sex" name="select2">
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </div>
            </div>
         -->
        </div>
        <div class="weui-form-preview__ft">
                <a class="weui-form-preview__btn weui-form-preview__btn_primary open-popup" id="showTooltips" data-target="#full" href="javascript:">免责声明</a>
            </div>
		<!-- 
		<label for="weuiAgree" class="weui-agree">
            <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox">
            <span class="weui-agree__text">
                阅读并同意<a href="javascript:;" class="open-popup" data-target="#full">《相关条款》</a>
            </span>
        </label>
         -->
        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:" onclick="submit()" >提交</a>
        </div>
	</section>
	<div id="full" class='weui-popup-container'>
		<div class="weui-popup-overlay"></div>
		<div class="weui-popup-modal">
			<article class="weui_article">
				<h1>免责声明</h1>
				<section>
					<s:property value="statement.statement" escape="false" />
				</section>
				<section>
					<a href="javascript:;"
						class="weui_btn weui_btn_plain_primary close-popup">关闭</a>
				</section>
			</article>
		</div>
	</div>
	<div id="toast" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-icon-success-no-circle weui-icon_toast"></i>
            <p class="weui-toast__content">已完成</p>
        </div>
    </div>
	<script src="<%=basePath %>/wct/jquery-weui/dist/lib/jquery-2.1.4.js"></script>
	<script src="<%=basePath %>/wct/jquery-weui/dist/js/jquery-weui.js"></script>
	<script type="text/javascript">
	/*
	$(".weui-check").click(function(){
		if($(this).prop('checked')){
			$("#total").html(parseFloat(parseFloat($("#total").html()) + parseFloat($(this).val())).toFixed(2));
		} else {
			$("#total").html(parseFloat(parseFloat($("#total").html()) - parseFloat($(this).val())).toFixed(2));
		}
	});
	*/
	
	function jsApiCall() {
		if('<s:property value="payStr" escape="false" />' != ''){
			WeixinJSBridge.invoke('getBrandWCPayRequest', <s:property value="payStr" escape="false" />,
				function(res) {
					WeixinJSBridge.log(res.err_msg);
					if (res.err_msg == "get_brand_wcpay_request:ok") {
						join();
					} else {
						alert("支付取消！");
					}
				});
		}
	}

	function callpay() {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', jsApiCall,false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', jsApiCall);
				document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
			}
		} else {
			jsApiCall();
		}
	}
	
	function submit(){
		if($("#name").val() == ''){
			alert("姓名不能为空！");
			$("#name").focus();
		} else if($("#mobile").val() == ''){
			alert("手机号码不能为空！");
			$("#mobile").focus();
		} else {
			if(parseFloat($("#total").html()) > 0.0){
				var str = window.navigator.userAgent;
		    	var version = str.substring(8, 11);
		    	if(version != "5.0") {
		    		alert("微信浏览器系统版本过低，请将微信升级至5.0以上");
		    	} else {
		    		callpay();
		    	}
			} else {
				join();
			}
		}
	}
	
	function join() {
		$.ajax({
    		url: "/mall/insertActJoiner.atc",
    		type: "POST",
    		data:{
    			'joiner.actId': <s:property value="actId" />,
    			'joiner.spec': '<s:property value="specIds" />',
    			'joiner.orderNo': $("#orderNo").val(),
    			'joiner.realName': $("#realName").val(),
    			'joiner.number': '<s:property value="number" />',
    			'joiner.price': $("#total").html(),
    			'joiner.mobile': $("#mobile").val(),
    			<s:if test="signup.fields.indexOf('1') >= 0">
    			'joiner.sex': $("#sex").val(),
                </s:if>
    			<s:if test="signup.fields.indexOf('2') >= 0">
    			'joiner.age': $("#age").val(),
                </s:if>
    			<s:if test="signup.fields.indexOf('3') >= 0">
    			'joiner.cardNo': $("#cardNo").val(),
                </s:if>
    			<s:if test="signup.fields.indexOf('4') >= 0">
    			'joiner.carNumber': $("#carNumber").val()
                </s:if>
    		},
    		success: function(result){
    			if(result.success){
	    			$("#toast").show();
					setTimeout("$('#toast').hide();", 2000);
					setTimeout("window.location.href='/open/wechat/biz/auth/initActSignupDetail.atc?auth=0&actId=<s:property value='actId'/>&sid=<%=sid %>';", 2000);
    			}
    		}
    	});
	}
	
	
	</script>
</body>
</html>
