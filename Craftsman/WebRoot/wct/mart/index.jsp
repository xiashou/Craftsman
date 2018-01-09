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
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/index.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/swiper.min.css">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script src="<%=basePath %>/wct/mart/js/index.js"></script>
    <title><s:property value="#session.title" escape="false" /></title>
</head>
<body>
<div class="navbar">
	<!-- 
    <div class="navbar-inner">
        <a href="search.html" class="search">搜索你想要的商品</a>
    </div>
     -->
    <div class="sub_navbar">
        <div class="swiper-container swiper-container-1">
            <div class="swiper-wrapper">
            	<div class="swiper-slide switch swiper-slide-active active" id="showCarActionsheet">
            		<s:if test="car != null">
            		<a href="javascript:void(0)"  >
            			<s:if test="brand != null">
                    	<img src="<%=basePath %>/upload/brand/<s:property value="brand.logo" />" align="absmiddle" width="20px" />&nbsp;
                    	</s:if>
                    	<s:property value="#session.car.carModel" escape="false"/>&nbsp;&nbsp;|&nbsp;&nbsp; 当前里程<s:property value="#session.car.carKilometers" />km
                    </a>
            		</s:if>
                </div>
            </div>
            <!-- Add Pagination -->
        </div>
    </div>
    <div class="weui-skin_android" id="carActionsheet" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-actionsheet">
            <div class="weui-actionsheet__menu">
            	<s:iterator value="carList" var="car" >
                <div class="weui-actionsheet__cell">
                	<a href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>&carId=<s:property value="id" escape="false"/>">
                	<s:property value="carShort" escape="false"/><s:property value="carCode" escape="false"/><s:property value="carNumber" escape="false"/>&nbsp;<s:property value="carModel" escape="false" />
                	</a>
                </div>
                </s:iterator>
            </div>
        </div>
    </div>
