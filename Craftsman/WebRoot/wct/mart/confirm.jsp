<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <!--上三句的作用：清除浏览器的缓存，以达到刷新的作用-->
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="<%=basePath %>/wct/dist/style/weui.min.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/gg.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/confirm.css">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">var memId = <%=member.getMemId()%>, type = <%=request.getParameter("type") == null ? 1 : request.getParameter("type")%>;</script>
    <script src="<%=basePath %>/wct/mart/common/layer/layer.js"></script>
    <script src="<%=basePath %>/wct/mart/js/mui.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <title>确认订单-<s:property value="#session.title" escape="false"/></title>
</head>
<body>
<div class="affirm_pay">
    <div class="address_data">
        <a class="address_link" href="javascript:void(0)">
            <s:if test="addressList.size==0">
	    	<div class="address">
                <span>点击添加地址</span>
            </div>
	    	</s:if>
	        <s:else>
        	<s:iterator value="addressList" var="addr">
        	<s:if test="isDefault == 1">
        	<div class="consignee_data">
                <p class="consignee_l lf">收货人：<span id="consignee"><s:property value="name" escape="false" /></span></p>
                <p class="consignee_r rf" id="contact"><s:property value="mobile" escape="false" /></p>
            </div>
            <div class="address">收货地址：
            <span id="orderAddr"><s:property value="province" escape="false"/><s:property value="city" escape="false"/><s:property value="area" escape="false"/><s:property value="address" escape="false"/></span>
            </div>
        	</s:if>
        	</s:iterator>
        	</s:else>
        </a>
    </div>
    <div class="order_content">
    	<s:set var="number" value="0" />
    	<s:iterator value="cartList" var="cart">
    	<s:set var="number" value="#number+number" />
    	<div class="order">
            <img class="lf" src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(goods.pictures)}" />" alt="">
            <div class="data lf">
                <div class="order_introduce"><s:property value="goods.goodsName" escape="false" /></div>
                <div class="order_norms"><s:if test="modeName != ''"><s:property value="modeName" escape="false" /></s:if><s:else>到店安装</s:else></div>
                <div class="p_n">
                    <p class="price lf">￥<span><s:property value="%{formatDouble(goods.aprice)}" escape="false"/></span></p>
                    <p class="number rf">x<span><s:property value="%{formatInt(number)}" /></span></p>
                </div>
            </div>
        </div>
    	</s:iterator>
        <div class="freight">运费 <span>包邮</span></div>
        <div class="message">
        	<textarea id="remark" rows="3" placeholder="给商家留言"></textarea>
        </div>
    </div>
    <div class="coupon">
        <!--<div class="lf">
            <p>使用优惠券</p>
            <p class="coupon_num">未使用优惠券(<span>1</span>)</p>
        </div>
        <div class="rf">
            使用优惠券
        </div>-->
    <ul class="mui-table-view mui-table-view-radio payStyle" id="mui_box">
		<li class="mui-table-view-cell liClick" data-type="1">
			<a class="mui-navigate-right">
				<div class="pay_fun"><img src="<%=basePath %>/wct/mart/images/wechatpay.png" class="pay_img"/>&nbsp;&nbsp;微信支付</div>
			</a>
            <img src="<%=basePath %>/wct/mart/images/xuanzhong.png" class="xuanzhong" alt=""/>
		</li>
		<li class="mui-table-view-cell" data-type="2">
			<a class="mui-navigate-right">
				<div class="pay_fun"><img src="<%=basePath %>/wct/mart/images/store.png" class="pay_img"/>&nbsp;&nbsp;线下门店支付</div>
			</a>
            <img src="<%=basePath %>/wct/mart/images/xuanzhong.png" class="xuanzhong" alt=""/>
		</li>
    </ul>
    </div>
    <div class="finally">
        <p class="finally_num">共<span><s:property value="%{formatInt(#number)}" /></span>件商品</p>
        <div class="end">
            <p class="total">商品总金额：<b>￥</b><span><s:property value="mallOrder.oprice" /></span></p>
            <p class="youhui">优惠：<span><s:property value="mallOrder.oprice - mallOrder.aprice" /></span></p>
            <p class="shifu">实付：<b>￥</b><span><s:property value="mallOrder.aprice" /></span></p>
        </div>
    </div>
    <div class="affirm">
        <a href="javascript:;" >确认付款</a>
    </div>
