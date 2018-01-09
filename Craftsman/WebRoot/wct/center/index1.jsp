<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css?v=1"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<!-- Head信息 -->
    	<div class="tsm-head">
    		<img src="/wct/dist/img/bg_index.jpg" alt="" width="100%" class="bg-blur">
    		<div class="mask"></div>
    		<div class="head-content">
    			<div class="m_info">
    				<div class="left-head">
    					<div class="head">
    						<img alt="" src='<s:property value="centerVo.wehcatHead"/>' width="100" height="100">
    					</div>
    					
    					<div class="name">
    						<!-- <s:property value="centerVo.wechatNick"/> -->
    						<s:if test="centerVo.sex == 1"><img alt="" src="/wct/dist/img/man.png" width="15px"></s:if>
    						<s:else><img alt="" src="/wct/dist/img/woman.png" width="15px"></s:else>
    					</div>
    					
    				</div>
    				<div class="right-info">
    					<div class="location">
    						<span><img alt="" src="/wct/dist/img/location.png" width="20px"></span>
    						<span class="text"><s:property value="centerVo.province"/>&nbsp;&nbsp;&nbsp;&nbsp;
    						<s:property value="centerVo.city"/></span>
    					</div>
    					<div class="car">
    						<span><img alt="" src="/wct/dist/img/car.png" width="20px"></span>
    						<a href="<%=basePath %>/wct/center/carList.jsp?sid=<%=sid %>">
    						<span class="text"><s:property value="centerVo.carNumber"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
    						<s:property value="centerVo.seriesName"/></span>
    					</div>
    					<div class="point">
    						<span><img alt="" src="/wct/dist/img/balance.png" width="20px"></span>
    						<span class="text">$ <s:property value="centerVo.balance"/>&nbsp;&nbsp;&nbsp;&nbsp;</span>
    						<span><img alt="" src="/wct/dist/img/point.png" width="20px"></span>
    						<span class="text"><s:property value="centerVo.point"/>&nbsp;积分</span>
    					</div>
    				</div>
    			</div>
    			<div class="num_bar">
    				<ul>
    					<li>
    						<!-- 无有保险日期则跳转到insurance.jsp -->
    						<s:if test="centerVo.insuranceRemainingTime == '--'">
    							<a href="<%=basePath %>/wct/center/insurance.jsp?type=add&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
    						</s:if>
    						<s:else>
    							<a href="<%=basePath %>/wct/center/insurance.jsp?type=edit&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
    						</s:else>
    						<span class="number">
    						<s:property value="centerVo.insuranceRemainingTime"/></span>天<br/>下次保险</a>
    					</li>
    					<li>
    						<s:if test="centerVo.inspectionRemainingTime == '--'">
    							<a href="<%=basePath %>/wct/center/inspection.jsp?type=add&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
    						</s:if>
    						<s:else>
    							<a href="<%=basePath %>/wct/center/inspection.jsp?type=edit&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
    						</s:else>
    						<span class="number">
    						<s:property value="centerVo.inspectionRemainingTime"/></span>天<br/>年检过期</a>
    					</li>
    					<li>
    						<s:if test="centerVo.maintainRemainingTime == '--'">
    							<a href="<%=basePath %>/wct/center/maintainAdd.jsp?type=add&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
    						</s:if>
    						<s:else>
    							<a href="<%=basePath %>/wct/center/maintainAdd.jsp?type=edit&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
    							<%-- <a href="<%=basePath %>/wct/center/maintain.jsp?sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>"> --%>
    						</s:else>
    						<span class="number"><s:property value="centerVo.maintainRemainingTime"/></span>天<br/>保养过期</a>
    					</li>
    					<li class="last">
    						<!-- 无查询记录则跳转到illegalSearch.jsp
    						<a href="<%=basePath %>/wct/center/illegal.jsp"><span class="number emp">0</span>次<br/>未处理违章</a> -->
    						<a href="<%=basePath %>/open/wechat/biz/auth/initMemberBook.atc?sid=<%=sid %>">预约服务</a>
    					</li>
    				</ul>
    			</div>
    		</div>
    	</div>
    	
    	<!-- 消息栏
    	<div class="tsm-message">
    		<div class="content">
    			<span class="notice"><img alt="" src="/wct/dist/img/notice.png" width="22px"></span>
    			<span class="text">您有一条消息未读！</span>
    		</div>
    	</div> -->
    	
   		<div class="weui-grids">
	        <a href="javascript:;" class="weui-grid tsm-index-grid">
	            <div class="weui-grid__icon">
	                <img src="/wct/dist/img/changepoint.png" alt="">
	            </div>
	        </a>
	        <a href="<%=basePath %>/open/wechat/biz/auth/initWechatActivityLottery.atc?sid=<%=sid %>" class="weui-grid tsm-index-grid">
	            <div class="weui-grid__icon">
	                <img src="/wct/dist/img/luckydraw.png" alt="">
	            </div>
	        </a>
	        <a href="<%=basePath %>/wct/center/orderRecord.jsp?sid=<%=sid %>" class="weui-grid tsm-index-grid">
	            <div class="weui-grid__icon">
	                <img src="/wct/dist/img/servicerecord.png" alt="">
	            </div>
	        </a>
	        <a href="<%=basePath %>/wct/center/memberStock.jsp?sid=<%=sid %>" class="weui-grid tsm-index-grid">
	            <div class="weui-grid__icon">
	                <img src="/wct/dist/img/cardpackage.png" alt="">
	            </div>
	        </a>
	        <a href="javascript:;" class="weui-grid tsm-index-grid">
	            <div class="weui-grid__icon">
	                <img src="/wct/dist/img/myorders.png" alt="">
	            </div>
	        </a>
	        <a href="javascript:;" class="weui-grid tsm-index-grid">
	            <div class="weui-grid__icon">
	                <img src="/wct/dist/img/drawcenter.png" alt="">
	            </div>
	        </a>
	    </div>
    	<!-- 底部导航栏 -->
	    <div class="weui-tabbar">
	        <a href="javascript:;" class="weui-tabbar__item">
	            <img src="/wct/dist/img/index_1.png" alt="" class="weui-tabbar__icon">
	            <p class="weui-tabbar__label">首页</p>
	        </a>
	        <a href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>" class="weui-tabbar__item">
	            <img src="/wct/dist/img/service_1.png" alt="" class="weui-tabbar__icon">
	            <p class="weui-tabbar__label">商城</p>
	        </a>
	        <a href="tel:<s:property value="centerVo.settingInfo.phone"/>" class="weui-tabbar__item">
	            <img src="/wct/dist/img/customer_1.png" alt="" class="weui-tabbar__icon">
	            <p class="weui-tabbar__label">客服中心</p>
	        </a>
	        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on">
	            <img src="/wct/dist/img/personal_2.png" alt="" class="weui-tabbar__icon">
	            <p class="weui-tabbar__label">个人中心</p>
	        </a>
	    </div>
	</div>
    	
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
	    $(function(){
	        $('.weui-tabbar__item').on('click', function () {
	            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
	            $.each($('.weui-tabbar img'), function(index, item){
	            	item.src = item.src.replace("_2", "_1");
	            });
	            $(this).find("img")[0].src = $(this).find("img")[0].src.replace("_1", "_2");
	        });
	    });
	</script>
  </body>
</html>
