<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String domain = request.getServerName();
	if(domain != null && (domain.indexOf("tsingma") >= 0 || domain.indexOf("tcode") >= 0)) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tsingma/index.html");
		dispatcher.forward(request, response);
	} else 
		response.sendRedirect("/login.atc");
%>