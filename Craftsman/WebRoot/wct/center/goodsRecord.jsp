<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>
<%
	String goodsId = request.getParameter("goodsId");
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-消费记录</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css?v=1"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="weui-cells__title title_bg">
   			<div class="text">消费记录</div>
   		</div>
   		<p></p>
   		<div id="mainDiv" class="weui-cells tsm-record">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <table><tr><td>车辆</td><td>时间</td><td>次数</td></tr></table>
                </div>
            </div>
        </div>
        <!-- 
   		<div id="mainDiv" class="tsm-stock-content">
	   		<div class="weui-row">
	   			<div class="weui-col-33">车辆</div>
	   			<div class="weui-col-33">时间</div>
	   			<div class="weui-col-33">次数</div>
	   		</div>
   		</div>
   		 -->
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    var goodsId = '<%=goodsId %>';
    $(function(){
  		$.ajax({
    		type:'post',
    		url:  '<%=basePath %>/open/wechat/biz/auth/queryCenterForSaleRecord.atc?sid=<%=sid %>',
    		data: {
    			'member.memId' : '<%=member.getMemId() %>',
    			'member.deptCode' : '<%=member.getDeptCode() %>',
    			'orderItem.goodsId' : goodsId
    		},
    		dataType: 'json',
    		cache: false,
    		async: false,
    		success: function(data){
    			var mainDiv = $("#mainDiv");
    			$.each(data.orderItemList, function(index, item){
    				/*
       				var childrenDiv = $("<div class='weui-form-preview__item'><label class='weui-form-preview__label'>" + 
       					item.goodsName + "</label><span class='weui-form-preview__value'>" + 
       					item.number+ "次</span></div>");
    				
    				var childrenDiv = $("<div class=\"weui-row\"><div class=\"weui-col-33\">"+item.carNumber+"</div>"+
    						"<div class=\"weui-col-33\">"+item.saleDate+"</div><div class=\"weui-col-33\">"
    						+item.number+"次</div></div>");
    				*/
    				var childrenDiv = $("<div class=\"weui-cell\"><div class=\"weui-cell__bd\"><table><tr><td>"+item.carNumber+"</td><td>"+item.saleDate+"</td><td>"+item.number+"</td></tr></table></div></div>");
       				childrenDiv.appendTo(mainDiv);
    			});
    			
    		}
		});
    });
  </script>
  </body>
</html>
