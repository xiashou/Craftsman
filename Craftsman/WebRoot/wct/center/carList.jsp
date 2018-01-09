<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-车辆列表</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css?v=1"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="weui-cells__title title_bg">
   			<div class="text">我的车辆</div>
   		</div>
   		<div class="tsm-car-list" id="mainDiv">
   		</div>
   		<div class="weui-btn-area">
           	<a class="weui-btn weui-btn_primary" href='<%=basePath %>/wct/center/carEdit.jsp?sid=<%=sid %>' id="add">添加车辆</a>
       	</div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    $(function(){
  		$.ajax({
    		type:'post',
    		url:  '<%=basePath %>/open/wechat/biz/auth/queryCenterForMemberCarInfo.atc?sid=<%=sid %>',
    		data: {
    			'member.memId' : '<%=member.getMemId() %>'
    		},
    		dataType: 'json',
    		cache: false,
    		async: false,
    		success: function(data){
    			var mainDiv = $("#mainDiv");
    			$.each(data.memberCarList, function(index, item){
    				
       				var childrenDiv = $("<div id='div" + index + "' class='list' ><div class='tsm-left'><div class='title'><span class='number'>" +
       					"<a href='<%=basePath %>/open/wechat/biz/auth/initCenter.atc?sid=<%=sid %>&carId="+item.id+"'>" + item.carShort + item.carCode + item.carNumber + "</a>" +
       					"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='brand'>" + item.brandName + "</span></div>" +
       		   			"<div class='content brand'>" + (item.carSeries==null?'':item.carSeries) + "&nbsp;&nbsp;&nbsp;&nbsp;" + (item.carModel==null?'':item.carModel) + "</div></div>" +
       		   			"<div class='tsm-right'><div class='weui-cell__ft'>" +
       			        "<a href='<%=basePath %>/wct/center/carEdit.jsp?sid=<%=sid %>&type=edit&carId=" + item.id + "' class='weui-vcode-btn'>编辑</a>" +
       			        "</div><div class='weui-cell__ft'><a href='javascript:remove(" + index + ", " + item.id + ");' class='weui-vcode-btn'>删除</a>" +
       			        "</div></div></div>");
    				
       				childrenDiv.appendTo(mainDiv);
    			});
    			
    		}
		});
    });
    
    function remove(index, carId) {
    	$.ajax({
    	    type: 'POST',
 	   	    data: {
 	   	    	'car.id' : carId
 	 		},
    	    url: '/open/member/deleteCar.atc',
    	    success: function(data){
    	    	$("#div" + index).remove();
    	    	$.alert("删除成功");
    	    },
            error: function(xhr, type){
             	alert('Ajax error!')
            }
    	});
    }
  </script>
  </body>
</html>
