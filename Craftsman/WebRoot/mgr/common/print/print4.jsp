<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>打印销售小票</title>
<script src='http://localhost:8000/CLodopfuncs.js'></script>
<SCRIPT language=javascript src="/mgr/common/lib/LodopFuncs.js"></SCRIPT>
<style>
* {font-size: 10px;}
body {
	margin: 0px;
	padding: 5px;
}
table {
	margin: 0px;
	padding: 0px;
	border-collapse: collapse;
}
@media Print {
	.Noprn {
		display: none;
	}
}
</style>

</head>

<body>

	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>

	<div>
		<table>
			<tr>
				<td>
					<!-- <img src="../Images/ckk_jxw_80.png" style="width: 30px;" /> -->
				</td>
				<td colspan="" style="font-size: 18px; font-weight: bold; text-align: center; text-indent: 10px;">
					<s:property value="setting.shopName" />
				</td>
			</tr>
		</table>
		<table style="width:160px">
			<tr>
				<td colspan="3">=============================</td>
			</tr>
			<tr>
				<td colspan="3">
					<b>
					<s:if test="orderHead.orderType==7">扣次套餐/卡片</s:if>
					<s:else>消费项目</s:else>
					</b>
				</td>
			</tr>
			<tr>
				<td width="50%"><b>名称</b></td>
				<s:if test="orderHead.orderType==7">
				<td colspan="2"><b>次数</b></td>
				</s:if>
				<s:else>
				<td><b>原价</b></td>
				<td><b>折后价</b></td>
				</s:else>
			</tr>
			
			<s:set var="totalPrice" value="0" />
			<s:set var="projectSum" value="0" />
			<s:iterator value="#request.orderList" status="orderItem">
				<s:if test="goodsType==1||goodsType==3||goodsType==5||goodsType==6">
				<tr>
					<td><s:property value="goodsName" escape="false" /></td>
					<s:if test="orderHead.orderType==7">
					<td><s:property value="number" /></td>
					</s:if>
					<s:else>
					<td><s:property value="%{formatDouble(unitPrice)}" /></td>
					<td><s:property value="%{formatDouble(price)}" /></td>
					</s:else>
					<s:set var="projectSum" value="#projectSum + unitPrice*number" />
					<s:set var="totalPrice" value="#totalPrice + price" />
				</tr>
				</s:if>
			</s:iterator>
			<!-- 
			<tr>
				<td colspan="3">=============================</td>
			</tr>
			<tr>
				<td colspan="3"><b>产品消耗</b></td>
			</tr>
			<tr>
				<td><b>产品名</b></td>
				<td><b>数量</b></td>
				<td><b>单价</b></td>
			</tr>
			
			<s:set var="productSum" value="0" />
			<s:iterator value="#request.orderList" status="orderItem">
            	<s:if test="goodsType==2||goodsType==4">
            	<tr>
					<td style="width:120px;"><s:property value="goodsName" escape="false" /></td>
					<td align="center"><s:property value="number" /></td>
					<td><s:property value="unitPrice" /></td>
					<s:set var="productSum" value="#productSum + unitPrice * number" />
					<s:set var="totalPrice" value="#totalPrice + price" />
				</tr>
            	</s:if>
            </s:iterator>
			
			<tr>
				<td><b>产品价值：</b></td>
				<td>&nbsp;</td>
				<td><div id="cpzj"><s:property value="#productSum" /></div></td>
			</tr>
			 -->
			
			<tr>
				<td colspan="3">=============================</td>
			</tr>
			<tr>
				<td colspan="2"><b>消费原价：</b></td>
				<td><s:property value="%{formatDouble(#projectSum)}" /></td>
			</tr>
			<tr>
				<td colspan="2"><b>优惠金额：</b></td>
				<td><s:property value="%{formatDouble(orderHead.oprice - orderHead.aprice)}" /></td>
			</tr>
			<tr>
				<td colspan="2"><b>实收金额：</b></td>
				<td><s:property value="%{formatDouble(orderHead.aprice)}" /></td>
			</tr>
			<tr>
				<td colspan="3">=============================</td>
			</tr>
			<tr>
				<td colspan="3"></td>
			</tr>
		</table>
		<table style="TABLE-LAYOUT: fixed; width:160px">
			<tr>
				<td>单号：No.<s:property value="orderHead.orderId" /></td>
			</tr>
			<tr>
				<td>
					会员：<s:property value="member.name" escape="false" /><s:if test='member.sex==1'>先生</s:if><s:elseif test='member.sex==2'>女士</s:elseif>&nbsp;&nbsp;&nbsp;
					<s:property value="car.carShort" escape="false" /><s:property value="car.carCode" /><s:property value="car.carNumber" />
				</td>
			</tr>
			<tr>
				<td>余额：<s:property value="member.balance" />&nbsp;&nbsp;&nbsp;积分：<s:property value="member.point" /></td>
			</tr>
			<tr>
				<td>时间：<s:property value="orderHead.saleDate.substring(0,10)" /></td>
			</tr>
			<tr>
				<td>电话：<s:property value="setting.phone" /></td>
			</tr>
			<tr>
				<td style="WORD-WRAP: break-word">地址：<s:property value="setting.address" /></td>
			</tr>
			<tr>
				<td>请保留小票</td>
			</tr>
			<tr>
				<td>谢谢您的惠顾，期待您再次光临！</td>
			</tr>
		</table>
		<br />
		<div id="print_div">
			<input id="abc" class="Noprn" type="button" onclick="print()" value="立即打印" />&nbsp;&nbsp; 
			<input id="abc2" class="Noprn" type="button" onclick="preview()" value="打印预览" />&nbsp;&nbsp;
		</div>
	</div>
	<script>
		window.onload = function() {

			var eles = document.getElementsByName("cpsp");
			var zj = 0;
			for (var i = 0; i < eles.length; i++) {
				var value = eles[i].value.split(",");
				zj += parseFloat(value[0]) * parseInt(value[1]);
			}
			try {
				//document.getElementById("cpzj").innerHTML = zj + "元";
			} catch (es) {
			}
		}

		function print() {
			document.getElementById("print_div").style.display = "none";
			var LODOP = getLodop();
			LODOP.SET_PRINT_PAGESIZE(3, 480, 0, "");
			LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%",
					document.documentElement.innerHTML);
			LODOP.PRINTA();
			document.getElementById("print_div").style.display = "block";
			location.href = location.href;
		}

		function preview() {
			document.getElementById("print_div").style.display = "none";
			var LODOP = getLodop();
			LODOP.SET_PRINT_PAGESIZE(3, 480, 0, "");
			LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%",
					document.documentElement.innerHTML);
			LODOP.PREVIEW();
			document.getElementById("print_div").style.display = "block";
		}
	</script>

</body>
</html>