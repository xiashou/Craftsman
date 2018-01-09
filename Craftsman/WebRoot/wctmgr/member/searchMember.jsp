<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>会员中心</title>
    <jsp:include page="/wctmgr/include.jsp" flush="true" />
    <script src="/wctmgr/dist/lib/Chart.min.js"></script>
</head>

<body ontouchstart>

	<div class="weui-panel weui-panel_access tsm-nomargintop">
		<div class="weui-panel__bd">
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="tsm-searchbar">
		      		<div class="title">输入车牌或手机号码后4位查询</div>
		      		<div class="input">
		      			<input id="keyword" type="text"><button id="search">查询</button>
		      		</div>
		      	</div>
      		</div>
	  	</div>
	</div>
	<div class="weui-flex tsm-flex-head">
	  	<div class="weui-flex__item">会员卡号</div>
		<div class="weui-flex__item">车牌</div>
	  	<div class="weui-flex__item">姓名</div>
	  	<div class="weui-flex__item">手机号</div>
	</div>
	<div id="data">
	</div>
	
<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
  	$(function() {
    	FastClick.attach(document.body);
    	
    	$("#search").click(function(){
    		loadMember($("#keyword").val());
    	});
  	});
  	
  	function loadMember(keyword){
  		$.ajax({
			url:'/boss/queryMember.atc',
		    type:'POST',
		    async:false,
		    data:{
		    	keyword: keyword
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success){
		    		$("#data").html('');
		    		if(data.memberList.length > 0) {
		    			$.each(data.memberList, function(n, value) {
			    			var div = $("<div class=\"weui-flex tsm-flex-item\"></div>")
			    			div.append("<div class=\"weui-flex__item\">" + value.vipNo + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + value.carShort + value.carCode + value.carNumber + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + value.name + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + value.mobile + "</div>");
			    			$("#data").append(div);
			    	  	});
		    		} else 
			    		$("#data").append("<div class=\"weui-flex tsm-flex-item\"><div class=\"weui-flex__item\">暂无数据</div></div>");
		    	}
		    }
		});
  	}
</script>
</body>
</html>