</div>
<div style="display: block" class="main">
	<div class="day">
		<s:if test="car.model != '' && car.model != null">
		<a href="/mall/initSelectBuy.atc?type=2&cid=<s:property value="car.id" />&sid=<%=sid %>&modelId=<s:property value="car.model"/>&modelName=<s:property value="car.carModel" escape="false" />" class="day_left lf">
		</s:if>
		<s:elseif test="car.carBrand != '' && car.carBrand != null">
		<a href="/mall/initSeriesSelect.atc?type=2&cid=<s:property value="car.id" />&sid=<%=sid %>&brandId=<s:property value="car.carBrand"/>&brandName=<s:property value="brand.brandName" escape="false"/>" class="day_left lf">
		</s:elseif>
		<s:else>
        <a href="/mall/initBrandSelect.atc?type=2&cid=<s:property value="car.id" />&sid=<%=sid %>" class="day_left lf">
		</s:else>
            <img src="<%=basePath %>/wct/mart/images/1.png" height="50px" alt="">更换轮胎
        </a>
        <s:if test="car.model != '' && car.model != null">
		<a href="/mall/initSelectBuy.atc?type=3&cid=<s:property value="car.id" />&sid=<%=sid %>&modelId=<s:property value="car.model"/>&modelName=<s:property value="car.carModel" escape="false" />" class="day_left lf">
		</s:if>
		<s:elseif test="car.carBrand != '' && car.carBrand != null">
		<a href="/mall/initSeriesSelect.atc?type=3&cid=<s:property value="car.id" />&sid=<%=sid %>&brandId=<s:property value="car.carBrand"/>&brandName=<s:property value="brand.brandName" escape="false"/>" class="day_left lf">
		</s:elseif>
		<s:else>
        <a href="/mall/initBrandSelect.atc?type=3&cid=<s:property value="car.id" />&sid=<%=sid %>" class="day_left lf">
		</s:else>
            <img src="<%=basePath %>/wct/mart/images/2.png" height="50px" alt="">维修保养
        </a>
        <a href="/open/wechat/biz/auth/initMallCategory.atc?sid=<%=sid %>" class="day_right lf">
            <img src="<%=basePath %>/wct/mart/images/3.png" height="50px" alt="">车品商城
        </a>
        <a href="#" class="day_right lf">
            <img src="<%=basePath %>/wct/mart/images/4.png" height="50px" alt="">积分商城
        </a>
    </div>
    <div class="swiper_img">
        <div class="swiper-container swiper-container-2">
            <div class="swiper-wrapper">
            	<s:iterator value="bannerList" var="banner">
            	<div class="swiper-slide">
                    <a href="<s:if test="link == '' || line == 'null'">javascript:void(0)</s:if><s:else>/mall/initMallGoodsDetail.atc?goodsId=<s:property value="link" />&sid=<%=sid %></s:else>">
                    	<img src="<%=basePath %>/upload/mall/banner/<s:property value="picture" />" alt="">
                    </a>
                </div>
            	</s:iterator>
            </div>
            <div class="swiper-pagination"></div>
        </div>
        <div class="fbg"></div>
    </div>
    <div class="content">
            <!-- 
        <div class="product">
            <a class="bg_img" href="#"><img src="<%=basePath %>/wct/mart/images/a.png" alt=""></a>
            <div class="look">
                <a href="#">
                    <div class="lf"><img src="images/look.png" alt=""></div>
                    <div class="rf">
                        <p>面部护肤热销排行榜</p>
                        <p><span>点击查看</span>&gt;&gt;</p>
                    </div>
                </a>
            </div>
            
            <div class="show">
                <ul>
                    <li>
                        <div class="lf">
                            <a href="#">
                                <img src="images/aa.png" alt="">
                            </a>
                        </div>
                        <div class="rf">
                            <p><a href="#">米其林</a></p>
                            <a href="#" class="into">立即进入</a>
                        </div>
                    </li>
                    <li>
                        <div class="lf">
                            <a href="#">
                                <img src="images/aa.png" alt="">
                            </a>
                        </div>
                        <div class="rf">
                            <p><a href="#">倍耐力</a></p>
                            <a href="#" class="into">立即进入</a>
                        </div>
                    </li>
                    <li>
                        <div class="lf">
                            <a href="#">
                                <img src="images/aa.png" alt="">
                            </a>
                        </div>
                        <div class="rf">
                            <p><a href="#">固特异</a></p>
                            <a href="#" class="into">立即进入</a>
                        </div>
                    </li>
                    <li>
                        <div class="lf">
                            <a href="#">
                                <img src="images/aa.png" alt="">
                            </a>
                        </div>
                        <div class="rf">
                            <p><a href="#">邓禄普</a></p>
                            <a href="#" class="into">立即进入</a>
                        </div>
                    </li>
                </ul>
            </div>
            
        </div>
             -->
        <div class="hot_sale">
            <p class="hot_commodity">热销商品</p>
            <div class="hot_sale_show">
            	<s:iterator value="goodsList" var="goods">
            	<div class="hot_product">
            		<a href="/mall/initMallGoodsDetail.atc?goodsId=<s:property value="goodsId" />&sid=<%=sid %>">
                    <img src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(pictures)}" />" alt="">
                    <p class="introduce"><s:property value="goodsName" escape="false" /></p>
                    <p class="price">优惠价：￥<s:property value="%{formatDouble(aprice)}" escape="false"/> </p>
                    </a>
               	</div>
            	</s:iterator>
            </div>
        </div>
    </div>
</div>

<footer>
    <a href="#" class="active">首页</a>
    <a href="/open/wechat/biz/auth/initMallCategory.atc?sid=<%=sid %>">分类</a>
    <a href="/open/wechat/biz/auth/initMallCart.atc?sid=<%=sid %>">购物车</a>
    <a href="/open/wechat/biz/auth/initMallCenter.atc?sid=<%=sid %>">我的</a>
</footer>
<script src="<%=basePath %>/wct/mart/js/swiper.min.js"></script>

</body>
</html>
