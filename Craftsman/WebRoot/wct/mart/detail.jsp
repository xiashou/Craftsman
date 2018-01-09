<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.tcode.business.member.model.Member"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+path;
String sid = request.getParameter("sid");
String gid = request.getParameter("goodsId");
Member member = request.getSession().getAttribute("member") == null ? null : (Member) request.getSession().getAttribute("member");

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <!--<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">-->
    <!--<meta http-equiv="Pragma" content="no-cache">-->
    <!--<meta http-equiv="Expires" content="0">-->
    <!--上三句的作用：清除浏览器的缓存，以达到刷新的作用-->
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/gg.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/detail.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/swiper.min.css">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script src="<%=basePath %>/wct/mart/js/detail.js"></script>
    <script src="<%=basePath %>/wct/mart/common/layer/layer.js"></script>
    <script type="text/javascript">
		var mid = '<%=member == null ? "" : member.getMemId() %>', gid='<%=gid%>', sid='<%=sid%>';
	</script>
    <title>商品详情-<s:property value="#session.title" escape="false" /></title>
    <style>
        .layui-layer-setwin .layui-layer-close2 {
            position: absolute;
            right: -11px;
            top: -11px;
            width: 31px;
            height: 31px;
            margin-left: 0;
            background-position: -60px 0;
        }
    </style>
</head>
<body>
	<div class="swiper_img">
	    <div class="swiper-container">
	        <div class="swiper-wrapper">
	        	<s:iterator value="goods.pictures.split(',')" status="st" var="as">  
	       		<div class="swiper-slide">
	                <img src="<%=basePath %>/upload/mall/goods/<s:property value="#as"/>" alt="">
	            </div>
			    </s:iterator>
	        </div>
	        <!-- Add Pagination -->
	        <div class="swiper-pagination"></div>
	    </div>
	</div>

<div class="data">
    <p class="introduce"><s:property value="goods.goodsName"/></p>
    <p class="price">￥<s:property value="%{formatDouble(goods.aprice)}" escape="false"/></p>
    <p class="scj">市场价：￥<span><s:property value="%{formatDouble(goods.oprice)}" escape="false"/></span></p>
    <!-- 
    <div class="send">
        <div class="lf">
            运费：包邮
        </div>
        <div class="rf">
            默认配送：圆通速递
        </div>
    </div>
     -->
</div>
<div class="warm_prompt">
    规格：<s:property value="goods.spec" escape="false"/>
</div>
<div class="img_word">
    <p>图文详情</p>
    <s:property value="goods.details" escape="false"/>
</div>
<div class="tool_bar">
	<a class="buy_cart" href="/open/wechat/biz/auth/initMallCart.atc?sid=<s:property value="sid" />">
        <i class="icon_cart">
            <span class="num">0</span>
        </i>
    </a>
    <a class="join" href="javascript:void(0)">加入购物车</a>
    <a class="buy" href="javascript:void(0)">立即购买</a>
</div>

<!--****************layer层*************************-->
<div id="affirm_join" class="affirm">
    <div class="affirm_up">
        <div class="img_box lf">
            <img src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(goods.pictures)}" />" alt="">
        </div>
        <div class="affirm_jieshao lf">
        	<input type="hidden" value="<s:property value="goods.goodsId"/>" />
            <p class="affirm_introduce"><s:property value="goods.goodsName" escape="false" /></p>
            <p class="total">总价&nbsp;￥<span><s:property value="%{formatDouble(goods.aprice)}" escape="false"/></span></p>
        </div>
    </div>
    <div class="affirm_down">
        <div class="norms">
            <p>配送方式</p>
            <s:iterator value="goods.sendMode.split(',')" status="st" var="as">  
		    <s:iterator value="modeList" var="mode">
		    <s:if test="#as == id">
		    <a <s:if test="#st.index == 0">class="active"</s:if> href="javascript:void(0)" data-modeId="<s:property value="id"/>"><s:property value="modeName" escape="false" /></a>
		    </s:if>
		    </s:iterator>
		    </s:iterator>
        </div>
        <div class="affirm_num">
            数量
            <button type="button">-</button>
            <span>1</span>
            <button type="button">+</button>
            <s:property value="%{formatInt(goods.number)}"/>
        </div>
    </div>
    <a href="JavaScript:void(0)" class="affirm_ok">确定</a>
</div>
<div id="affirm_buy" class="affirm">
    <div class="affirm_up">
        <div class="img_box lf">
            <img src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(goods.pictures)}" />" alt="">
        </div>
        <div class="affirm_jieshao lf">
            <p class="affirm_introduce"><s:property value="goods.goodsName"/></p>
            <p class="total">总价&nbsp;￥<span><s:property value="%{formatDouble(goods.aprice)}" escape="false"/></span></p>
        </div>
    </div>
    <div class="affirm_down">
        <div class="norms">
            <p>配送方式</p>
            <s:iterator value="goods.sendMode.split(',')" status="st" var="as">  
		    <s:iterator value="modeList" var="mode">
		    <s:if test="#as == id">
		    <a <s:if test="#st.index == 0">class="active"</s:if> href="javascript:void(0)" data-modeId="<s:property value="id"/>"><s:property value="modeName" escape="false" /></a>
		    </s:if>
		    </s:iterator>
		    </s:iterator>
        </div>
        <div class="affirm_num">
            数量
            <button type="button">-</button>
            <span>1</span>
            <button type="button">+</button>
            <s:property value="%{formatInt(goods.number)}"/>
        </div>
    </div>
    <a href="JavaScript:void(0)" class="affirm_ok">确定</a>
</div>
<script src="<%=path %>/wct/mart/js/swiper.min.js"></script>

</body>
</html>
