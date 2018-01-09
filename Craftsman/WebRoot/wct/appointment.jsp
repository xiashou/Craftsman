<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>

<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>一键预约</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>

<body>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">预约门店</label>
			</div>
			<div class="weui-cell__bd">
				<select id="store" class="weui-input">
					<option selected>门店1</option>
					<option>门店2</option>
				</select>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">预约时间</label>
			</div>
			<div class="weui-cell__bd">
				<div class="weui-cell__bd">
			    	<input class="weui-input" type="datetime-local" value="" placeholder="请选择预约时间">
			    </div>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea" placeholder="预约内容" rows="3"></textarea>
					<div class="weui-textarea-counter">
						<span>0</span>/200
					</div>
				</div>
			</div>
		</div>
	</div>
		<div style="padding: 15px;">
			<a href="javascript:;" class="weui-btn weui-btn_primary">预约</a>
		</div>

	<script>
		
	</script>
</body>
</html>
