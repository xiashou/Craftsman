<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="authorization.jsp"%>
<%@ include file="wechat_jssdk.jsp"%>


<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>一键救援</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>

<body>
	<div class="weui-cells weui-cells_form" id="mainDiv">
		
	</div>
	

	<script type="text/javascript">
	  	//根据会员获取对应门店信息（如对应门店公司下还有其他门店，一并获取）
	  	$.showLoading("加载中...");
	 	$.ajax({
		    type: 'POST',
		    dataType: 'json',
		    data: {
    			'member.memId' : '<%=member.getMemId() %>',
    			'member.deptCode' : '<%=member.getDeptCode() %>',
    			'member.companyId' : '<%=member.getCompanyId() %>',
    		},
    		async: false,
		    url: '/open/wechat/biz/auth/getSOSDeptInfoByMember.atc?sid=<%=sid %>',
		    success: function(data){
	   			$.hideLoading();
		    	if(data.success) {
		    		$.each(data.deptList, function(index, dept){
		    			var childrenDiv = $('<div class="weui-cell">' +
				    			'<div class="weui-cell__hd">' +
								'<label class="weui-label">' + dept.deptName + '</label>' +
							'</div>' +
						'</div>' +
						'<div class="weui-cell">' +
							'<div class="weui-cell__hd">' +
								'<img src="/upload/system/20170328230251686.jpg" width="70px" style="margin-right: 20px">' +
							'</div>' +
							'<div class="weui-cell__bd">' +
								'<p>' + dept.address + '</p>' +
								'<p>' + dept.phoneNumber + '</p>' +
							'</div>' +
							'<div class="weui-cell__ft">说明文字</div>' +
						'</div>' +
						'<div style="padding: 15px;">' +
							'<a href="javascript:sendLocation(\'' + dept.deptCode + '\');" class="weui-btn weui-btn_primary">发送位置</a>' +
						'</div>'); 

	    		    	childrenDiv.appendTo(mainDiv);
		    		});
		    /* 		$("#shopRemark").html(data.settingInfo.shopRemark);
		    		$("#shopName").html(data.settingInfo.shopName);
		    		$("#phone").html(data.settingInfo.phone);
		    		$("#address").html(data.settingInfo.address); */
		    	}
		    },
	        error: function(xhr, type){
	         	alert('Ajax error!')
	        }
		    
		});
	 	
	 	 //等待微信JS初始化完成
	    $.showLoading("初始化位置中...");
	    wx.ready(function(){
	    	$.hideLoading();
	    });
	 	
	 	function sendLocation(deptCode) {
	 		$.showLoading("正在发送位置信息...");
	 		wx.getLocation({
			    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			    success: function (res) {
			        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
			        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
			        var speed = res.speed; // 速度，以米/每秒计
			        var accuracy = res.accuracy; // 位置精度
			        $.ajax({
					    type: 'POST',
					    dataType: 'json',
					    data: {
			    			'wechatSOSLocation.deptCode' : deptCode,
			    			'wechatSOSLocation.latitude' : latitude,
			    			'wechatSOSLocation.longitude' : longitude,
			    			'wechatSOSLocation.speed' : speed,
			    			'wechatSOSLocation.accuracy' : accuracy,
			    		},
					    url: '/open/wechat/biz/auth/sendSOSLocation.atc?sid=<%=sid %>',
					    success: function(data){
					    	if(data.success) {
					    		$.alert("发送成功！")
					    	}else {
					    		$.alert(data.msg)
					    	}
					    	$.hideLoading();
					    },
			        });
			    }
			});
	 	}
</script>
</body>
</html>
