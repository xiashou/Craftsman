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
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/gg.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/cart.css?v=1">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">var memId = <%=member.getMemId()%>, type = <%=request.getParameter("type") == null ? 1 : request.getParameter("type")%>;</script>
    <script src="<%=basePath %>/wct/mart/js/cart.js"></script>
    <script src="<%=basePath %>/wct/mart/common/layer/layer.js"></script>
    <title>购物车-<s:property value="#session.title" escape="false" /></title>
</head>
<body>
<div class="top">
	<s:if test="modelId != null && type != null && cid != null">
    <a href="/mall/initBrandSelect.atc?type=<s:property value="type" />&cid=<s:property value="cid" />&sid=<%=sid %>" class="again">重新选择</a>
    </s:if>
    <!-- <a href="javascript:history.back()" class="back"></a> -->
    <span>购物车</span>
    <a href="JavaScript:void(0)" class="edit">编辑</a>
</div>
<div class="main">
    <p class="title">全选</p>
    <s:iterator value="cartList" var="cart">
    <div class="product">
        <div class="commodity_box">
            <div class="commodity" data-gid="<s:property value="goodsId" />" data-mid="<s:property value="sendMode" escape="false" />" data-mname="<s:property value="modeName" escape="false" />">
                <div class="img lf">
                    <img src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(goods.pictures)}" />" alt="">
                </div>
                <div class="detail lf">
                    <p class="jieshao"><s:property value="goods.goodsName" escape="false" /></p>
                    <p class="size"><s:property value="modeName" escape="false" /></p>
                    <div class="price_num">
                        <div class="price lf">
                        	￥<span><s:property value="%{formatDouble(goods.aprice)}" escape="false"/></span>
                        </div>
                        <div class="num rf" data-gid="<s:property value="goodsId" />">
                            <button type="button">-</button>
                            <span id="data-gnum"><s:property value="%{formatInt(number)}" /></span>
                            <button type="button">+</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="del" data-gid="<s:property value="goodsId" />">删除</div>
        </div>
    </div>
    </s:iterator>
</div>
<div class="pay">
    <p>合计：￥ <span></span></p>
    <a href="javascript:;">结算(<span></span>)</a>
</div>
<s:if test="cartList.size == 0">
<div class="empty">
    <img src="<%=basePath %>/wct/mart/images/kong_cart.png" alt="">
    <s:if test="modelId != null && type != null && cid != null">
    <p>没有找到任何匹配的商品哦T.T</p>
    <p class="sle">重新来一次吧</p>
    <a href="/mall/initBrandSelect.atc?type=<s:property value="type" />&cid=<s:property value="cid" />&sid=<%=sid %>">重新选择</a>
    </s:if>
    <s:else>
    <p>购物车快饿扁了T.T<s:property value="modelId" /> </p>
    <p class="sle">主人快给我挑点宝贝吧</p>
    <a href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>">去逛逛</a>
    </s:else>
</div>
</s:if>

<footer>
    <a href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>">首页</a>
    <a href="/open/wechat/biz/auth/initMallCategory.atc?sid=<%=sid %>">分类</a>
    <a href="#" class="active">购物车</a>
    <a href="/open/wechat/biz/auth/initMallCenter.atc?sid=<%=sid %>">我的</a>
</footer>
</body>
</html>
