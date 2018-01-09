<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + ":" + request.getServerPort();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>用户登录</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="apple-touch-fullscreen" content="YES" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="shortcut icon" href="<%=basePath%>/resources/images/favicon.png">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/ext/resources/css/ext-all-neptune.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/style.css"/>
<style type="text/css">
.x-body, body{background:#fff url(/resources/images/bg_login.png) 0 0 no-repeat;font:18px/22px "微软雅黑";color:#7a6d6b;position:relative;background-size: 100% 100%;}
a:LINK{color:#888888; text-decoration: none;}
a:HOVER{color:#888888; text-decoration: underline;}
a:VISITED{color:#888888; text-decoration: none;}
</style>
<script type="text/javascript" src="<%=basePath%>/resources/ext/bootstrap.js"></script>
</head>

<body ontouchstart>
	<div class="right">
		<div class="login">
			<div class="title">账号登录</div>
			<div class="input">
				<input type="text" id="userName" placeholder="用户名">
				<input type="password" id="password" placeholder="密码">
				<input type="button" value="登&nbsp;录" onclick="login();"></button>
			</div>
		</div>
	</div>
	<div class="footer">深圳市智能工匠技术有限公司 <a href="http://www.miibeian.gov.cn/" target="_blank">粤ICP备16085903号</a></div>
<script>

document.getElementById("userName").onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){ // enter 键
    	 document.getElementById("password").focus();
    }
}; 

document.getElementById("password").onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){ // enter 键
    	 login();
    }
}; 

	
function login() {
	var userName = document.getElementById("userName");
	var password = document.getElementById("password");
		
	if(Ext.isEmpty(userName.value)){
		Ext.MessageBox.show({
			title: '提示',
			msg: "用户名不能为空！",
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.INFO
		});
		userName.focus();
		return false;
	} else if(Ext.isEmpty(password.value)) {
		Ext.MessageBox.show({
			title: '提示',
			msg: "密码不能为空！",
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.INFO
		});
		password.focus();
		return false;
	}
	
	Ext.MessageBox.show({
		title : '请等待',
		msg : '正在登录,请稍候.....',
		width : 250
	});
	
	Ext.Ajax.request({
        method:'POST',
        url: '/userLogin.atc',
        params : {
        	'sysUser.userName' : userName.value,
        	'sysUser.password' : password.value
		},
		success : function(resp, opts) {
			Ext.MessageBox.hide();
			var result = Ext.decode(resp.responseText);
			if(result.success){
				Ext.MessageBox.show({
					title : '请等待',
					msg : '正在验证您的身份,请稍候.....',
					width : 250,
					wait : true,
					waitConfig : {
						interval : 50
					}
				});
				setCookie("login.userName", userName.value, 240);
				window.location.href = '/index.atc';
			} else {
				Ext.MessageBox.show({
		           title: '提示',
		           msg: result.msg,
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.ERROR
                });
			}
		},
		failure : function(resp, opts) {
			Ext.MessageBox.hide();
			Ext.MessageBox.show({
		           title: '提示',
		           msg: action.result.msg != '' ? action.result.msg : '与服务器失去连接！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.ERROR
		    });
		}
    });
	
}

/**
 * 设置Cookie
 * @param {} name
 * @param {} value
 */
function setCookie(name, value, minuts) {
	var argv = setCookie.arguments;
	var argc = setCookie.arguments.length;
    var expiration = new Date((new Date()).getTime() + minuts * 60000 * 60);
	document.cookie = name + "=" + escape(value) + "; expires=" + expiration.toGMTString();
}

/**
 * 获取Cookie
 * @param {} Name
 * @return {}
 */
function getCookie(Name) {
	var search = Name + "="
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search)
		if (offset != -1) {
			offset += search.length
			end = document.cookie.indexOf(";", offset)
			if (end == -1)
				end = document.cookie.length
			return unescape(document.cookie.substring(offset, end))
		} else
			return ""
	}
}

/**
 * 从缓存中清除Cookie
 * @param {} name
 */
function clearCookie(name) {
	var expdate = new Date();
	expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
	setCookie(name, "", expdate);
}
	

</script>
</body>
</html>
