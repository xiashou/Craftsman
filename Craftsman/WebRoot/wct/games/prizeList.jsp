<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../authorization.jsp"%> <%-- --%>
<%@ include file="../include.jsp"%>
<%
	String activityCode = request.getParameter("activityCode");
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>奖品列表</title>
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
    		url:  '<%=basePath %>/open/wechat/biz/auth/queryMemberWechatActivityPartLottery.atc?sid=<%=sid %>',
    		data: {
    			'wechatActivityLottery.activityCode' : '<%=activityCode %>'
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
    			$.each(data.wechatActivityLotteryPartList, function(index, part){
    				redeemStatus = part.redeemStatus;
    				redeemStatusStr = "未兑奖";
    				flagStr =  "<div id='div" + part.id + "'  class='weui-form-preview__ft'>" + 
			        "<a class='weui-form-preview__btn weui-form-preview__btn_primary' href='javascript:redeemPrize(" + part.id + ");'>兑换奖品</a>" + 
				    "</div>";
    				if(redeemStatus == 1) {
    					redeemStatusStr="已兑奖";
    					flagStr = "";
    				}
    				
    				
    				var childrenDiv = $("<div class='weui-form-preview'>" + 
    				        "<div class='weui-form-preview__hd'>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>奖品等级</label>" + 
    				            "<em class='weui-form-preview__value'>" + part.levelName + "</em>" + 
    				        "</div>" + 
    				    "</div>" + 
    				    "<div class='weui-form-preview__bd'>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>奖品名称</label>" + 
    				            "<span class='weui-form-preview__value'>" + part.prizeDesc + "</span>" + 
    				        "</div>" + 
    				        "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>抽奖时间</label>" + 
    				            "<span class='weui-form-preview__value'>" +part.lotteryTime + "</span>" + 
    				        "</div>" + 
    				        "<div class='weui-form-preview__item'>" + 
					            "<label class='weui-form-preview__label'>兑奖状态</label>" + 
					            "<span id='span" + part.id + "' class='weui-form-preview__value'>" + redeemStatusStr + "</span>" + 
					        "</div>" + 
    				        /* "<div class='weui-form-preview__item'>" + 
    				            "<label class='weui-form-preview__label'>消费时间</label>" + 
    				            "<span class='weui-form-preview__value'>" + part.prizeLevel + "</span>" + 
    				        "</div>" +  */
    				    "</div>" + flagStr + 
    				"</div><br>");

    		    	childrenDiv.appendTo(mainDiv);
    			});
    		}
        });
        
    });
    
    function redeemPrize(partId) {
    	$.confirm("确认兑奖", function() {
    		  //点击确认后的回调函数
    		  $.ajax({
	    		type:'post',
	    		url:  '<%=basePath %>/open/wechat/biz/auth/redeemMemberWechatActivityPartLottery.atc?sid=<%=sid %>',
	    		data: {
	    			'wechatActivityLotteryPart.id' : partId
	    		},
	    		dataType: 'json',
	    		cache: false,
	    		success: function(data){
	    			$.alert("恭喜您，兑奖成功！");
	    			if(data.msg = 'success') {
	    				$("#div" + partId).remove();
	    				$("#span" + partId).html("已兑奖");
	    			}
	    		}
    		  });
 		  }, function() {
 		  //点击取消后的回调函数
 		  });
    }
	</script>
  </body>
</html>
