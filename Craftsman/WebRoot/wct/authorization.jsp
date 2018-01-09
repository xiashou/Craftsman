<%@page import="com.tcode.open.wechat.util.AuthorizationUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	AuthorizationUtil.validateAccess(request, response);	
%>
