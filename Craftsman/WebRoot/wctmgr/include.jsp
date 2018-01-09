<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tcode.system.model.SysUser"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + ":" + request.getServerPort();
	SysUser sessionUser = (SysUser) session.getAttribute("SESSION_MAGR");
%>
    <link rel="stylesheet" href="/wctmgr/dist/css/weui.min.css">
	<link rel="stylesheet" href="/wctmgr/dist/css/jquery-weui.min.css">
	<link rel="stylesheet" href="/wctmgr/dist/css/style.css">
	<script src="/wctmgr/dist/lib/jquery.min.js"></script>
	<script src="/wctmgr/dist/lib/jquery-weui.min.js"></script>
	<script type="text/javascript">
		var now = new Date();
		var year = now.getFullYear(), month = now.getMonth() + 1, date = now.getDate();
		var lyear = year - 1, lmonth = month - 1, ldate = date - 1;
		if(ldate == 0) {
			ldate = 1;
			lmonth = lmonth - 1;
		}
		if(lmonth == 0){
			lmonth = 1;
			lyear = lyear - 1;
		}
		if (month < 10) month = "0" + month;
        if (date < 10) date = "0" + date;
        if (lmonth < 10) lmonth = "0" + lmonth;
        if (ldate < 10) ldate = "0" + ldate;
		 
		var cYear = year, cMonth = cYear + "/" + month, cDay = cMonth + "/" + date;
		var oYear = lyear, oMonth = cYear + "/" + lmonth, oDay = cMonth + "/" + ldate;
		
		var AA = '<%=(sessionUser == null) ? "" : sessionUser.getDeptId()%>';
		if(AA == '')
			window.location.href = '/boss/bindex.atc';
	</script>
	<script>
	/**
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "https://hm.baidu.com/hm.js?1342037efbd12977a0de3d64429d52ed";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
	*/
	</script>
