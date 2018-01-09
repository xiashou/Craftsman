<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
	String sid = request.getParameter("sid");
	String type = request.getParameter("type");
	String cid = request.getParameter("cid");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><s:property value="#session.title" escape="false" /></title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="/favicon.ico">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<link rel="stylesheet" href="<%=basePath %>/wct/mart/common/select/select.css">

</head>
<body>
	<div class="page-group">
		<div id="page-brand" class="page">
			<div class="tsm-head">
				<div class="tsm-title"><%=request.getParameter("brandName") %></div>
				<!--<span class="icon icon-right"></span>-->
			</div>
			<div class="content" style="margin-top: 43px">
				<div class="list-block contacts-block">
					<div class="list-group">
						<ul>
							<s:iterator value="seriesList" var="series" status="st">
							<li class="item-content">
						    	<div class="item-inner">
									<a href="/mall/initModelSelect.atc?type=<%=type %>&cid=<%=cid %>&sid=<%=sid %>&seriesId=<s:property value="id"/>&seriesName=<%=request.getParameter("brandName") %>-<s:property value="seriesName" escape="false"/>">
						          		<div class="item-title"><s:property value="seriesName" escape="false"/></div>
						        	</a>
						        </div>
						    </li>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
	<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
	<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
</body>
</html>