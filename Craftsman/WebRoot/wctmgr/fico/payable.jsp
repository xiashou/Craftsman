<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>应付账款</title>
    <jsp:include page="/wctmgr/include.jsp" flush="true" />
</head>

<body ontouchstart>

    <div class="weui-search-bar">
    	<div class="tsm-link"></div>
    	<div class="tsm-shopsel">
    		<select id="shop_picker" onchange="changeSelect();" >
    			<option value="">所有</option>
    			<s:iterator value="deptList" var="dept" >
    				<option value="<s:property value="deptCode" />"><s:property value="deptName" /></option>
    			</s:iterator>
    		</select>
    	</div>
	</div>
	
	<div class="weui-panel weui-panel_access tsm-nomargintop">
		<div class="weui-panel__bd">
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="today">
		      		<div class="title">总应付金额</div>
		      		<div class="data">¥<s:property value="%{formatDouble(rList[0])}" /></div>
		      	</div>
		      	<div class="yesterday">
		      		<div class="title">总还款金额</div>
		      		<div class="data">¥<s:property value="%{formatDouble(rList[1])}" /></div>
		      	</div>
      		</div>
      		<div class="weui-media-box__bd tsm-panelhead">
		      	<div class="today last">
		      		<div class="title">总剩余未付金额</div>
		      		<div class="data">¥<s:property value="%{formatDouble(rList[2])}" /></div>
		      	</div>
      		</div>
	  	</div>
	</div>
	
	<div class="weui-flex tsm-flex-head">
		<div class="weui-flex__item">供应商</div>
	  	<div class="weui-flex__item">总应付</div>
	  	<div class="weui-flex__item">总还款</div>
	</div>
	<div id="data">
	<s:iterator value="payList" var="pay">
		<div class="weui-flex tsm-flex-item">
			<div class="weui-flex__item"><s:property value="name" /></div>
		  	<div class="weui-flex__item"><s:property value="%{formatDouble(payable)}" /></div>
		  	<div class="weui-flex__item"><s:property value="%{formatDouble(repayment)}" /></div>
		</div>
	</s:iterator>
	</div>

<script src="/wct/jquery-weui/dist/lib/fastclick.js"></script>
<script>
  	$(function() {
    	FastClick.attach(document.body);
  	});
  	
  	function changeSelect() {
  		$.showLoading();
  		loadPayable($("#shop_picker").val());
  		$.hideLoading();
  	}
  	
  	function loadPayable(deptCode){
  		$.ajax({
			url:'/boss/queryPayable.atc',
		    type:'POST',
		    data:{
		    	companyId: AA,
		    	deptCode: deptCode
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && data.success){
		    		$("#data").html('');
		    		if(data.rList.length > 0) {
		    			$(".today .data").html("¥" + Number(data.rList[0]).toFixed(2));
		    			$(".yesterday .data").html("¥" + Number(data.rList[1]).toFixed(2));
		    			$(".last .data").html("¥" + Number(data.rList[2]).toFixed(2));
		    		}
		    		if(data.payList.length > 0) {
		    			$.each(data.payList, function(n, value) {
			    			var div = $("<div class=\"weui-flex tsm-flex-item\"></div>")
			    			div.append("<div class=\"weui-flex__item\">" + value.name + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + Number(value.payable).toFixed(2) + "</div>");
			    			div.append("<div class=\"weui-flex__item\">" + Number(value.repayment).toFixed(2) + "</div>");
			    			$("#data").append(div);
			    	  	});
		    		}
		    	}
		    }
		});
  	}
</script>
</body>
</html>
