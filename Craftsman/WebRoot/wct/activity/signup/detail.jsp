<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tcode.core.util.Utils"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../../wechat_jssdk.jsp"%>
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
    <link rel="stylesheet" href="<%=basePath %>/wct/activity/signup/css/gg.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/activity/signup/css/detail.css?v=3">
    <link rel="stylesheet" href="<%=basePath %>/wct/activity/signup/css/swiper.min.css">
    <script src="<%=basePath %>/wct/activity/signup/js/jquery-1.8.3.min.js"></script>
    <script>var sid='<%=sid%>';</script>
    <script src="<%=basePath %>/wct/activity/signup/js/detail.js?v=1"></script>
    <script src="<%=basePath %>/wct/mart/common/layer/layer.js"></script>
    <title>活动详情</title>
</head>
<body>
<div class="swiper_img">
    <div class="swiper-container">
        <div class="swiper-wrapper">
        	<s:iterator value="signup.pictures.split(',')" status="st" var="as">  
       		<div class="swiper-slide">
                <img src="<%=basePath %>/upload/wechat/activity/<s:property value="#as"/>" alt="">
            </div>
		    </s:iterator>
        </div>
        <!-- Add Pagination -->
        <div class="swiper-pagination"></div>
    </div>
</div>
<div class="data">
    <p class="introduce"><s:property value="signup.name" escape="false" /></p>
    <p class="price">¥ <s:property value="signup.price" escape="false" /></p>
    <div class="send">
        <div class="lf"> 报名 <s:property value="signup.signFicNumber" />/<s:property value="signup.number"/></div>
        <div class="rf">阅读  <s:property value="signup.readFicNumber" /></div>
    </div>
</div>
<div class="warm_prompt">
	<p class="time"><s:property value="signup.dateStart" />-<s:property value="signup.dateEnd" /></p>
	<p class="location"><s:property value="signup.address" escape="false" /></p>
	<p class="mobile"><s:property value="signup.contact" /></p>
	<s:if test="signup.organization != null && signup.organization != ''">
	<p class="zbf"><s:property value="signup.organization" /></p>
	</s:if>
</div>
<div class="img_word">
    <p class="title">图文详情</p>
    <s:property value="signup.introduce" escape="false" />
</div>
<s:set name="dateStart" scope="page" value="signup.dateStart"/>
<s:set name="dateEnd" scope="page" value="signup.dateEnd"/>
<div class="tool_bar">
    <a class="shangjia" href="javascript:void(0)"></a>
    <%
    String nowTime = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    String dateStart = (String) pageContext.getAttribute("dateStart");
    String dateEnd = (String) pageContext.getAttribute("dateEnd");
    
    if (Utils.daysBetween2(dateStart, nowTime, "yyyy/MM/dd") < 0){
    %>
    <a class="nobigen" href="javascript:void(0)">活动未开始</a>
    <%
    } else if(Utils.daysBetween2(nowTime, dateEnd, "yyyy/MM/dd") < 0){
   	%>
   	<a class="nobigen" href="javascript:void(0)">活动已结束</a>
    <%
    } else {
    %>
    	<s:if test="joiner == null">
	    <a class="join" href="javascript:void(0)">我要报名 剩余<%=Utils.daysBetween2(nowTime, dateEnd, "yyyy/MM/dd") %>天</a>
    	</s:if>
    	<s:else>
    	<a class="cancel" href="javascript:void(0)">取消报名 剩余<%=Utils.daysBetween2(nowTime, dateEnd, "yyyy/MM/dd") %>天</a>
    	</s:else>
    <%
    }
    %>
    
</div>

<!--****************layer层*************************-->
<div id="affirm_join" class="affirm">
    <div class="affirm_up">
        <div class="img_box lf">
            <img src="<%=basePath %>/upload/wechat/activity/<s:property value="%{imgString(signup.pictures)}" />" alt="">
        </div>
        <div class="affirm_jieshao lf">
        	<input id="actId" type="hidden" value="<s:property value="signup.id"/>" />
            <p class="affirm_introduce"><s:property value="signup.name" escape="false" /></p>
            <p class="total">总价&nbsp;¥<span><s:property value="%{formatDouble(signup.price)}" escape="false"/></span></p>
        </div>
    </div>
    <div class="affirm_down">
        <div class="norms">
            <p>增值服务</p>
            <s:if test="specList.size() > 0">
            <s:iterator value="specList" var="spec">  
		    <a href="javascript:void(0)" data-specId="<s:property value="id"/>" data-price="<s:property value="price" />"><s:property value="name" escape="false" /> &nbsp; ￥<s:property value="price" /></a>
		    </s:iterator>
            </s:if>
            <s:else>无</s:else>
        </div>
        <div class="affirm_num">
            数量
            <button type="button">-</button>
            <span>1</span>
            <button type="button">+</button>
        </div>
    </div>
    <a href="JavaScript:void(0)" class="affirm_ok">确定</a>
</div>

<script src="<%=basePath %>/wct/activity/signup/js/swiper.min.js"></script>
<script type="text/javascript">
	$('.shangjia').click(function(){
		location.href= "/open/wechat/biz/auth/initActSignupList.atc?auth=0&sid=<%=sid%>";
	});
	$('.tool_bar .cancel').click(function(){
    	if(confirm('取消活动后请联系活动发起人退款，确定取消报名吗？')) { 
    		$.ajax({
        		url: "/mall/cancelActJoiner.atc",
        		type: "POST",
        		data:{
        			'joiner.actId': <s:property value="signup.id" />,
        		},
        		success: function(result){
        			if(result.success){
        				layer.closeAll('page');
        		        layer.msg('取消成功',{
        		            time:1000
        		        });
        		        window.location .href = window.location .href;
        			}
        		}
        	});
    	}
    });
	$(function(){
		wx.ready(function(){
			wx.onMenuShareTimeline({
			    title: '<s:property value="signup.name" escape="false" />', // 分享标题
			    link: '<%=basePath %>/open/wechat/biz/auth/initActSignupDetail.atc?auth=0&actId=<s:property value="signup.id" />&sid=<%=sid %>', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			    imgUrl: '<%=basePath %>/upload/wechat/activity/<s:property value="%{imgString(signup.pictures)}" />', // 分享图标
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    }
			});
			wx.onMenuShareAppMessage({
			    title: '<s:property value="signup.name" escape="false" />', // 分享标题
			    desc: '<s:property value="signup.dateStart" />-<s:property value="signup.dateEnd" />  <s:property value="signup.address" escape="false" />', // 分享描述
			    link: '<%=basePath %>/open/wechat/biz/auth/initActSignupDetail.atc?auth=0&actId=<s:property value="signup.id" />&sid=<%=sid %>', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			    imgUrl: '<%=basePath %>/upload/wechat/activity/<s:property value="%{imgString(signup.pictures)}" />', // 分享图标
			    type: '', // 分享类型,music、video或link，不填默认为link
			    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    }
			});
		});
	});
</script>
</body>
</html>
