<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印销售单</title>
    <script src='http://localhost:8000/CLodopfuncs.js'></script>
    <SCRIPT language=javascript src="/mgr/common/lib/jquery-1.7.2.min.js"></SCRIPT>
	<SCRIPT language=javascript src="/mgr/common/lib/LodopFuncs.js"></SCRIPT>
	<style>
        *{font-size: 14px;}
         table{width:96%;}
         table td,th{padding:5px;}
          .table1{border-collapse:collapse; }
         .table1 td,.table1 th{border:1px solid #000;}
         .table2{border-collapse:collapse;}
         .table2 td,.table2 th{border:1px solid #000; text-align:center;}
         div[name='btnDel']{background: url(/resources/images/icons/delete.png);background-position:center; background-size:25px 25px; width:22px; height:22px; float:left;border-radius:14px; position:absolute; top:2px;}
         div[name='btnAdd']{background: url(/resources/images/icons/add.png); background-position:center; background-size:25px 25px; width:22px; height:22px; float:left;border-radius:14px; position:absolute; top:2px; }
    </style>
  </head>

<body>
	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>

	<div id="div_main">
    
        <table>
	        <tr>
	            <th ><div contenteditable="true" style="font-size:25px;"><s:property value="setting.shopName" /></div></th>
	        </tr>
	        <tr>
	            <th><div contenteditable="true" style="font-size:16px;">结算单</div></th>
	        </tr>
	    </table>
	    <table style="margin-top:10px;">
	        <tr>
	            <td width="25%"><div contenteditable="true">项目单号：<s:property value="orderHead.orderId" /></td>
	            <td width="25%" align="center"><div contenteditable="true">接车人员：</div></td>
	            <td width="25%" align="center"><div contenteditable="true">进厂公里：Km</div></td>
	            <td width="25%" align="right"><div contenteditable="true">进厂日期：</div></td>
	        </tr>
	    </table>
	    <table style="margin-top:5px;" class="table1">
	        <tr>
	            <td colspan="2"><div contenteditable="true"><b>联系地址：</b><s:property value="setting.address" /></div></td>
	            <td><div contenteditable="true"><b>联系电话：</b><s:property value="setting.phone" /></div></td>
	        </tr>
	        <tr>
	            <td><div contenteditable="true"><b>车牌号码：</b><s:property value="car.carShort" escape="false" /><s:property value="car.carCode" /><s:property value="car.carNumber" /></div></td>
	            <td><div contenteditable="true"><b>车辆型号：</b><s:property value="car.brandName" escape="false" />/</div></td>
	            <td><div contenteditable="true"><b>车架号码：</b><s:property value="car.carFrame" /></div></td>
	        </tr>
	        <tr>
	            <td><div contenteditable="true"><b>联系车主：</b><s:property value="member.name" escape="false" /><s:if test='member.sex==1'>先生</s:if><s:elseif test='member.sex==2'>女士</s:elseif></div></td>
	            <td><div contenteditable="true"><b>联系电话：</b><s:property value="member.mobile" /></div></td>
	            <td><div contenteditable="true"><b>会员编号：</b><s:property value="member.vipNo" /></div></td>
	        </tr>
	
	    </table>

        <table  cellpadding="0" cellspacing="0" style="width:100%" >
	        <tr>
	            <td style=" padding:0px">
	            <table class="table2" id="tab_xm" style="margin-top:5px;" cellpadding="0" cellspacing="0" >
	                <tr id="item0">
	                    <th style=" text-align:center; position:relative; width:10%;"><div contenteditable="true">序号</div>
	                    <div name="btnAdd" onclick="addTable()" ></div>
	                    </th>
	                    <th style=" width:20%"><div contenteditable="true">项目名称</div></th>
	                    <th style=" width:20%"><div contenteditable="true">施工方</div></th>
	                    <th style=" width:20%"><div contenteditable="true">产品价格</div></th>
	                    <th style=" width:10%"><div contenteditable="true">工时费</div></th>
	                    <th style=" width:10%"><div contenteditable="true">原价</div></th>
	                    <th style=" width:10%;display:none;"><div contenteditable="true">实价</div></th>
	                </tr>
	                
	                <% int i = 0; %>
    				<s:set var="totalPrice" value="0" />
    				<s:set var="projectSum" value="0" />
            		<s:iterator value="#request.orderList" status="orderItem">
            		<s:if test="goodsType==1||goodsType==3||goodsType==5||goodsType==6">
            		<%i++; %>
	                <tr id="tab_main<%=i %>">
	                    <td style="text-align:center; position:relative;">
	                    	<div name="btnDel" onclick="delTable(<%=i %>)" ></div>
	                    	<input type="hidden" id="hidProCount<%=i %>" value="<%=i %>" />
	                    	<div contenteditable="true"><%=i %></div>
	                    </td>
	                    <td><div contenteditable="true"><s:property value="goodsName" escape="false" /></div></td>
	                    <td><div contenteditable="true"><s:property value="performerName" escape="false" /></div></td>
	                    <td>
	                      	<label id="labProPrice<%=i %>"><s:property value="price" /></label>
	                      	<s:set var="projectSum" value="#projectSum + price" />
	                    </td>
	                    <td><div contenteditable="true" id="divManHourPrice<%=i %>" name="divManHourPrice" onkeyup="changeManHourPrice(this,<%=i %>)">0.0</div></td>
	                    <td><div contenteditable="true" id="divOriginPrice<%=i %>" name="divOriginPrice" onkeyup="changeItemPrice(1,1)">
	                    	<s:property value="unitPrice" /></div>
	                    	<s:set var="totalPrice" value="#totalPrice + unitPrice" />
	                    </td>
	                </tr>
	                </s:if>
	                </s:iterator>
	                
	             </table>
	            </td>
	        </tr>
	    </table>
	    
	    <input type="hidden" id="hidTbItemCount" value="<%=i %>" />
        
        <table cellpadding="0" cellspacing="0"  width="100%" style=" margin-top:5px">
	        <tr>
	            <td style=""><div contenteditable="true">合计：￥<label id="lblItemPrice"><s:property value="#projectSum" /></label></div></td>
	            <td style=""><div contenteditable="true"><b>优惠金额：</b>￥<label id="labDiscount"><s:property value="#totalPrice - projectSum" /></label></div></td>
	            <td style=""><div contenteditable="true"><b>实收金额：</b>￥<label id ="labTotalPrice"><s:property value="#projectSum" /></label></div></td>
	            <td style="text-align:right;"><div contenteditable="true"><b>人民币大写：</b><label id="labRMBAmount"></label></div></td>
	        </tr>
	    </table>
	    <table style="margin-top:5px;">
	        <tr>
	            <td style="width:60%;">
	            	<div contenteditable="true"><b>结算日期：</b>
	            		<s:property value="orderHead.saleDate.substring(0,4)" />年
                    	<s:property value="orderHead.saleDate.substring(5,7)" />月
                    	<s:property value="orderHead.saleDate.substring(8,10)" />日
	            	</div>
	            </td>
	            <td style="width:40%;">
	                <div contenteditable="true"><b>客户验收及签署：</b></div>
	            </td>
	        </tr>
	    </table>
		<div id="print_div" style="margin-top:30px;">
            <input id="abc" class="Noprn" type="button" onclick="print()" value="立即打印" />&nbsp;&nbsp;
            <input id="abc2" class="Noprn" type="button" onclick="preview()" value="打印预览" />&nbsp;&nbsp;
        </div>

        <script>
            function isNumberPro(ele) {
                var val = ele.innerHTML;
                if (!val) return false;
                var strP = /^[0-9\.]*$/;
                if (!strP.test(val)) return false;
                try {
                    if (parseFloat(val) != val) return false;
                }
                catch (ex) {
                    return false;
                }
                return true;
            }
            function isNumber(ele) {
                var val = ele.html();
                if (!val) ele.html("");
                var strP = /^[0-9\.]*$/;
                if (!strP.test(val)) ele.html("");
                try {
                    if (parseFloat(val) != val) ele.html("");
                }
                catch (ex) {
                    return false;
                }
            }


            function print() {
                document.getElementById("print_div").style.display = "none";
                var LODOP = getLodop();
                LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                LODOP.ADD_PRINT_HTM(20, 20, "100%", "100%", document.documentElement.innerHTML);
                LODOP.PRINTA();
                document.getElementById("print_div").style.display = "block";
            }

            function preview() {
                document.getElementById("print_div").style.display = "none";
                $("div[name='btnAdd']").hide();
                $("div[name='btnDel']").hide();
                var LODOP = getLodop();
                LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                LODOP.ADD_PRINT_HTM(20, 20, "100%", "100%", document.documentElement.innerHTML);
                LODOP.PREVIEW();
                document.getElementById("print_div").style.display = "block";
                $("div[name='btnAdd']").show();
                $("div[name='btnDel']").show();
            }

        </script>
    </div>

</body>
</html>
<script>
$("#labRMBAmount").html(upDigit(<s:property value="#projectSum" />));

function addTable() {
    var hidTbCount = parseInt($("#hidTbItemCount").val());
    var html = "";
    html += "<tr id=\"tab_main" + (hidTbCount + 1) + "\"><td style=\" text-align:center; position:relative;\"><input type=\"hidden\" id=\"hidProCount" + (hidTbCount + 1) + "\" value=\"0\" /><div contenteditable=\"true\">" + (hidTbCount + 1) + "</div><div name=\"btnDel\" onclick=\"delTable(" + (hidTbCount + 1) + ")\" ></div></td><td><div contenteditable=\"true\"></div></td>";
    html += "<td><div contenteditable=\"true\"></div></td><td><div contenteditable=\"true\"></div></td>";
    html += "<td><div contenteditable=\"true\" id=\"divManHourPrice" + (hidTbCount + 1) + "\" name=\"divManHourPrice\" onkeyup=\"changeManHourPrice(this," + (hidTbCount + 1) + ")\"></div></td>";
    html += "<td><div contenteditable=\"true\" id=\"divOriginPrice" + (hidTbCount + 1) + "\" name=\"divOriginPrice\" onkeyup=\"changeItemPrice(" + (hidTbCount + 1) + ",1)\"></div></td>";
    //html += "<td><div contenteditable=\"true\" id=\"divActualPrice" + (hidTbCount + 1) + "\" name=\"divActualPrice\" onkeyup=\"changeItemPrice(" + (hidTbCount + 1) + ",2)\"></div></td></tr>";

    $("#tab_xm").append(html);
    $("#hidTbItemCount").val(hidTbCount + 1);
}
function delTable(i) {
    if (confirm("确定删除吗?")) {
        $("#tab_main" + i).remove();
        $("#hidTbItemCount").val(parseInt($("#hidTbItemCount").val()) - 1);
    }
    var allOriginPrice = 0; var allActualPrice = 0;
    var discountPrice = 0; var allItemPrice = 0;

    $("div[name='divOriginPrice']").each(function () {
        ;
        allOriginPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
    });
    $("div[name='divActualPrice']").each(function () {
        allItemPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
    });
    discountPrice = allOriginPrice - allItemPrice;
    $("#lblItemPrice").html(allOriginPrice);
    $("#labDiscount").html(discountPrice.toFixed(2));
    $("#labTotalPrice").html(allItemPrice);
    PageMethods.RMBAmount(allItemPrice + "", test);
}

function changeItemPrice(i, t) {
    var ele;
    if (t == 1)
        ele = $("#divOriginPrice" + i);
    else
        ele = $("#divActualPrice" + i);
    isNumber(ele);

    var allOriginPrice = 0; var allActualPrice = 0;
    var discountPrice = 0; var allItemPrice = 0;

    $("div[name='divOriginPrice']").each(function () {
        ;
        allOriginPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
    });
    $("div[name='divActualPrice']").each(function () {
        allItemPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
    });
    discountPrice = (parseFloat(allOriginPrice) - parseFloat(allItemPrice));
    $("#lblItemPrice").html(allOriginPrice);
    $("#labDiscount").html(parseFloat(discountPrice).toFixed(2));
    $("#labTotalPrice").html(allItemPrice);
    PageMethods.RMBAmount(allItemPrice + "", test);
}
function test(result) {
    $("#labRMBAmount").html(result);
}
function changeProPrice(ele, tbitem) {
    var isResult = isNumberPro(ele);
    if (!isResult) {
        ele.innerHTML = "";
    }
    var allPrice = 0;
    var count = parseInt(document.getElementById("hidProCount" + tbitem).value);
    if (count > 0) {
        var salesPrice = 0; var salesCount = 0;
        for (var i = 0; i < count; i++) {
            salesPrice = document.getElementById("divSalesPrice_" + tbitem + "_" + (i + 1)).innerHTML;
            salesPrice = salesPrice == "" ? 0 : salesPrice;
            salesCount = document.getElementById("divSalesCount_" + tbitem + "_" + (i + 1)).innerHTML;
            salesCount = salesCount == "" ? 0 : salesCount;
            document.getElementById("divSalesAllPrice_" + tbitem + "_" + (i + 1)).innerHTML = parseInt(salesCount) * parseFloat(salesPrice);
            allPrice += parseInt(salesCount) * parseFloat(salesPrice);
        }
        document.getElementById("labProPrice" + tbitem).innerHTML = allPrice;
        document.getElementById("divOriginPrice" + tbitem).innerHTML = allPrice + parseFloat(document.getElementById("divManHourPrice" + tbitem).innerHTML);
        var allOriginPrice = 0, allItemPrice = 0, discountPrice = 0;
        $("div[name='divOriginPrice']").each(function () {
            ;
            allOriginPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
        });
        $("div[name='divActualPrice']").each(function () {
            allItemPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
        });
        discountPrice = (parseFloat(allOriginPrice) - parseFloat(allItemPrice));
        $("#lblItemPrice").html(allOriginPrice);
        $("#labDiscount").html(parseFloat(discountPrice).toFixed(2));
        $("#labTotalPrice").html(allItemPrice);
    }
}
function changeManHourPrice(ele, tbitem) {
    var isResult = isNumberPro(ele);
    if (!isResult) {
        ele.innerHTML = "";
    }

    $("#divOriginPrice" + tbitem).html(parseFloat(ele.innerHTML) + parseFloat($("#labProPrice" + tbitem).html()));
    var allOriginPrice = 0, allItemPrice = 0, discountPrice = 0;
    $("div[name='divOriginPrice']").each(function () {
        ;
        allOriginPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
    });
    $("div[name='divActualPrice']").each(function () {
        allItemPrice += parseFloat($(this).html() == "" ? 0 : $(this).html());
    });
    discountPrice = (parseFloat(allOriginPrice) - parseFloat(allItemPrice));
    $("#lblItemPrice").html(allOriginPrice);
    $("#labDiscount").html(parseFloat(discountPrice).toFixed(2));
    $("#labTotalPrice").html(allItemPrice);
}


function upDigit(n){  
    var fraction = ['角', '分'];  
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];  
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];  
    var head = n < 0? '欠': '';  
    n = Math.abs(n);  

    var s = '';  

    for (var i = 0; i < fraction.length; i++) {  
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
    }  
    s = s || '整';  
    n = Math.floor(n);  

    for (var i = 0; i < unit[0].length && n > 0; i++){  
        var p = '';  
        for (var j = 0; j < unit[1].length && n > 0; j++){  
            p = digit[n % 10] + unit[1][j] + p;  
            n = Math.floor(n / 10);  
        }  
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;  
    }  
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');  
} 
</script>