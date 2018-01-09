<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-服务记录</title>
    <%@ include file="../include.jsp"%>
    <link rel="stylesheet" href="/wct/dist/style/weui.css"/>
    <link rel="stylesheet" href="/wct/dist/style/css.css"/>
</head>

<body ontouchstart>
    <div class="weui-toptips weui-toptips_warn js_tooltips">错误提示</div>
    <div class="container" id="container">
    	<div class="page__bd" id="mainDiv">
    		<div class="weui-search-bar" id="searchBar">
	            <form class="weui-search-bar__form">
	                <div class="weui-search-bar__box">
	                    <i class="weui-icon-search"></i>
	                    <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required/>
	                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
	                </div>
	                <label class="weui-search-bar__label" id="searchText">
	                    <i class="weui-icon-search"></i>
	                    <span>搜索</span>
	                </label>
	            </form>
	            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
	        </div>
    	</div>
    </div>
	<script src="/wct/dist/lib/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">
    $(function(){
    	$.showLoading("正在加载...");
        var $searchBar = $('#searchBar'),
            $searchResult = $('#searchResult'),
            $searchText = $('#searchText'),
            $searchInput = $('#searchInput'),
            $searchClear = $('#searchClear'),
            $searchCancel = $('#searchCancel');

        function hideSearchResult(){
            $searchResult.hide();
            $searchInput.val('');
        }
        function cancelSearch(){
            hideSearchResult();
            $searchBar.removeClass('weui-search-bar_focusing');
            $searchText.show();
        }

        $searchText.on('click', function(){
            $searchBar.addClass('weui-search-bar_focusing');
            $searchInput.focus();
        });
        $searchInput
            .on('blur', function () {
                if(!this.value.length) cancelSearch();
            })
            .on('input', function(){
                if(this.value.length) {
                    $searchResult.show();
                } else {
                    $searchResult.hide();
                }
            })
        ;
        $searchClear.on('click', function(){
            hideSearchResult();
            $searchInput.focus();
        });
        $searchCancel.on('click', function(){
            cancelSearch();
            $searchInput.blur();
        });
        
        //加载内容
        var mainDiv = $("#mainDiv");
        $.ajax({
    		type:'post',
    		url:  '<%=basePath %>/open/wechat/biz/auth/queryCenterForHeadOrderInfo.atc?sid=<%=sid %>',
    		data: {
    			'orderHead.memId' : '<%=member.getMemId() %>',
    			'orderHead.deptCode' : '<%=member.getDeptCode() %>',
    			'start': 0,
    			'limit': 20
    		},
    		dataType: 'json',
    		cache: false,
    		success: function(data){
    			if(!data){
    				$(".ui-loading-block").removeClass("show");
    				return;
    			}
    			$.hideLoading();
    			//获取行项目信息
    			$.each(data.orderHeadList, function(index, head){
    				goodsName = "";
    				//$.ajax({
    		    	//	type:'post',
    		    	//	url:  '<%=basePath %>/open/wechat/biz/auth/queryCenterForItemOrderInfo.atc?sid=<%=sid %>',
    		    	//	data: {
    		    	//		'orderItem.orderId' : head.orderId
    		    	//	},
    		    	//	dataType: 'json',
    		    	//	cache: false,
    		    	//	async: false,
    		    	//	success: function(data){
    		    	//		$.each(data.orderItemList, function(index, item){
    		    	//			goodsName = item.goodsName;
    		    	//			return;
    		    	//		});
    		    	//	}
    				//});
					$.each(head.orderItemList, function(index, item){
	    				goodsName = item.goodsName;
	    				return;
	    			});
    				var childrenDiv = $("<div class='weui-form-preview'>" + 
    				        "<div class='weui-form-preview__hd'>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>消费金额</label>" + 
    				            "<em class='weui-form-preview__value'>¥" + head.aprice + "</em>" + 
    				        "</div>" + 
    				    "</div>" + 
    				    "<div class='weui-form-preview__bd'>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>消费项目</label>" + 
    				            "<span class='weui-form-preview__value'>" + goodsName + "</span>" + 
    				        "</div>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>车牌号码</label>" + 
    				            "<span class='weui-form-preview__value'>" + head.carNumber + "</span>" + 
    				        "</div>" + 
    				        "<div class='weui-form-preview__item'>" + 
					            "<label class='weui-form-preview__label'>支付方式</label>" + 
					            "<span class='weui-form-preview__value'>" + head.payType + "</span>" + 
					        "</div>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>消费时间</label>" + 
    				            "<span class='weui-form-preview__value'>" + head.saleDate + "</span>" + 
    				        "</div>" + 
    				    "</div>" + 
    				    "<div class='weui-form-preview__ft'>" + 
    				        "<a class='weui-form-preview__btn weui-form-preview__btn_primary' href='<%=basePath %>/wct/center/orderRecordDetail.jsp?sid=<%=sid %>&orderId=" + head.orderId + "'>查看详情</a>" + 
    				    "</div>" + 
    				"</div><br>");

    		    	childrenDiv.appendTo(mainDiv);
    			});
    		}
        });
        
    });
	</script>
  </body>
</html>
