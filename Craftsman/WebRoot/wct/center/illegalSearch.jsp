<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-违章查询</title>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-cells__title title_bg">
    			<div class="text">违章查询</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">车牌号</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入车牌号"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">车架号码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入车架号码后6位"/>
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">发动机号</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入发动机号后4位"/>
	                </div>
	            </div>
	        </div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:" id="showTooltips">确定</a>
	        </div>
    	</div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
	    
	</script>
  </body>
</html>
