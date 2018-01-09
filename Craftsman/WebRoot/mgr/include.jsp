<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.tcode.core.util.Utils" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + ":" + request.getServerPort();
	String jspPath = request.getRequestURI();
	String jsPath = jspPath.substring(0, jspPath.lastIndexOf("/")) + "/js" + jspPath.substring(jspPath.lastIndexOf("/"), jspPath.length() - 1);
%>
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="shortcut icon" href="<%=basePath %>/resources/images/favicon.png" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>/resources/ext/resources/css/ext-all-neptune.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/resources/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/resources/css/icon.css"/>
<script type="text/javascript" src="<%=basePath %>/resources/ext/bootstrap.js"></script>
<script async type="text/javascript" src="<%=basePath %>/resources/ext/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript">
    Ext.Loader.setPath('Ext.ux', '<%=basePath%>/resources/ext/ux');
    Ext.Ajax.defaultHeaders = {'Request-By': 'TSINGMA'};
	Ext.Ajax.on('requestcomplete', function(conn, response, options){
		try {
			var json = Ext.decode(response.responseText);  
			if(typeof json == 'object' && !json.success && !Ext.isEmpty(json)){
			    if(json.timeout){
			    	parent.logout();
			    }
			}
		} catch(e){
			//当后台返回异常数据时，停止最后一次ajax请求，隐藏messageBox，弹出错误信息。
			Ext.Ajax.abort();
			//Ext.MessageBox.hide();
			parent.showError(response.responseText);
		}
	}, this);
</script>

<%
if(!Utils.isEmpty(request.getQueryString())){
	Map<String, String> params = Utils.URLToMap(request.getQueryString());
	if(!Utils.isEmpty(params)){
%>
<script>
<%
		for(String key : params.keySet()){
%>
	var <%=key%> = '<%=params.get(key)%>';
<%
		}
%>
</script>
<%
	}
%>

<%} %>
<script src="<%=basePath + jsPath%>"></script>
<script async src="<%=basePath %>/resources/ext/ux/custom/Grid2Excel.js"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?1342037efbd12977a0de3d64429d52ed";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
