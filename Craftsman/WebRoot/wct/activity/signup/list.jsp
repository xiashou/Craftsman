<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta  http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="expires" content="0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="http://frozenui.github.io/frozenui/css/frozen.css">
    <script src="<%=basePath %>/wct/activity/signup/lib/zepto.min.js"></script>
    <script src="<%=basePath %>/wct/activity/signup/js/frozen.js"></script>
    <title>活动报名</title>
</head>
<body>
	<section id="searchbar">
    	<div  class="ui-searchbar-wrap ui-border-b">
            <div class="ui-searchbar ui-border-radius">
                <i class="ui-icon-search"></i>
                <div class="ui-searchbar-text">搜索活动名称</div>
                <div class="ui-searchbar-input"><input value="" type="text" placeholder="搜索活动名称" autocapitalize="off"></div>
                <i class="ui-icon-close"></i>
            </div>
            <button class="ui-searchbar-cancel">取消</button>
        </div>
        <script type="text/javascript">
            $('.ui-searchbar').tap(function(){
                $('.ui-searchbar-wrap').addClass('focus');
                $('.ui-searchbar-input input').focus();
            });
            $('.ui-searchbar-cancel').tap(function(){
                $('.ui-searchbar-wrap').removeClass('focus');
            });
        </script>
	</section>
	<section id="list">
		<s:if test="bannerList.size != 0">
		<div class="ui-slider" style="height:50px">
            <ul class="ui-slider-content" style="width: 300%">
            	<s:iterator value="bannerList" var="banner" >
            	<li>
            		<a href="<s:if test="link != null && link != ''">http://<s:property value="link" /></s:if><s:else>javascript:;</s:else>">
            			<span style="background-image:url(<%=basePath %>/upload/wechat/activity/banner/<s:property value="picture" />)"></span>
            		</a>
            	</li>
            	</s:iterator>
            </ul>
        </div>
        </s:if>
		<div class="ui-tab">
		    <ul class="ui-tab-nav ui-border-b">
		        <li class="current">全部活动</li>
		        <li>我参加的</li>
		    </ul>
		    <ul class="ui-tab-content" style="width:300%">
		        <li>
		        	<ul class="ui-list ui-list-link ui-border-tb">
				    	<s:iterator value="signupList" var="signup">
				    	<li class="ui-border-t" data-href="/open/wechat/biz/auth/initActSignupDetail.atc?auth=0&actId=<s:property value="id"/>&sid=<%=sid %>">
				            <div class="ui-list-img">
				                <span style="background-image:url(<%=basePath %>/upload/wechat/activity/<s:property value="%{imgString(pictures)}" />)"></span>
				            </div>
				            <div class="ui-list-info">
				                <h4 class="ui-nowrap"><s:property value="name" escape="false" /></h4>
				                <p class="ui-nowrap"><s:property value="address" escape="false" /></p>
				            </div>
				        </li>
				    	</s:iterator>
				    </ul>
		        </li>
		        <li>
		        	<ul class="ui-list ui-list-link ui-border-tb">
				    	<s:iterator value="joinList" var="signup">
				    	<li class="ui-border-t" data-href="/open/wechat/biz/auth/initActSignupDetail.atc?auth=0&actId=<s:property value="id"/>&sid=<%=sid %>">
				            <div class="ui-list-img">
				                <span style="background-image:url(<%=basePath %>/upload/wechat/activity/<s:property value="%{imgString(pictures)}" />)"></span>
				            </div>
				            <div class="ui-list-info">
				                <h4 class="ui-nowrap"><s:property value="name" escape="false" /></h4>
				                <p class="ui-nowrap"><s:property value="address" escape="false" /></p>
				            </div>
				        </li>
				    	</s:iterator>
				    </ul>
		        </li>
		    </ul>
		</div>
	    
	</section>
	<script>
		(function (){
	        var tab = new fz.Scroll('.ui-tab', {
		        role: 'tab',
		        autoplay: false,
		        interval: 3000
		    });
	        
	        <s:if test="bannerList.size != 0">
	        var slider = new fz.Scroll('.ui-slider', {
                role: 'slider',
                indicator: true,
                autoplay: true,
                interval: 3000
            });
	        </s:if>
	        
		    /* 滑动开始前
		    tab.on('beforeScrollStart', function(fromIndex, toIndex) {
		        console.log(fromIndex,toIndex);// from 为当前页，to 为下一页
		    })
	         */
		})();
        $('.ui-list li').click(function(){
        	//location.href= "detail.jsp";
            if($(this).data('href')){
                location.href= $(this).data('href');
            }
        });
   	</script>
</body>
</html>
