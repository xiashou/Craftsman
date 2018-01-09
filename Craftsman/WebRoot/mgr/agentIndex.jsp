<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tcode.system.model.SysAgent"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + ":" + request.getServerPort();
	SysAgent sessionAgent = (SysAgent) session.getAttribute("SESSION_AGENT");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="expires" content="0">
<base href="<%=basePath%>" />
<link rel="shortcut icon" href="<%=basePath %>/resources/images/favicon.png" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/ext/resources/css/ext-all-neptune.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/icon.css"/>
<script type="text/javascript" src="<%=basePath%>/resources/ext/bootstrap.js"></script>
<script async type="text/javascript" src="<%=basePath%>/resources/ext/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
	var AA = '1', BB = <%=sessionAgent.getRoleId()%>, CC = '<%=sessionAgent.getAgentId()%>', DD = '<%=sessionAgent.getAgentName()%>',FF='0',
	GG = '<%=sessionAgent.getRealName()%>', HH = '智能工匠', KK = '<%=sessionAgent.getAreaId()%>';
	document.title = '智能工匠';
	Ext.Loader.setPath('Ext.Tsingma', '<%=basePath%>/mgr/common/js');
    Ext.Loader.setPath('Ext.ux', '<%=basePath%>/resources/ext/ux');
</script>
<script async type="text/javascript" src="<%=basePath%>/mgr/common/js/function.js"></script>
<script async type="text/javascript" src="<%=basePath%>/mgr/common/js/definition.js"></script>
<script async type="text/javascript" src="<%=basePath%>/mgr/common/js/agentIndex.js"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?1342037efbd12977a0de3d64429d52ed";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>

</head>
<body>
</body>
</html>