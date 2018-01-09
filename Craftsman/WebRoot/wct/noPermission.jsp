<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + path;
%>
<html>
    <head>
        <title>温馨提示</title>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta name="format-detection" content="telephone=no">
        <script type="text/javascript">
        /**
         * 禁用微信右上角分享按钮
         */
        function onBridgeReady(){
            WeixinJSBridge.call('hideOptionMenu');
        }
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }
        </script>
        <style type="text/css">
            .backImg{
                background-size:100% 100%;
                background-image: url(<%=basePath%>/resources/images/nopromission.jpg);
            }
        </style>
    </head>
     
    <body class="backImg">
    </body>
</html>