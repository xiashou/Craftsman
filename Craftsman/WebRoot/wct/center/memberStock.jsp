<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-卡片套餐</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/jquery-weui/dist/lib/weui.css">
	<link rel="stylesheet" href="/wct/jquery-weui/dist/css/jquery-weui.css">
    <link rel="stylesheet" href="/wct/dist/style/css.css?v=2"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="weui-cells__title title_bg">
   			<div class="text">我的套餐卡片</div>
   		</div>
   		<p></p>
   		<div id="mainDiv" class="weui-cells tsm-stock">
        </div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    $(function(){
  		$.ajax({
    		type:'post',
    		url:  '<%=basePath %>/open/wechat/biz/auth/queryCenterForMemberStockInfo.atc?sid=<%=sid %>',
    		data: {
    			'member.memId' : '<%=member.getMemId() %>'
    		},
    		dataType: 'json',
    		cache: false,
    		async: false,
    		success: function(data){
    			var mainDiv = $("#mainDiv");
    			$.each(data.memberStockList, function(index, item){
    				var sourceName = item.sourceName;
    				if(!sourceName) sourceName = "赠送";
    				/*
       				var childrenDiv = $("<div class='weui-form-preview__item'><label class='weui-form-preview__label'>" + 
       					item.goodsName + "</label><span class='weui-form-preview__value'>" + 
       					item.number+ "次</span></div>");
    				
    				var childrenDiv = $("<div class=\"weui-row\"><div class=\"weui-col-25\">" + sourceName + "</div>" +
    						"<div class=\"weui-col-25\">"+item.goodsName+"</div><div class=\"weui-col-25\">"+item.number+"次</div>" +
    						"<div class=\"weui-col-25\"><a href='goodsRecord.jsp?sid=<%=sid %>&goodsId="+item.goodsId+"'>详情</a></div></div>");
    				*/
    				var childrenDiv = $("<a class=\"weui-cell weui-cell_access\" href=\"goodsRecord.jsp?sid=<%=sid %>&goodsId="+item.goodsId+"\"><div class=\"weui-cell__bd\">" +
    						"<table><tr><td>" + sourceName + "</td><td>"+item.goodsName+"</td><td>"+item.number+"次</td></tr></table></div>" +
    						"<div class=\"weui-cell__ft\">消费记录</div></a>");
       				childrenDiv.appendTo(mainDiv);
    			});
    			
    		}
		});
    });
  </script>
  </body>
</html>
