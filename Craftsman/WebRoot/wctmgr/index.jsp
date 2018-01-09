<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>管家首页</title>
    <jsp:include page="/wctmgr/include.jsp" flush="true" />
</head>

<body ontouchstart>

	<div class="swiper-container">
		<div class="swiper-wrapper">
			<div class="swiper-slide"><img src="/wctmgr/dist/images/banner/banner1.png" /></div>
        	<div class="swiper-slide"><img src="/wctmgr/dist/images/banner/banner2.png" /></div>
        	<div class="swiper-slide"><img src="/wctmgr/dist/images/banner/banner3.png" /></div>
      	</div>
      	<div class="swiper-pagination"></div>
    </div>
    <div class="weui-row weui-no-gutter">
		<div id="sales" class="weui-col-50 tsm-col-big">
      		<div class="tsm-big-text">今日营业额</div>
      		<div class="tsm-big-number"><s:if test="price == null">0.00</s:if><s:else><s:property value="price" /></s:else></div>
      	</div>
      	<div id="members" class="weui-col-50 tsm-col-big tsm-no-left">
      		<div class="tsm-big-text">新增车主</div>
      		<div class="tsm-big-number"><s:property value="gownth" /></div>
      	</div>
    </div>

    <div class="weui-row weui-no-gutter">
      	<div id="fico" class="weui-col-33">
      		<div class="tsm-grid-img"><img src="/wctmgr/dist/images/cwgl.png" width="50px"></div>
      		<div class="tsm-grid-text">财务</div>
      	</div>
      	<div id="member" class="weui-col-33 tsm-no-left">
      		<div class="tsm-grid-img"><img src="/wctmgr/dist/images/khgl.png" width="50px"></div>
      		<div class="tsm-grid-text">会员</div>
      	</div>
      	<div id="performance" class="weui-col-33 tsm-no-left">
      		<div class="tsm-grid-img"><img src="/wctmgr/dist/images/jxgl.png" width="50px"></div>
      		<div class="tsm-grid-text">绩效</div>
      	</div>
      	<div id="store" class="weui-col-33 tsm-no-top">
      		<div class="tsm-grid-img"><img src="/wctmgr/dist/images/kcgl.png" width="50px"></div>
      		<div class="tsm-grid-text">库存</div>
      	</div>
      	<div id="mall" class="weui-col-33 tsm-no-top tsm-no-left">
      		<div class="tsm-grid-img"><img src="/wctmgr/dist/images/scgl.png" width="50px"></div>
      		<div class="tsm-grid-text">商城</div>
      	</div>
      	<div id="report" class="weui-col-33 tsm-no-top tsm-no-left">
      		<div class="tsm-grid-img"><img src="/wctmgr/dist/images/bbgl.png" width="50px"></div>
      		<div class="tsm-grid-text">报表</div>
      	</div>
    </div>
    

<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
  	$(function() {
    	FastClick.attach(document.body);
    	
    	$("#sales").click(function(){
    		window.location.href = "/boss/bsales.atc";
    	});
    	$("#fico").click(function(){
    		$.actions({
      			actions: [{
	      		    text: "应收账款",
	      		  	className: "tsm-green",
	      		    onClick: function() {
	      		    	window.location.href = "/boss/breceivable.atc";
	      		    }
      		  	},{
	      		    text: "应付账款",
	      		  	className: "tsm-red",
	      		    onClick: function() {
	      		    	window.location.href = "/boss/bpayable.atc";
	      		    }
      		  	}]
      		});
    	});
    	$("#member").click(function(){
    		$.actions({
      			actions: [{
	      		    text: "查找会员",
	      		  	className: "tsm-green",
	      		    onClick: function() {
	      		    	window.location.href = "/boss/bsearchMember.atc";
	      		    }
      		  	},{
	      		    text: "会员统计",
	      		  	className: "tsm-green",
	      		    onClick: function() {
	      		    	window.location.href = "/boss/bmember.atc";
	      		    }
      		  	}]
      		});
    	});
    	$("#performance").click(function(){
    		$.toast("内部测试中...", "forbidden");
    	});
    	$("#store").click(function(){
    		$.actions({
      			actions: [{
	      		    text: "查找货品",
	      		  	className: "tsm-green",
	      		    onClick: function() {
	      		    	window.location.href = "/boss/bsearchStore.atc";
	      		    }
      		  	},{
	      		    text: "库存统计",
	      		  	className: "tsm-green",
	      		    onClick: function() {
	      		    	window.location.href = "/boss/bstore.atc";
	      		    }
      		  	}]
      		});
    	});
  	});
  	
</script>
<script src="/wctmgr/dist/lib/swiper.min.js"></script>
<script>
    $(".swiper-container").swiper({
      	loop: true,
      	autoplay: 3000
    });
</script>
</body>
</html>
