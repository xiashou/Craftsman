<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>百度地图API自定义地图</title>
<!--引用百度地图API-->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=VVfQNAbez2R0LFLKaxox2UiY3Sy142os"></script>
</head>

<body>
	<!--百度地图容器-->
	<div style="width:470px;height:440px;border:#ccc solid 1px;font-size:12px" id="map"></div>
	<!--<a href="http://api.map.baidu.com/marker?location=39.916979519873,116.41004950566&title=目的位置&content=百度奎科大厦&output=html">adsasdasdasdasdasdasdasd</a>-->
</body>
<script type="text/javascript">
	//创建和初始化地图函数：
	function initMap() {
		createMap();//创建地图
		setMapEvent();//设置地图事件
		addMapControl();//向地图添加控件
	}
	function createMap() {
		map = new BMap.Map("map");
		map.centerAndZoom(new BMap.Point(114.03833, 22.670828), 12);
		
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				mk = new BMap.Marker(r.point);
				mk.addEventListener("click",attribute);
				mk.enableDragging();
				map.addOverlay(mk);
				map.panTo(r.point);
				//alert('您的位置：'+r.point.lng+','+r.point.lat);
			}
			else {
				alert('failed'+this.getStatus());
			}        
		},{enableHighAccuracy: true})
		
		
	}
	function setMapEvent() {
		map.enableScrollWheelZoom();
		map.enableKeyboard();
		map.enableDragging();
		map.enableDoubleClickZoom()
	}
	//向地图添加控件
	function addMapControl() {
		var scaleControl = new BMap.ScaleControl({
			anchor : BMAP_ANCHOR_BOTTOM_LEFT
		});
		scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
		map.addControl(scaleControl);
		var navControl = new BMap.NavigationControl({
			anchor : BMAP_ANCHOR_TOP_LEFT,
			type : BMAP_NAVIGATION_CONTROL_LARGE
		});
		map.addControl(navControl);
		var overviewControl = new BMap.OverviewMapControl({
			anchor : BMAP_ANCHOR_BOTTOM_RIGHT,
			isOpen : true
		});
	}
	function attribute(){
		var textxmap = parent.document.getElementById("xlocation-inputEl");
		var textymap = parent.document.getElementById("ylocation-inputEl");
		if(textxmap && textymap){
			var p = mk.getPosition();  //获取marker的位置
			textxmap.value = p.lng;
			textymap.value = p.lat;
		} else
			alert("获取父窗口控件出错！");
	}
	
	var map, mk;
	initMap();
</script>
</html>