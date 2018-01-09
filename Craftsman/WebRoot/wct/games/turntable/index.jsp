<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + path;
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>签到送红包</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/wct/games/turntable/css/reset.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/wct/games/turntable/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/wct/games/turntable/css/function.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/wct/games/turntable/css/style.css">

	<script type="text/javascript" src="<%=basePath %>/wct/games/turntable/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/wct/games/turntable/js/jquery.SuperSlide.2.1.1.source.js"></script>
	<script type="text/javascript" src="<%=basePath %>/wct/games/turntable/js/html5.js"></script>
	<script type="text/javascript" src="<%=basePath %>/wct/games/turntable/js/public.js"></script>
	<script type="text/javascript" src="<%=basePath %>/wct/games/turntable/js/app.js"></script>
</head>
<body class="g-body-in">
	<div class="g-wrap">
		<section class="h15"></section>
		<section class="main-sec pt5 main-wheel">
			<div class="big-border" style="height: 331.219px; transform: rotate(14500deg);">
				<div class="small-border g9" style="height: 332px;">

					<div class="shan">
						<span>1元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>2元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>3元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>4元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>
					
					<div class="shan">
						<span>5元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>6元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>7元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>8元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>

					<div class="shan">
						<span>9元现金</span>
						<img src="<%=basePath %>/wct/games/turntable/images/monery.png" width="30%">
					</div>
					
					<img src="<%=basePath %>/wct/games/turntable/images/middle.png" width="50%" class="middle" style="transform: rotate(-14500deg);">
				</div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 0.5%;"></div>
				<div class="stars" style="left: 70%; top: 6%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 84.5%; top: 18%;"></div>
				<div class="stars" style="left: 92.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 95.5%; top: 50%;"></div>
				<div class="stars" style="left: 91%; top: 68%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 81.5%; top: 81.5%;"></div>
				<div class="stars" style="left: 68%; top: 91%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 95.5%;"></div>
				<div class="stars" style="left: 32%; top: 92.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 16%; top: 83%;"></div>
				<div class="stars" style="left: 6%; top: 70%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 0.5%; top: 50%;"></div>
				<div class="stars" style="left: 3.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 14%; top: 15%;"></div>
				<div class="stars" style="left: 27%; top: 5.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 0.5%;"></div>
				<div class="stars" style="left: 70%; top: 6%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 84.5%; top: 18%;"></div>
				<div class="stars" style="left: 92.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 95.5%; top: 50%;"></div>
				<div class="stars" style="left: 91%; top: 68%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 81.5%; top: 81.5%;"></div>
				<div class="stars" style="left: 68%; top: 91%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 95.5%;"></div>
				<div class="stars" style="left: 32%; top: 92.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 16%; top: 83%;"></div>
				<div class="stars" style="left: 6%; top: 70%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 0.5%; top: 50%;"></div>
				<div class="stars" style="left: 3.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 14%; top: 15%;"></div>
				<div class="stars" style="left: 27%; top: 5.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 0.5%;"></div>
				<div class="stars" style="left: 70%; top: 6%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 84.5%; top: 18%;"></div>
				<div class="stars" style="left: 92.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 95.5%; top: 50%;"></div>
				<div class="stars" style="left: 91%; top: 68%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 81.5%; top: 81.5%;"></div>
				<div class="stars" style="left: 68%; top: 91%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 95.5%;"></div>
				<div class="stars" style="left: 32%; top: 92.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 16%; top: 83%;"></div>
				<div class="stars" style="left: 6%; top: 70%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 0.5%; top: 50%;"></div>
				<div class="stars" style="left: 3.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 14%; top: 15%;"></div>
				<div class="stars" style="left: 27%; top: 5.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 0.5%;"></div>
				<div class="stars" style="left: 70%; top: 6%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 84.5%; top: 18%;"></div>
				<div class="stars" style="left: 92.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 95.5%; top: 50%;"></div>
				<div class="stars" style="left: 91%; top: 68%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 81.5%; top: 81.5%;"></div>
				<div class="stars" style="left: 68%; top: 91%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 50%; top: 95.5%;"></div>
				<div class="stars" style="left: 32%; top: 92.5%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 16%; top: 83%;"></div>
				<div class="stars" style="left: 6%; top: 70%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 0.5%; top: 50%;"></div>
				<div class="stars" style="left: 3.5%; top: 32%;"></div>
				<div class="stars" style="box-shadow: rgb(255, 255, 255) 0px 0px 5px; left: 14%; top: 15%;"></div>
				<div class="stars" style="left: 27%; top: 5.5%;"></div>
			</div>
		</section>

		<section class="main-sec">
			<div class="g-num">您还有<em>1</em>次抽奖机会</div>
		</section>

		<section class="main-sec loptop">
			<div class="m-title"><h3>有谁在抢</h3></div>
			<div class="tempWrap" style="overflow:hidden; position:relative; height:291px">
				<dl class="peolist" style="height: 873px; position: relative; padding: 0px; margin: 0px; top: -291px;">
					<dd class="clone" style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏跪谢老板</p>
						</div>
					</dd>
					<dd class="clone" style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏</p>
						</div>
					</dd>
					<dd class="clone" style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏</p>
						</div>
					</dd>
					<dd style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏跪谢老板</p>
						</div>
					</dd>
					<dd style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏</p>
						</div>
					</dd>
					<dd style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏</p>
						</div>
					</dd>
					<dd class="clone" style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏跪谢老板</p>
						</div>
					</dd>
					<dd class="clone" style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏</p>
						</div>
					</dd>
					<dd class="clone" style="height: 68px;">
						<img src="<%=basePath %>/wct/games/turntable/images/facepic.jpg" width="20%">
						<div class="right">
							<span><h2>雨中漫步</h2><em>7月1日 21:25</em></span>
							<p>跪谢老板打赏</p>
						</div>
					</dd>
				</dl>
			</div>
		</section>

		<script type="text/javascript">
			jQuery(".loptop").slide({mainCell:"dl",autoPage:true,effect:'topLoop',autoPlay:true,scroll:3,vis:3,easing:'swing',delayTime:500, interTime:3000, pnLoop:true});
		</script>

		<section class="main-sec">
			<div class="m-title"><h3>活动说明</h3></div>
			<div class="einfo">
				<p>活动时间：7月1日-7月30日</p>
				<p>每日签到可获1次抽奖机会</p>
				<p>每日邀请好友参与可获1次抽奖机会</p>
			</div>
		</section>

		<section class="main-sec">
			<footer>智能工匠,提供技术支持</footer>
		</section>
	</div>

	<div class="dialog gz">
		<div class="d-main">
			<p>请进入"镇江微生活"公众号立即参与</p>
			<div class="btn-w">
				<a class="btn" href="http://www.17sucai.com/preview/9786/2016-02-01/wheel-master/index.html#">进入</a>
				<a class="btn false" href="javascript:void(0);">取消</a>
			</div>
		</div>
	</div>

	<div class="dialog info">
		<div class="d-main">
			<p>次数用光,金额不足 之类的 信息提示表单</p>
		</div>
	</div>
	
	
	<div class="dialog theForm" style="display: none;">
		<div class="d-main">
			<p>恭喜你,抽奖成功,获得了8元现金,请注意查收</p>
			<div class="btn-w">
				<a class="btn btn-lang close" href="javascript:;">确定</a>
			</div>
		</div>
	</div>
	
	
	<div class="dialog again">
		<div class="d-main">
			<p>再来一次</p>
			<div class="btn-w">
				<a class="btn close" href="javascript:;">再来一次</a>
				<a class="btn false" href="javascript:;">取消</a>
			</div>
		</div>
	</div>

<script type="text/javascript">
	var valueJson = {
		'wheelBody' : $('.big-border'), //转盘主体
		'wheelSmall' : $('.small-border'), //转盘内部
		'starsNum' : 16, //转盘边缘小黄点个数

		'starsPostion' : [[50, 0.5], [70, 6], [84.5, 18], [92.5, 32], [95.5, 50], [91, 68], [81.5, 81.5], [68, 91], [50, 95.5], [32, 92.5], [16, 83], [6, 70], [0.5, 50], [3.5, 32], [14, 15], [27, 5.5]], //小圆点坐标
		'actionRan' : 7200, //转盘转动弧度
		'theOnce' : 0, //初始化转盘第一个
		'startBtn' : $('.middle'), //开始按钮

		//需要后台传值的参数
		'clickAjaxUrl' : 'www.baidu.com', //点击抽奖获取信息的交互的ajax
		'is_gz' : 1, //是否开启关注 1开 2 关
		'is_follow' : 1 //是否关注

 	};
	indexApp.init(valueJson).wheelStart(); //应用开始
</script>
</body></html>