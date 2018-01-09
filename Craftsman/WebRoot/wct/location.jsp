<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>一键导航</title>
    <%@ include file="wechat_jssdk.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <script type="text/javascript">
    $(function(){
		wx.ready(function(){
			wx.openLocation({
				latitude: 23.099994,
				longitude: 113.324520,
			    name: 'TIT 创意园',
			    address: '广州市海珠区新港中路 397 号',
			    scale: 14,
			    infoUrl: 'http://weixin.qq.com'
			});
		});
	});
	</script>
  </body>
</html>
