<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>
<%
	String orderId = request.getParameter("orderId");
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-服务详情</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd">
    		<div class="weui-form-preview">
	            <div class="weui-form-preview__hd">
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">消费金额</label>
	                    <em class="weui-form-preview__value" id="aprice"></em>
	                </div>
	            </div>
	            <div class="weui-form-preview__bd">
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">会员卡号</label>
	                    <span class="weui-form-preview__value" id="vipNo"></span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">车牌号码</label>
	                    <span class="weui-form-preview__value" id="carNumber"></span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">消费门店</label>
	                    <span class="weui-form-preview__value" id="deptCode"></span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">所得积分</label>
	                    <span class="weui-form-preview__value" id="point"></span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">消费时间</label>
	                    <span class="weui-form-preview__value" id="saleDate"></span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">服务人员</label>
	                    <span class="weui-form-preview__value" id="creator"></span>
	                </div>
	            </div>
        	</div>
    		<div class="weui-cells tsm-cells">
    			<div class="weui-panel__hd" id="mainDiv">消费项目列表</div>
	            <div class="weui-cell tsm-red">
	                <div class="weui-cell__bd">
	                    <p>总计</p>
	                </div>
	                <div class="weui-cell__ft tsm-red" id="opriceDiv">￥20.00</div>
	            </div>
	            <div class="weui-cell tsm-red">
	                <div class="weui-cell__bd">
	                    <p>优惠</p>
	                </div>
	                <div class="weui-cell__ft tsm-red" id="preferentialDiv">￥-20.00</div>
	            </div>
	            <div class="weui-cell tsm-red">
	                <div class="weui-cell__bd">
	                    <p>实收</p>
	                </div>
	                <div class="weui-cell__ft tsm-red" id="apriceDiv">￥20.00</div>
	            </div>
	        </div>
    	</div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
	</script>
  </body>
  <script type="text/javascript">
    $(function(){
  		$.ajax({
    		type:'post',
    		url:  '<%=basePath %>/open/wechat/biz/auth/queryCenterForHeadOrderInfo.atc?sid=<%=sid %>',
    		data: {
    			'orderHead.memId' : '<%=member.getMemId() %>',
    			'orderHead.deptCode' : '<%=member.getDeptCode() %>',
    			'start': 0,
    			'limit': 20,
    			'orderHead.orderId' : '<%=orderId %>'
    		},
    		dataType: 'json',
    		cache: false,
    		async: false,
    		success: function(data){
    			var mainDiv = $("#mainDiv");
    			$.each(data.orderHeadList, function(index, head){
    				$("#aprice").html("￥" + head.aprice);
    				$("#vipNo").html(head.vipNo);
    				$("#carNumber").html(head.carNumber);
    				$("#deptCode").html(head.deptName);
    				$("#point").html(head.point);
    				$("#saleDate").html(head.saleDate);
    				$("#creator").html(head.creator);
    				
    				//加载行项目内容
    				$.each(head.orderItemList, function(index, item){
    					var number = index + 1;
        				var childrenDiv = $("<div class='weui-cell'><div class='weui-cell__bd'><p>" + 
        				number + "、" + item.goodsName + "</p></div><div class='weui-cell__ft'>" + 
        				"￥" + item.price + "</div></div>");
        				childrenDiv.appendTo(mainDiv);
    				});
        			
    				//加载金额信息
    				/* var priceDiv = $("<div class='weui-cell tsm-red'><div class='weui-cell__bd'><p>总计</p></div><div class='weui-cell__ft tsm-red'>" + 
    					head.oprice	+ "</div></div><div class='weui-cell tsm-red'><div class='weui-cell__bd'><p>优惠</p></div><div class='weui-cell__ft tsm-red'>" + 
    					head.oprice - head.aprice+ "</div></div><div class='weui-cell tsm-red'><div class='weui-cell__bd'><p>实收</p></div><div class='weui-cell__ft tsm-red'>" + 
    					head.aprice + "</div></div>"); */
    				$("#opriceDiv").html(head.oprice);
    				$("#preferentialDiv").html(head.oprice - head.aprice);
    				$("#apriceDiv").html(head.aprice);
    			});
    			
    		}
		});
    });
  </script>
</html>
