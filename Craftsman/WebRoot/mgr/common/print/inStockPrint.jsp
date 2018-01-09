<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>打印入库单</title>
    <script src='http://localhost:8000/CLodopfuncs.js'></script>
    <script language=javascript src="/mgr/common/lib/jquery-1.7.2.min.js"></script>
	<script language=javascript src="/mgr/common/lib/LodopFuncs.js"></script>
	<style>
        *{font-size: 14px;}
        .div_main{text-align: center;}
        table{width: 90%; margin: 8px auto;}
        table td, th{padding: 5px 3px;}
        #view{border-right: 1px solid #000;border-bottom:1px solid #000}
        #view th{border-left:1px solid #000;border-top:1px solid #000}
        #view td{border-left:1px solid #000;border-top:1px solid #000}
        
    </style>
	
  </head>

<body>
	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>
	<div id="div_main">
		<table>
	        <tr>
	            <th ><div style="font-size:25px; height:40px"><s:property value="setting.shopName" /></div></th>
	        </tr>
	        <tr>
	            <th><div style="font-size:16px;">采购入库单</div></th>
	        </tr>
	    </table>
	    <table style="margin-top:8px;">
	        <tr>
	            <td width="25%">入库日期：<s:property value="inNumber.substring(0,4)" />年<s:property value="inNumber.substring(4,6)" />月<s:property value="inNumber.substring(6,8)" />日</td>
	            <td width="25%"></td>
	            <td width="25%"></td>
	            <td width="25%"></td>
	        </tr>
	    </table>
	    <table id="view" cellpadding="0" cellspacing="0" >
	        <tr>
	            <th style="text-align:center; width:10%;">序号</th>
                <th style="width:30%">名称</th>
                <th style="width:20%">供应商</th>
                <th style="width:10%">数量</th>
                <th style="width:15%">单价</th>
                <th style="width:15%">总价</th>
	        </tr>
    		<s:set var="totalNumber" value="0" />
	        <s:set var="totalPrice" value="0" />
	        <s:iterator value="#request.inStockList" var="inStock" status="index">
	        <tr>
	        	<td style="text-align:center;"><s:property value="#index.index + 1" /></td>
	        	<td><s:property value="goodsName" /></td>
	        	<td><s:property value="supplierName" /></td>
	        	<td style="text-align:center;"><s:property value="number" /></td>
	        	<td style="text-align:center;"><s:property value="%{formatDouble(inPrice)}" /></td>
	        	<td style="text-align:center;"><s:property value="%{formatDouble(number * inPrice)}" /></td>
	        </tr>
	        <s:set var="totalNumber" value="#totalNumber + number" />
	        <s:set var="totalPrice" value="#totalPrice + number * inPrice" />
	        </s:iterator>
	    </table>
	    <table cellpadding="0" cellspacing="0" style="margin-top:5px">
	        <tr>
	            <th style="text-align:left; width:10%;">合计</th>
                <th style="width:30%"></th>
                <th style="width:20%"></th>
                <th style="width:10%">数量：<s:property value="#totalNumber" /></th>
                <th style="width:15%"></th>
                <th style="width:15%">金额：<s:property value="%{formatDouble(#totalPrice)}" /></th>
	        </tr>
	    </table>
	    <table cellpadding="0" cellspacing="0" style="margin-top:5px">
	        <tr>
	            <th style="text-align:left; width:10%;">录入员：</th>
                <th style="width:30%"></th>
                <th style="width:20%">采购员：</th>
                <th style="width:10%"></th>
                <th style="width:15%">验收员：</th>
                <th style="width:15%"></th>
	        </tr>
	    </table>
	    <div id="print_div" style="width: 90%; margin: 100px auto">
	        <input id="abc" class="Noprn" type="button" onclick="print()" value="立即打印" />&nbsp;
	        <input id="abc2" class="Noprn" type="button" onclick="preview()" value="打印预览" />&nbsp;
	    </div>
	</div>
	
<script type="text/javascript">
function print() {
    document.getElementById("print_div").style.display = "none";
    var LODOP = getLodop();
    //LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
    LODOP.ADD_PRINT_HTM(20, 20, "100%", "100%", "<!DOCTYPE>" + document.documentElement.innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "TableRowThickNess", 25);
    LODOP.PRINTA();
    document.getElementById("print_div").style.display = "block";
}
function preview() {
    document.getElementById("print_div").style.display = "none";
    var LODOP = getLodop();
    //LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
    LODOP.ADD_PRINT_HTM(20, 20, "100%", "100%", "<!DOCTYPE>" + document.documentElement.innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "TableRowThickNess", 25);
    LODOP.PREVIEW();
    document.getElementById("print_div").style.display = "block";
}
</script>
</body>
</html>
