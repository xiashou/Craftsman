<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head id="Head1">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>公众账号授权</title>
    <style>
        .main_nav{width: 700px; margin: 0 auto;}
        .main_nav input, .main_nav textarea {
		    padding: 5px;
		    border: 1px solid #ccc;
		    border-radius: 3px;
		    margin-bottom: 10px;
		    width: 80%;
		    box-sizing: border-box;
		    color: #2C3E50;
		    font-size: 16px;
		}
    </style>
    <script>
        function check() {
        	v = document.getElementById("txtSms").value;
            if (v == ""||v.length<3||v.length>8) {
                $.alert("店铺短信签名不能为空，且长度为3到8位！");
                return false;
            }
        	v = document.getElementById("txtSignCode").value;
            if (v == "") {
                $.alert("授权码不能为空！");
                return false;
            }
            v = document.getElementById("txtSms").value;
            if (v == ""||v.length<3||v.length>8) {
            	$.alert("店铺短信签名不能为空，且长度为3到8位！");
                return false;
            }
            $.confirm("确认资料填写无误吗？", function() {
            	  //点击确认后的回调函数
            	  $("#form1").attr("action","<%=basePath %>/open/wechat/sys/authorization.atc?sid=" + document.getElementById("txtSignCode").value);
            	  $("#form1").submit();
            	  }, function() {
            	  //点击取消后的回调函数
            	  });
        }
    </script>
</head>
<body style="overflow: auto;">
    <form method="post" action="" id="form1">

    <input name="lat" type="hidden" id="lat" />
    <input name="lng" type="hidden" id="lng" />
    <input name="pro" type="hidden" id="pro" />
    <input name="city" type="hidden" id="city" />
    <input name="area" type="hidden" id="area" />
    <input name="address" type="hidden" id="address" />

    <div class="main_nav">
        <div class="nav_content">
        <div style="font-size:24px;font-weight:bold;height:50px;line-height:50px;font-family:微软雅黑;">填写商户基本资料</div>

        <div class="text_name">短信签名：</div>
        <div class="text_inputs"><input name="wechatAuthorizerParams.msgSignature" type="text" id="txtSms" style="width: 200px"/>&nbsp;长度为3-8位，用于短信备案，例：【智能工匠】</div>
        <div class="text_desc"></div>

        <div class="text_name">授权码：</div>
        <div class="text_inputs"><input name="wechatAuthorizerParams.sid" type="text" id="txtSignCode" /></div>
        <div class="text_desc"></div>

        <div class="text_name">微信密钥：</div>
        <div class="text_inputs"><input name="wechatAuthorizerParams.authorizerAppSecret" type="text" id="txtWeSecret" /></div>
        <div class="text_desc"></div>

        <a href="mpUpload.jsp" target="_blank" style="color:blue;font-size:16px;">点击前往上传域名配置文件>></a>
        <div class="text_name"></div>
        <div class="text_inputs" style="width:550px;">
            <input type="button" name="Button1" value="前往绑定微信公众号  >>" onclick="return check();" id="Button1" style="border:1px solid #169d12;background-color:#06be04;color:#fff;font-weight:bold;font-size:14px;text-align:center;margin-top:20px;width:200px;cursor:pointer;" />&nbsp;&nbsp;
        </div>
        </div>
    </div>

    </form>
</body>
</html>