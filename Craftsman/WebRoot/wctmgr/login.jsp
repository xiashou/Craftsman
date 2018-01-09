<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;"/>
<title>智能工匠管家</title>
<link rel="stylesheet" type="text/css" href="/wctmgr/dist/css/style.css" />
<script type="text/javascript" src="/wctmgr/dist/lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/wctmgr/dist/js/phone.js" ></script>
<script type="text/javascript" src="/wctmgr/dist/lib/layer.min.js"></script>
<script type="text/javascript" src="/wctmgr/dist/js/login.js"></script>
<style type="text/css">
body{ background-color: #323542;}
</style>
</head>

<body>
<div class="whole">
	<div class="title">欢迎使用智能工匠</div>
    <div class="coordinates-icon">
    	<div class="coordinates topAct">
        	<!-- <img src="/wctmgr/dist/images/coordinates.png" /> -->
        	<img src="/resources/images/1.png" />
        </div>
        <div class="circle-1-1 circle-show-2"></div>
        <div class="circle-2-2 circle-show-1"></div>
        <div class="circle-3-3 circle-show"></div>
    </div>
    <div class="login-form">
    	<form action="#">
        	<div class="user-name common-div">
            	<span class="eamil-icon common-icon">
                	<img src="/wctmgr/dist/images/eamil.png" />
                </span>
                <input type="email" name="username" value="" placeholder="账号" />        
            </div>
            <div class="user-pasw common-div">
            	<span class="pasw-icon common-icon">
                	<img src="/wctmgr/dist/images/password.png" />
                </span>
                <input type="password" name="password" value="" placeholder="密码" />        
            </div>
            <div class="login-btn common-div"> 登  录 </div>
        </form>
    </div>
    <!-- 
    <div class="forgets">
    	<a href="#">Forget password?</a>
        <a href="#">New here?Sign up</a>
    </div>
     -->
</div>
</body>
</html>

