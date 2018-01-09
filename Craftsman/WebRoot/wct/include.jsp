<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tcode.business.member.model.Member"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
	String sid = request.getParameter("sid");
	Member member = request.getSession().getAttribute("member") == null ? new Member() : (Member) request.getSession().getAttribute("member");
%>
	<script src="/wct/jquery-weui/dist/lib/jquery-2.1.4.js"></script>
	<script src="/wct/jquery-weui/dist/js/jquery-weui.js"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
		var sid = '<%=sid %>';
	</script>
	<script>
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "https://hm.baidu.com/hm.js?1342037efbd12977a0de3d64429d52ed";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
	</script>