</div>
<div  class="affirm_address">
<!-- 
    <div class="affirm_address_head">
        <ul>
            <li><img class="affirm_address_img" src="<%=basePath %>/wct/mart/images/back.png"/>确认</li>
            <li>我的地址</li>
            <li class="addBtn">添加</li>
        </ul>
    </div>
 -->
    <div class="affirm_address_main">
    	<s:if test="addressList!=null && addressList.size()==0">
    	<div class="nullDiv">
            <img src="<%=basePath %>/wct/mart/images/address.png"/>
            <p>你还没有收货地址</p><br />
        </div>
    	</s:if>
        <s:else>
        <div class="addContent">
        	<s:iterator value="addressList" var="addr">
        	<div class="weui-form-preview" data-id="<s:property value="id" />">
	            <div class="weui-form-preview__bd">
	                <div class="weui-form-preview__item tsm_head">
	                    <label class="weui-form-preview__label"><s:property value="name" escape="false" /></label>
	                    <span class="weui-form-preview__value"><s:property value="mobile" /></span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <span class="weui-form-preview__value"><s:property value="province" escape="false"/><s:property value="city" escape="false"/><s:property value="area" escape="false"/><s:property value="address" escape="false"/></span>
	                </div>
	            </div>
	            <div class="weui-form-preview__ft">
	            	<div class="weui-cells weui-cells_checkbox">
			            <label class="weui-cell weui-check__label">
			                <div class="weui-cell__hd">
			                    <input type="checkbox" class="weui-check" name="isDefault" onclick="setDefault(this);" <s:if test="isDefault == 1">checked="checked"</s:if> data-id="<s:property value="id" />">
			                    <i class="weui-icon-checked"></i>
			                </div>
			                <div class="weui-cell__bd">
			                    <p>设为默认地址</p>
			                </div>
			            </label>
			        </div>
			        <div class="tsm_button">
			        	<button onclick="initEdtAddress('<s:property value="id" />');">编辑</button> <button onclick="delAddress('<s:property value="id" />');">删除</button>
			        </div>
	            </div>
	        </div>
        	</s:iterator>
        </div>
        </s:else>
    </div>
    <div class="affirm_address_bottom" id="addAddr">新增收货地址</div>
</div>
<div class="address_add">
	<div class="address_add_head_l">
	    <img class="affirm_add_head_img" src="<%=basePath %>/wct/mart/images/back.png"/>
	   	<span class="affirm_add_head_f" >返回</span>
	    <div class="affirm_add_head_new" >新增地址</div>
	</div>
    <div class="address_add_main" id="address_add_main">
    	<form id="addrForm">
    	<div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                	<label class="weui-label">收货人</label>
                </div>
                <div class="weui-cell__bd">
                    <input id="aid" type="hidden">
                    <input id="name" class="weui-input" type="text" placeholder="收货人姓名">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                	<label class="weui-label">手机号码</label>
                </div>
                <div class="weui-cell__bd">
                    <input id="mobile" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入手机号">
                </div>
            </div>
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label class="weui-label">省份</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="select1" id="loc_province">
                    </select>
                </div>
            </div>
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">城市</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="select2" id="loc_city">
                    </select>
                </div>
            </div>
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">区</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="select3" id="loc_county">
                    </select>
                </div>
            </div>
	        <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea id="address" class="weui-textarea" placeholder="详细地址" rows="3"></textarea>
                    <div class="weui-textarea-counter"><span>0</span>/200</div>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                	<label class="weui-label">邮编</label>
                </div>
                <div class="weui-cell__bd">
                    <input id="postCode" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入邮编">
                </div>
            </div>
        </div>
        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:" id="addAddress">确定</a>
        </div>
        </form>
        <!-- 
        <ul>
            <li><span class="address_add_main_span">收货人</span><input  class="address_add_main_input" type="text" placeholder="请输入收货人" /></li>
            <li><span class="address_add_main_span">手机号码</span><input class="address_add_main_input"type="text" placeholder="请输入手机号码"/></li>
            <li><span class="address_add_main_span">所在省</span>
                    <select id="loc_province" value="0"></select>
            </li>
            <li><span class="address_add_main_span">所在市</span>
                <select id="loc_city" value="0"></select>
            </li>
            <li><span class="address_add_main_span">所在区</span>
                <select id="loc_county" value="0"></select>
            </li>
            <li><span class="address_add_main_span">详细地址</span><input class="address_add_main_input"type="text" placeholder="请输入详细地址"/></li>
            <li><span class="address_add_main_span">邮编</span><input class="address_add_main_input"type="text" placeholder="请输入邮编"/></li>
        </ul>
        <button class="address_add_main_but">保存</button>
         -->
    </div>
