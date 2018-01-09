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
			<div class="content">
				<div id="content_list" class="list-block contacts-block">
					<div class="list-group">
						<ul>
							<li class="list-group-title">A</li>
							<s:iterator value="brandList" var="brand" status="st">
							<s:if test="#headWord != shortCode.substring(0,1) && #headWord != null">
							</ul></div><div class="list-group"><ul>
							<li class="list-group-title"><s:property value="shortCode.substring(0,1)"/></li>
							<s:set name="isFirst" value="true"></s:set>
							</s:if>
							<s:if test="#isFirst == true"><li data-ch="<s:property value="shortCode.substring(0,1)"/>"><s:set name="isFirst" value="false"></s:set></s:if>
							<s:else><li></s:else>
								<a class="external" href="/mall/initSeriesSelect.atc?type=<%=type %>&cid=<%=cid %>&sid=<%=sid %>&brandId=<s:property value="id"/>&brandName=<s:property value="brandName" escape="false"/>">
								<div class="item-content">
									<div class="item-media">
									<s:if test="logo != null">
									<img src="<%=basePath %>/upload/brand/<s:property value="logo"/>" onerror="this.src='<%=basePath %>/resources/images/noImage.jpg'" width="40px" />
									</s:if>
									<s:else><img src="<%=basePath %>/resources/images/noImage.jpg" width="40px" /></s:else>
									</div>
									<div class="item-inner"><div class="item-title"><s:property value="brandName" escape="false"/></div></div>
								</div>
								</a>
							</li>
							<s:set name="headWord" value="shortCode.substring(0,1)"></s:set>
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
	<script type='text/javascript' src='<%=basePath %>/wct/mart/common/select/select.js' charset='utf-8'></script>
	<script>
var app = app || {};
app.ItemList = function (data) {
	var list = [];
	var map = {};
	var elItemList = document.querySelector('#content_list');
	return {
		gotoChar: function (ch) {
			if (ch === '*') {
				elItemList.scrollTop = 0;
			} else if (ch === '#') {
				elItemList.scrollTop = elItemList.scrollHeight;
			} else {
				var target = elItemList.querySelector('[data-ch="' + ch + '"]');
				if (target) {
					target.scrollIntoView();
				}
			}
		}
	}
}
app.main = function () {
	var itemList = app.ItemList(app.data);
	new IndexSidebar().on('charChange', itemList.gotoChar);
}
app.main();
</script>
</body>
</html>