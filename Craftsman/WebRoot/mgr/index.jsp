<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tcode.system.model.SysUser"%>
<%@ page import="com.tcode.system.model.SysDept"%>
<%@ page import="com.tcode.system.model.SysParam"%>
<%@ page import="com.tcode.business.shop.model.Setting"%>
<%@ page import="com.tcode.business.basic.model.BaseArea"%>
<%@ page import="com.tcode.core.util.WeatherUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + ":" + request.getServerPort();
	SysUser sessionUser = (SysUser) session.getAttribute("SESSION_USER");
	SysDept sessionDept = (SysDept) session.getAttribute("SESSION_DEPT");
	Setting setting = (Setting) session.getAttribute("SESSION_SETTING");
	BaseArea area = null;
	if(session.getAttribute("SESSION_AREA") != null)
		area = (BaseArea) session.getAttribute("SESSION_AREA") ;
	Map<String, String> sysParamMap = (Map<String, String>) application.getAttribute("SysParam");
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
	var AA = '<%=sessionDept.getId()%>', BB = <%=sessionUser.getRoleId()%>, CC = '<%=sessionUser.getUserId()%>', DD = '<%=sessionUser.getUserName()%>',
		EE = '<%=sessionDept.getAreaId()%>', FF = '<%=sessionDept.getParentId()%>', GG = '<%=sessionUser.getRealName()%>', HH = '<%=setting != null ? setting.getShopName() : "" %>',
		II = '<%=(area != null ? area.getAreaShort() : "")%>', JJ = '<%=(area != null ? area.getAreaCode() : "") %>', 
		KK = '<%=(setting != null && setting.getLogo() != null && !setting.getLogo().equals("")) ? "/upload/store/logo/" + setting.getLogo() : "/resources/images/1.png" %>';
	document.title = HH;
	Ext.Loader.setPath('Ext.Tsingma', '<%=basePath%>/mgr/common/js');
    Ext.Loader.setPath('Ext.ux', '<%=basePath%>/resources/ext/ux');
</script>
<script async type="text/javascript" src="<%=basePath%>/mgr/common/js/function.js"></script>
<script async type="text/javascript" src="<%=basePath%>/mgr/common/js/definition.js"></script>
<script async type="text/javascript" src="<%=basePath%>/mgr/common/js/index.js"></script>
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