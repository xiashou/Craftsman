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
    <div class="container" id="container">
    	
    	<!-- Head信息 -->
    	<div class="tsm-head">
    		<img src="/wct/dist/img/bg_index2.jpg" height="300px" width="100%" class="bg-noblur">
    		<img src="/wct/dist/img/bg_index2.jpg" height="300px" width="100%" class="bg-blur">
    		<div class="head-content">
    			<div class="m_info">
    				<div class="img-head">
    					<div class="head">
    						<img src='<s:property value="centerVo.wehcatHead"/>' width="150px" height="150px">
    					</div>
    				</div>
    				<div class="text-head">
    					<div class="name"><s:property value="centerVo.wechatNick" escape="false" /> 
    						<s:if test="centerVo.sex == 1"><img alt="" src="/wct/dist/img/man3.png" width="17px"></s:if>
    						<s:else><img alt="" src="/wct/dist/img/woman3.png" width="17px"></s:else>
    					</div>
    					<div class="info">
    						<span><img alt="" src="/wct/dist/img/balance1.png" width="20px"></span>
    						<span class="text">$ <s:property value="centerVo.balance"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    						<span><img alt="" src="/wct/dist/img/point1.png" width="20px"></span>
    						<span class="text"><s:property value="centerVo.point"/></span>
    					</div>
    					<div class="car">
    						<span><img src="/wct/dist/img/car2.png" width="24px"></span>
    						<a href="<%=basePath %>/wct/center/carList.jsp?sid=<%=sid %>">
    						<span class="text"><s:property value="centerVo.carNumber"/></span></a>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    	<div class="tsm-number">
    		<ul>
    			<li>
    				<!-- 无有保险日期则跳转到insurance.jsp -->
					<s:if test="centerVo.insuranceRemainingTime == '--'">
						<a href="<%=basePath %>/wct/center/insurance.jsp?type=add&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
					</s:if>
					<s:else>
						<a href="<%=basePath %>/wct/center/insurance.jsp?type=edit&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
					</s:else>
					<s:property value="centerVo.insuranceRemainingTime"/>天 保险
					</a>
				</li>
    			<li>
    				<s:if test="centerVo.inspectionRemainingTime == '--'">
						<a href="<%=basePath %>/wct/center/inspection.jsp?type=add&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
					</s:if>
					<s:else>
						<a href="<%=basePath %>/wct/center/inspection.jsp?type=edit&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
					</s:else>
					<s:property value="centerVo.inspectionRemainingTime"/>天 年检
					</a>
    			</li>
    			<li class="last">
    				<s:if test="centerVo.maintainRemainingTime == '--'">
						<a href="<%=basePath %>/wct/center/maintainAdd.jsp?type=add&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
					</s:if>
					<s:else>
						<a href="<%=basePath %>/wct/center/maintainAdd.jsp?type=edit&sid=<%=sid %>&carId=<s:property value='centerVo.carId'/>">
					</s:else>
					<s:property value="centerVo.maintainRemainingTime"/>天 保养
					</a>
    			</li>
    		</ul>
    	</div>
    	<div class="tsm-list">
    		<ul>
    			<li>
    				<div class="head"><img src='/wct/dist/img/book.png' width="75px" height="75px"></div>
    				<div class="text"><a href="<%=basePath %>/open/wechat/biz/auth/initMemberBook.atc?sid=<%=sid %>">预约服务</a></div>
    				<div class="arrow"><a href="<%=basePath %>/open/wechat/biz/auth/initMemberBook.atc?sid=<%=sid %>"><img src='/wct/dist/img/arrow.png' width="30px" height="30px"></a></div>
    			</li>
    			<li>
    				<div class="head"><img src='/wct/dist/img/record.png' width="75px" height="75px"></div>
    				<div class="text"><a href="<%=basePath %>/wct/center/orderRecord.jsp?sid=<%=sid %>">服务记录</a></div>
    				<div class="arrow"><a href="<%=basePath %>/wct/center/orderRecord.jsp?sid=<%=sid %>"><img src='/wct/dist/img/arrow.png' width="30px" height="30px"></a></div>
    			</li>
    			<li>
    				<div class="head"><img src='/wct/dist/img/store.png' width="75px" height="75px"></div>
    				<div class="text"><a href="<%=basePath %>/wct/center/memberStock.jsp?sid=<%=sid %>">卡片套餐</a></div>
    				<div class="arrow"><a href="<%=basePath %>/wct/center/memberStock.jsp?sid=<%=sid %>"><img src='/wct/dist/img/arrow.png' width="30px" height="30px"></a></div>
    			</li>
    			<li>
    				<div class="head"><img src='/wct/dist/img/gift.png' width="75px" height="75px"></div>
    				<div class="text"><a href="<%=basePath %>/open/wechat/biz/auth/initWechatActivityLottery.atc?sid=<%=sid %>">幸运抽奖</a></div>
    				<div class="arrow"><a href="<%=basePath %>/open/wechat/biz/auth/initWechatActivityLottery.atc?sid=<%=sid %>"><img src='/wct/dist/img/arrow.png' width="30px" height="30px"></a></div>
    			</li>
    		</ul>
    	</div>
    	
	</div>
	<div class="weui-footer">
        <p class="weui-footer__text">Copyright © 2015-2018 智能工匠</p>
        <p>&nbsp;</p>
    </div>
    	
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  </body>
</html>
