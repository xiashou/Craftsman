<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../include.jsp"%>
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
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/category.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/swiper.min.css">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script src="<%=basePath %>/wct/mart/js/swiper.min.js"></script>
    <script src="<%=basePath %>/wct/mart/js/category.js"></script>
    <title>分类-<s:property value="#session.title" escape="false" /></title>
</head>
<body>
<!-- 
<div class="search_box">
    <a href="javascript:history.back()" class="back lf"></a>
    <a href="search.html" class="search lf">输入商品名称</a>
</div> -->
<div class="main">
    <div class="left">
        <ul>
        	<s:iterator value="goodsTypeList" status="st" var="type">  
        	<li <s:if test="#st.first">class="current"<s:set name="firstTypeName" value="typeName"></s:set></s:if>><s:property value="typeName" /></li>
        	</s:iterator>
        </ul>
    </div>
    <s:set name="type" value=""></s:set>
    <div class="right">
        <div style="display: block" class="right_content">
            <div class="category">
                <p class="title"><s:property value="firstTypeName" /></p>
                <div class="show">
                	<s:iterator value="goodsList" status="st" var="goods">
                	<s:if test="#type != typeId && #type != null">
                    </div></div></div>
					<div class="right_content"><div class="category"><p class="title"></p><div class="show">
					</s:if>
                	<div class="product">
                        <a class="link" href="/mall/initMallGoodsDetail.atc?goodsId=<s:property value="goodsId" />&sid=<%=sid %>">
                            <p class="introduce"><s:property value="goodsName" escape="false" /></p>
                            <img src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(pictures)}" />" alt="">
                        </a>
                    </div>
                    <s:set name="type" value="typeId"></s:set>
		        	</s:iterator>
                </div>
            </div>
        </div>
        
    </div>
</div>

<footer>
    <a href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>">首页</a>
    <a href="#" class="active">分类</a>
    <a href="/open/wechat/biz/auth/initMallCart.atc?sid=<%=sid %>">购物车</a>
    <a href="/open/wechat/biz/auth/initMallCenter.atc?sid=<%=sid %>">我的</a>
</footer>
</body>
</html>
