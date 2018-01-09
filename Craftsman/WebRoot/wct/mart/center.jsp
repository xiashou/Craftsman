<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/gg.css">
    <link rel="stylesheet" href="<%=basePath %>/wct/mart/css/center.css?v=4">
    <script src="<%=basePath %>/wct/mart/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">var memId = <%=member.getMemId()%>, type = <%=request.getParameter("type") == null ? 1 : request.getParameter("type")%>;</script>
    <title>我的订单-<s:property value="#session.title" escape="false" /></title>
</head>
<body>
<div class="main">
    <div class="user_box">
        <div class="user_info">
            <a href="javascript:void(0)" class="vip_data">
                <div class="head">
                    <img src="<%=member.getWehcatHead()%>" error="<%=basePath %>/wct/mart/images/0.jpg" alt="">
                </div>
                <div class="vip_right">
                    <p class="user_name">
                        <%=member.getWechatNick()%> <!-- <span class="open_vip">开通年卡会员</span> -->
                    </p>
                    <p class="vip_number">会员号:<span><%=member.getVipNo()%></span></p>
                    <p class="vip_number">积分:<span><%=member.getPoint()%></span></p>
                </div>
            </a>
        </div>
        <!--11111-->
        <div class="tjr_xg_tc">
            <span class="tjr_sp1">修改推荐人</span>
            <br/>
            <label>请输入推荐人会员号</label>
            <br/>
            <input  class="txt" type="text" name="tjrName" />
            <br/>
            <p><span class="tjr_sp2">取消</span><span>确定</span></p>

        </div>
        <!--2222-->
        <div class="user_order">
            <a class="dfk" href="/open/wechat/biz/auth/initMallOrders.atc?sid=<%=sid %>&status1=1">
                <p><s:property value="status1" /></p>
                待付款
            </a>
            <a class="dsh" href="/open/wechat/biz/auth/initMallOrders.atc?sid=<%=sid %>&status3=3">
                <p><s:property value="status3" /></p>
                待收货
            </a>
        </div>
    </div>
    <div class="kt_img">
        <img src="" alt="">
    </div>
    <ul class="item_list">
        <li class="item">
            <a class="item_order_link" href="/open/wechat/biz/auth/initMallOrders.atc?sid=<%=sid %>">
                <div class="item_content">
                    全部订单
                    <span>查看</span>
                </div>
            </a>
        </li>
        <!-- 
        <li class="item">
            <a class="item_er_link" href="javascript:void(0)">
                <div class="item_content">
                    我的地址
                    <span>点击编辑</span>
                </div>
            </a>
        </li>
         -->
    </ul>
    <footer>
	    <a href="/open/wechat/biz/auth/initMallIndex.atc?sid=<%=sid %>">首页</a>
	    <a href="/open/wechat/biz/auth/initMallCategory.atc?sid=<%=sid %>">分类</a>
	    <a href="/open/wechat/biz/auth/initMallCart.atc?sid=<%=sid %>">购物车</a>
	    <a href="#" class="active">我的</a>
	</footer>
</div>

</body>
</html>
