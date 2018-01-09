<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + ":" + request.getServerPort();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户登录</title>
<link rel="shortcut icon" href="<%=basePath%>/resources/images/favicon.png">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/ext/resources/css/ext-all-neptune.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/style.css"/>
<script type="text/javascript" src="<%=basePath%>/resources/ext/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>/resources/ext/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/mgr/common/js/login.js"></script>
</head>

<body>
	<div id="hello-win" class="x-hidden">
		<div id="hello-tabs">
			<img border="0" width="450" height="80" src="<%=basePath%>/resources/images/login_banner11.jpg" />
		</div>
	</div>
</body>
</html>
