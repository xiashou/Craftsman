<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="authorization.jsp"%>
<%@ include file="include.jsp"%>

<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联系我们</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="/wct/dist/style/bootstrap.min.css">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=VVfQNAbez2R0LFLKaxox2UiY3Sy142os"></script>
</head>

<body>
<div style="margin:10px;">
    <div class="panel panel-info">
        <div class="panel-heading">
            智能工匠
        </div>
        <div class="panel-body" id="shopRemark">
            
        </div>

        <div class="panel-heading">
            联系方式
        </div>
        <ul class="list-group">
            <li class="list-group-item">
                <span class="badge" id="shopName" style="background-color:#fff;color:#000;font-size:14px;"></span>
                <span class="glyphicon" style="font-size:16px;"><img src="/wct/dist/img/info.png"></span>
            </li>
            <li class="list-group-item">
                <span class="badge" id="phone" style="background-color:#fff;color:#000;font-size:14px;"></span>
                <span class="glyphicon" style="font-size:16px;"><img src="/wct/dist/img/phone.png"></span>
            </li>
            <li class="list-group-item">
                <span class="badge" id="address" style="background-color:#fff;color:#000;font-size:14px;"></span>
                <span class="glyphicon" style="font-size:16px;"><img src="/wct/dist/img/addr.png"></span>
            </li>
            <li class="list-group-item">
            	<a id="location_link" href="#">
                <div style="width:100%;height:300px;border:#ccc solid 1px;font-size:12px" id="map"></div>
                </a>
            </li>
        </ul>

    </div>
</div>
<script type="text/javascript">
	  	//根据车辆ID获取车辆信息
	  	var map, p1, p2, shopName;
	  	$.showLoading("加载中...");
	 	$.ajax({
		    type: 'POST',
		    dataType: 'json',
	   	    data: {
	 		},
		    url: '/open/wechat/biz/auth/initContactInfo.atc?sid=<%=sid %>',
		    success: function(data){
	   			$.hideLoading();
		    	if(data.success) {
		    		$("#shopRemark").html(data.settingInfo.shopRemark);
		    		$("#shopName").html(data.settingInfo.shopName);
		    		$("#phone").html(data.settingInfo.phone);
		    		$("#address").html(data.settingInfo.address);
		    		
		    		if(data.settingInfo.xlocation && data.settingInfo.ylocation){
			    		initMap(data.settingInfo.xlocation, data.settingInfo.ylocation);
		    		}
		    	}
		    },
	        error: function(xhr, type){
	         	alert('Ajax error!')
	        }
		});
	 	
	 	//创建和初始化地图函数：
		function initMap(a, b) {
			createMap(a, b);//创建地图
			setMapEvent();//设置地图事件
			addMapControl();//向地图添加控件
		}
		function createMap(x, y) {
			map = new BMap.Map("map");
			map.centerAndZoom(new BMap.Point(x, y), 15);
			p2 = new BMap.Marker(new BMap.Point(x, y)); // 创建点
    		map.addOverlay(p2);            //增加点
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
</script>
</body>
</html>
