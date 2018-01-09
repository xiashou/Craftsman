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
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/orders.css?v=2">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">var memId = <%=member.getMemId()%>, type = <%=request.getParameter("type") == null ? 1 : request.getParameter("type")%>;</script>
    <script src="<%=basePath %>/wct/mart/common/layer/layer.js"></script>
    <title>我的订单-<s:property value="#session.title" escape="false" /></title>
</head>
<body>
<div class="order_list">
	<s:if test="orderList.size()==0">
		<div class="order">
        <div class="title" style="text-align: center"> 没有找到符合要求的订单!</div>
        </div>
	</s:if>
	<s:iterator value="orderList" var="order">
	<div class="order">
        <div class="title">
            订单号：<s:property value="orderId" />
            <span class="rf">
            <s:if test="status == 1">待付款</s:if>
            <s:elseif test="status == 2">待发货</s:elseif>
            <s:elseif test="status == 3">已发货</s:elseif>
            <s:elseif test="status == 4">已收货</s:elseif>
            <s:elseif test="status == 5">已完成</s:elseif>
            <s:elseif test="status == 0">已取消</s:elseif>
            </span>
        </div>
        <s:set var="num" value="0" />
        <s:iterator value="itemList" var="item">
        <div class="order_product">
            <img src="<%=basePath %>/upload/mall/goods/<s:property value="%{imgString(pictures)}" />" alt="">
            <div class="order_right">
                <div class="order_introduce"><s:property value="goodsName" /></div>
                <div class="order_data">数量：<span class="num"><s:property value="number" /></span>，&nbsp;<b>￥</b><span><s:property value="aprice" /></span></div>
                <s:set var="num" value="#num + number" />
            </div>
        </div>
        </s:iterator>
        <div class="end">共 <span><s:property value="#num" /> 件</span>，合计 <b>￥<span><s:property value="aprice" /></span></b></div>
        <div class="btn">
            <s:if test="status == 1">
            <a class="pay" href="/mall/initOrderPay.atc?orderId=<s:property value="orderId" />&type=<%=request.getParameter("type") == null ? 1 : request.getParameter("type")%>&sid=<%=sid%>">付款</a>
            <a class="cancel" href="javascript:void(0)" onclick="cancelOrder('<s:property value="orderId" />');">取消订单</a>
            </s:if>
            <s:elseif test="status == 3">
            	快递单号：<s:property value="expressNo" />&nbsp;
            <a class="pay" href="javascript:void(0)" onclick="confirmOrder('<s:property value="orderId" />');">确认收货</a>
            </s:elseif>
            <s:elseif test="status == 4">
            <a class="cancel" href="javascript:void(0)">已完成</a>
            </s:elseif>
            <s:elseif test="status == 0">
            <a class="cancel" href="javascript:void(0)">已取消</a>
            </s:elseif>
        </div>
    </div>
	</s:iterator>
</div>
<script type="text/javascript">
function cancelOrder(orderId) {
	if(confirm('确定要取消这笔订单吗?')){
		$.ajax({
			url: "/mall/cancelOrder.atc",
			type: "POST",
			data:{
				orderId: orderId,
			},
			success: function(result){
				if(result.success){
					layer.msg('操作成功！',{
    		            time:1000,
    		            area:['60%','']
    		        });
					setTimeout("window.location.reload();",1000);
				}
			}
		});
	} 
}

function confirmOrder(orderId) {
	if(confirm('确认收货吗?')){
		$.ajax({
			url: "/mall/confirmOrder.atc",
			type: "POST",
			data:{
				orderId: orderId,
			},
			success: function(result){
				if(result.success){
					layer.msg('操作成功！',{
    		            time:1000,
    		            area:['60%','']
    		        });
					setTimeout("window.location.reload();",1000);
				}
			}
		});
	} 
}
</script>
</body>
</html>