</div>


<div class="removeHint">
    <div class="removeHintContent">
        <p>系统提示</p>
        <p>确定要删除吗？</p>
        <div class="noBtn">取消</div>
        <div class="yesBtn">确定</div>
    </div>


</div>
<!-- <script src="js/jquery-1.8.3.min.js"></script>-->
    <script src="<%=basePath %>/wct/mart/js/confirm.js"></script>
    <script src="<%=basePath %>/wct/mart/js/location.js"></script>
<script>
	
	var payStr = '<s:property value="payStr" escape="false" />';
	if(payStr == ''){
		layer.msg('发生错误，请重新进入！',{
	        time:3000,
	        area:['60%','']
	    });
	}

    $(".data").css("width",parseInt($(".order").width())-80);

    $(".affirm_address,.address_add").css({
        height:$(window).height()
    });

    $(".address_link").click(function(){
    	$(".affirm_pay").hide();
    	$(".affirm_address").show();
    	document.title = '管理收货地址';
    });
    
    $("#addAddr").click(function(){
    	$("#addrForm")[0].reset();
    	$(".affirm_address").hide();
        $(".address_add").show();
        document.title = '新增收货地址';
    });

    /*
    $(".affirm_address_main input,.addBtn").click(function(){
        $(".affirm_address").hide();
        $(".address_add").show();
        $(".affirm_add_head_new").text("新增地址");
    });
    */

    $(".affirm_add_head_img,.affirm_add_head_f").click(function(){
        $(".address_add").hide();
        $(".affirm_address").show();
        document.title = '管理收货地址';
    });
    showLocation();
    
    $(".affirm a").click(function(){
    	
    	var type = $(".payStyle .liClick").attr("data-type");
    	if(type == 1){
    		var str = window.navigator.userAgent;
        	var version = str.substring(8, 11);
        	if(version != "5.0") {
        		alert("微信浏览器系统版本过低，请将微信升级至5.0以上");
        	} else {
        		callpay();
        	}
    	} else {
    		completeOrder(2);
    	}
    });
    
	function jsApiCall() {
		if('<s:property value="payStr" escape="false" />' != ''){
			WeixinJSBridge.invoke('getBrandWCPayRequest', <s:property value="payStr" escape="false" />,
				function(res) {
					WeixinJSBridge.log(res.err_msg);
					//alert(res.err_code+res.err_desc+res.err_msg); 
					if (res.err_msg == "get_brand_wcpay_request:ok") {
						completeOrder(1);
					} else {
						layer.msg('支付已取消！',{
	    		            time:2000,
	    		            area:['50%','']
	    		        });
					}
				});
		} else
			layer.msg('参数错误，请重新进入！',{
		        time:3000,
		        area:['60%','']
		    });
		
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
	
	function completeOrder(type){
		$.ajax({
    		url: "/mall/completeMallOrder.atc",
    		type: "POST",
    		data:{
    			'order.orderId': '<s:property value="mallOrder.orderId" />',
    			'order.orderType': type,
    			'order.remark': $("#remark").val(),
    			'order.consignee': $("#consignee").html(),
    			'order.contact': $("#contact").html(),
    			'order.address': $("#orderAddr").html(),
    			'order.modelId': '<s:property value="modelId" />',
    			'order.payType': type,
    			'order.status': type == 1 ? 2 : 1
    		},
    		success: function(result){
    			if(result.success){
    				if(type == 1)
    					layer.msg('支付成功！',{
        		            time: 1000,
        		            area:['60%','']
        		        });
    				else
    					layer.msg('订单已生成，请及时前往门店支付！',{
        		            time:3000,
        		            area:['80%','']
        		        });
    				window.location.href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>";
    			}
    		}
    	});
	}
</script>
</body>
</html>
