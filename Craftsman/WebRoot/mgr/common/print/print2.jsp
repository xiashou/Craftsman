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
        *{font-size: 12px;}
        table{width: 96%;}
        table td, th{padding: 2px;}
        .table1{border-collapse: collapse;}
        .table1 td, .table1 th{}
        .table2{border-collapse: collapse;border: 1px solid #000;border-bottom: 0px;}
        .table2 td, .table2 th{border-bottom: 1px solid #000;text-align: center; }
        div[name='btnDel']{
            background: url(/resources/images/icons/delete.png);
            background-position: center;
            background-size: 20px 20px;
            width: 20px;
            height: 20px;
            float: left;
            border-radius: 14px;
            position: absolute;
            cursor: pointer;
            top: 1px;
        }
        div[name='btnAdd'] {
            background: url(/resources/images/icons/add.png);
            background-position: center;
            background-size: 20px 20px;
            width: 20px;
            height: 20px;
            float: left;
            border-radius: 14px;
            position: absolute;
            cursor: pointer;
            top: 1px;
        }
    </style>
	
  </head>

<body>
	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>

	<div id="div_main">
    
        <input name="hidVoucherAmount" type="hidden" id="hidVoucherAmount" value="0" />
        <table style="margin-bottom:3px;">
            <tr>
                <th style=" padding:0px;">
                    <div contenteditable="true" style="font-size: 24px;"><s:property value="setting.shopName" /></div>
                </th>
            </tr>
            <tr>
                <th style="padding:0px;">
                    <div contenteditable="true" style="font-size: 14px;">
                    	结算单&nbsp;&nbsp;项目单号：<s:property value="orderHead.orderId" />&nbsp;&nbsp;
                    	<s:property value="orderHead.saleDate.substring(0,4)" />年
                    	<s:property value="orderHead.saleDate.substring(5,7)" />月
                    	<s:property value="orderHead.saleDate.substring(8,10)" />日
                    </div>
                </th>
            </tr>
        </table>
        
        <table  class="table1">
            <tr>
                <td colspan="2">
                    <div contenteditable="true">
                        <b>联系地址：</b><s:property value="setting.address" /></div>
                </td>
                <td>
                    <div contenteditable="true">
                        <b>联系电话：</b><s:property value="setting.phone" /></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div contenteditable="true">
                        <b>车牌号码：</b><s:property value="car.carShort" escape="false" /><s:property value="car.carCode" /><s:property value="car.carNumber" /></div>
                </td>
                <td>
                    <div contenteditable="true">
                        <b>车辆型号：</b><s:property value="car.brandName" escape="false" /></div>
                </td>
                <td>
                    <div contenteditable="true">
                        <b>车架号码：</b><s:property value="car.carFrame" /></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div contenteditable="true">
                        <b>车主姓名：</b><s:property value="member.name" escape="false" /><s:if test='member.sex==1'>先生</s:if><s:elseif test='member.sex==2'>女士</s:elseif></div>
                </td>
                <td>
                    <div contenteditable="true">
                        <b>车主电话：</b><s:property value="member.mobile" /></div>
                </td>
                <td>
                    <div contenteditable="true">
                        <b>会员编号：</b><s:property value="member.vipNo" /></div>
                </td>
            </tr>
            <tr>
                <td style="width: 30%; text-align: left;">
                    <div contenteditable="true">
                        <b>接车人员：</b></div>
                </td>
                <td style="width: 40%; text-align: left;">
                    <div contenteditable="true">
                        <b>进厂公里：</b><s:property value="car.carKilometers" />KM</div>
                </td>
                <td style="width: 30%; text-align: left;">
                    <div contenteditable="true">
                        <b>年检时间：</b><s:property value="car.carInsuCompany" /></div>
                </td>
            </tr>
        </table>
        
        <table id="tab_project" class="table2" style="margin-top: 5px;" cellpadding="0" cellspacing="0">
            <tr id="item0">
                <th style="text-align: center; border-right: 1px solid #000; position: relative; width: 10%;">
                    <div contenteditable="true">序号</div>
                    <div name="btnAdd" onclick="addProject()">
                    </div>
                </th>
                <th style="width: 40%">
                    <div contenteditable="true">项目名称</div>
                </th>
                <th style="width: 20%">
                    <div contenteditable="true">施工方</div>
                </th>
                <th>
                    <div contenteditable="true">产品价格</div>
                </th>
                <th>
                    <div contenteditable="true">工时费</div>
                </th>
                <th>
                    <div contenteditable="true">原价</div>
                </th>
            </tr>
            
            <% int i = 0; %>
    		<s:set var="totalPrice" value="0" />
    		<s:set var="projectSum" value="0" />
            <s:iterator value="#request.orderList" status="orderItem">
            	<s:if test="goodsType==1||goodsType==3||goodsType==5||goodsType==6">
                <tr id="tr_project_<%=i%>">
                	<td style="text-align: center; border-right: 1px solid #000; position: relative;">
                    	<div id="PRINTPROJECT_<%=i %>" contenteditable="true"><%=i+1 %></div>
                    	<div name="btnDel" onclick="delProject(<%=i%>)"> </div>
                	</td>
                	<td>
                    	<div contenteditable="true"><s:property value="goodsName" escape="false" /></div>
                	</td>
	                <td>
	                    <div contenteditable="true"><s:property value="performerName" escape="false" /></div>
	                </td>
	                <td>
	                    <div contenteditable="true" id="divProductPrice<%=i%>" name="divProductPrice" onkeyup="sum()"><s:property value="price" /></div>
	                    <s:set var="projectSum" value="#projectSum + price" />
	                </td>
	                <td>
	                    <div contenteditable="true" id="divManHourPrice<%=i%>" name="divManHourPrice" onkeyup="sum()">0.0</div>
	                </td>
	                <td>
	                    <div contenteditable="true" id="divOriginalPrice<%=i%>" name="divOriginalPrice" onkeyup="sum()"><s:property value="unitPrice" /></div>
	                    <s:set var="totalPrice" value="#totalPrice + unitPrice" />
	                </td>
	            </tr>
	            <% i++; %>
	            </s:if>
            </s:iterator>
            <input type="hidden" id="PRINTPROJECTCount" value="<%=i %>" />
        </table>
        
        <table id="tab_product" class="table2" style="margin-top: 5px;" cellpadding="0" cellspacing="0">
            <tr>
                <th style="text-align: center; border-right: 1px solid #000; position: relative; width: 10%;">
                    <div contenteditable="true">序号</div>
                    <div name="btnAdd" onclick="addProduct()"> </div>
                    </div>
                </th>
                <th style="width: 40%; border-right: 1px solid #000;">
                    <div contenteditable="true"> 产品名</div>
                </th>
                <th style="width: 20%;">
                    <div contenteditable="true"> 单价</div>
                </th>
                <th style="width: 10%;">
                    <div contenteditable="true">数量</div>
                </th>
                <th>
                    <div contenteditable="true">总价</div>
                </th>
            </tr>
            <% int j = 0; %>
            <s:set var="productSum" value="0" />
            <s:iterator value="#request.orderList" status="orderItem">
            	<s:if test="goodsType==2||goodsType==4">
            	<tr id="tr_product_<%=j %>">
            		<td style="text-align: center; border-right: 1px solid #000; position: relative;">
	                    <div id="PRINTPRODUCT_<%=j %>" contenteditable="true"><%=j+1 %></div>
	                    <div name="btnDel" onclick="delProduct(<%=j %>)"></div>
	                </td>
	                <td style="border-right: 1px solid #000;">
	                    <input type="hidden" name="PRINTPRODUCT" value="<s:property value="#orderItem.index + 1"/>" />
	                    <div contenteditable="true"><s:property value="goodsName" escape="false" /></div>
	                </td>
	                <td>
	                    <div contenteditable="true" id="divSalesPrice_<%=j %>" onkeyup="changeProPrice(<%=j %>)"><s:property value="unitPrice" /></div>
	                </td>
	                <td>
	                    <div contenteditable="true" id="divSalesCount_<%=j %>" onkeyup="changeProPrice(<%=j %>)"><s:property value="number" /></div>
	                    <s:set var="totalPrice" value="#totalPrice + unitPrice*number" />
	                </td>
	                <td>
	                    <div contenteditable="true" id="divSalesAllPrice_<%=j %>" name="divSalesAllPrice" onkeyup="sum()"><s:property value="price" /></div>
	                    <s:set var="productSum" value="#productSum + price" />
	                </td>
	            </tr>
	            <% j++; %>
            	</s:if>
        	</s:iterator>
        	<input type="hidden" id="PRINTPRODUCTCount" value="<%=j %>" />
        </table>
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td align="left" style="border-right: 0px; height: 30px;" >
                    <div contenteditable="true">
                        <b>工时费合计：</b>￥<label id="lblGSFHJ">0.0</label></div>
                </td>
                <td align="center" style="border-right: 0px; border-left: 0px;">
                    <div contenteditable="true">
                        <b>项目合计：</b>￥<label id="lblCPHJ"><s:property value="#projectSum" /></label></div>
                </td>
                <td align="center" style="border-right: 0px; border-left: 0px;">
                    <div contenteditable="true">
                        <b>原价：</b>￥<label id="lblYJ"><s:property value="#totalPrice" /></label></div>
                </td>
                <td align="right" style="border-left: 0px; border-right: 0px">
                    <div contenteditable="true">
                        <b>结账方式：</b>
                        <label id="Label1">
                        	<s:if test="orderHead.pbalance != 0 && orderHead.pbalance != null">余额 </s:if>
                        	<s:if test="orderHead.pcash != 0 && orderHead.pcash != null">现金 </s:if>
                        	<s:if test="orderHead.pcard != 0 && orderHead.pcard != null">刷卡 </s:if>
                        	<s:if test="orderHead.ptransfer != 0 && orderHead.ptransfer != null">转账 </s:if>
                        	<s:if test="orderHead.pwechat != 0 && orderHead.pwechat != null">微信 </s:if>
                        	<s:if test="orderHead.palipay != 0 && orderHead.palipay != null">支付宝 </s:if>
                        	<s:if test="orderHead.pbill != 0 && orderHead.pbill != null">挂账 </s:if>
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="left" style="border-left: 0px; border-right: 0px">
                    <div contenteditable="true" onkeyup="sum()">
                        <b>优惠金额：</b>￥<label id="lblYHJE"><s:property value="#totalPrice - #projectSum" /></label></div>
                </td>
                <td align="center" style="border-left: 0px; border-right: 0px">
                    <div contenteditable="true">
                        <b>实收金额：</b>￥<label id="lblSSJE"><s:property value="#productSum + #projectSum" /></label></div>
                </td>
                <td style="border-left: 0px; text-align: center;">
                    <div contenteditable="true">
                        <b>人民币大写：</b><label id="labRMBAmount"></label></div>
                </td>
                <td style="border-left: 0px; text-align: right;">
                    <b>票据：</b>
                    <input type="checkbox" />收据&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" />发票
                </td>
            </tr>
        </table>
        <table style="margin-top: 5px;">
            <tr>
                <td>
                    <div contenteditable="true">
                        <b>备注：</b></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div contenteditable="true">
                        <b>温馨提示：</b>请留意下次保养公里数：
                        公里</div>
                </td>
            </tr>
        </table>
        <table style="margin-top: 5px;">
            <tr>
                <td style="width: 40%;">
                    <div contenteditable="true">
                        <b>签署：</b><s:property value="setting.shopName" /></div>
                </td>
                <td style="width: 60%;">
                    <div style="float: right">
                        <div>
                            <div contenteditable="true">
                                <b>客户验收：</b>
                            </div>
                        </div>
                        <div style="margin: 10px 0px 10px 0px">
                            <div contenteditable="true">
                                <b>时 间：</b>　　　　年　　　　月　　　　日</div>
                        </div>
                        <div>
                            <div contenteditable="true">
                                本人现已将车提走，并同意支付本单款项！</div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <div id="print_div" style="margin-top: 30px;">
            <input id="abc" class="Noprn" type="button" onclick="print()" value="立即打印" />&nbsp;
            <input id="abc2" class="Noprn" type="button" onclick="preview()" value="打印预览" />&nbsp;
        </div>

        <script>
            var gz = 0, yejf = 1, bz = 1;

            function showYEJF() {
                if (yejf == 0) {
                    $("#u_yejf").show();
                    yejf = 1;
                } else {
                    $("#u_yejf").hide();
                    yejf = 0;
                }
            }

            function showGZ() {
                if (gz == 0) {
                    $("#divGZ").show();
                    gz = 1;
                } else {
                    $("#divGZ").hide();
                    gz = 0;
                }
            }

            function showBZ() {
                if (bz == 0) {
                    $("#trBZ").show();
                    bz = 1;
                } else {
                    $("#trBZ").hide();
                    bz = 0;
                }
            }

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

            function checktab() {
                try {
                    var eles = $("input[name='PRINTPRODUCT']");
                    if (eles.length == 0) {
                        $("#tab_product").hide();
                    }
                } catch (e) {alert(e);}
            }

            function print() {
                checktab();
                document.getElementById("print_div").style.display = "none";
                $("div[name='btnAdd']").hide();
                $("div[name='btnDel']").hide();
                var LODOP = getLodop();
                //LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                LODOP.ADD_PRINT_HTM(20, 20, "100%", "100%", "<!DOCTYPE>" + document.documentElement.innerHTML);
                LODOP.SET_PRINT_STYLEA(0, "TableRowThickNess", 25);
                LODOP.PRINTA();
                document.getElementById("print_div").style.display = "block";
                $("div[name='btnAdd']").show();
                $("div[name='btnDel']").show();
                $("#tab_pro").show();
                try {
                    //var no = '332826';
                    //sava();
                    
                } catch (ex) { }
            }

            function preview() {
                checktab();
                document.getElementById("print_div").style.display = "none";
                $("div[name='btnAdd']").hide();
                $("div[name='btnDel']").hide();
                var LODOP = getLodop();
                //LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                LODOP.ADD_PRINT_HTM(20, 20, "100%", "100%", "<!DOCTYPE>" + document.documentElement.innerHTML);
                LODOP.SET_PRINT_STYLEA(0, "TableRowThickNess", 25);
                LODOP.PREVIEW();
                document.getElementById("print_div").style.display = "block";
                $("#tab_pro").show();
                $("div[name='btnAdd']").show();
                $("div[name='btnDel']").show();
                /*
                try {
                    var from = document.getElementById("form1");
                    if (from != null) {
                        $("div[name='btnAdd']").show();
                        $("div[name='btnDel']").show();
                    }
                } catch (ex) {
                    $("div[name='btnAdd']").hide();
                    $("div[name='btnDel']").hide();
                }
                */
            }
            function sava() {
                $("div[name='btnAdd']").hide();
                $("div[name='btnDel']").hide();
                var newhtml = document.getElementById("div_main").innerHTML;
                document.body.innerHTML = newhtml;
                var html = document.documentElement.innerHTML;
                html = html.replace(/true/g, "false");
                var no = '332826';
                $.ajax({
                    type: 'POST',
                    dataType: 'text',
                    url: "../Handler/MainHandler.ashx",
                    data: {
                        action: 'SavaShopPrintRecord',
                        t: "xmxf",
                        h: html,
                        n: no
                    },
                    success: function (data) {
                        $("div[name='btnAdd']").show();
                        $("div[name='btnDel']").show();
                    }
                });
            }
        </script>
    </div>


</body>
</html>
<script>
$("#labRMBAmount").html(upDigit(<s:property value="#productSum + #projectSum" />));
function changeProPrice(id) {
    try {
        var p = 0;
        if (!isNaN(parseFloat($("#divSalesPrice_" + id).html()))) {
            p = parseFloat($("#divSalesPrice_" + id).html());
        }
        var c = 0;
        if (!isNaN(parseFloat($("#divSalesCount_" + id).html()))) {
            c = parseInt($("#divSalesCount_" + id).html());
        }
        $("#divSalesAllPrice_" + id).html(p * c);
        sum();
    } catch (e) { alert(e); }
}

function sort(type) {
    try {
        var eles = $("input[name='" + type + "']");
        for (var i = 0; i < eles.length; i++) {
            var id = eles[i].value;
            $("#" + type + "_" + id).html(i + 1);
        }
    } catch (e) { alert(e); }
}

function addProject() {
    try {
        var hidTbCount = parseInt($("#PRINTPROJECTCount").val());
        var html = "";
        html += "<tr id=\"tr_project_" + hidTbCount + "\">";
        html += "<td style=\"border-right:1px solid #000; text-align:center; position:relative;\"><div name=\"btnDel\" onclick=\"delProject(" + (hidTbCount) + ")\" ></div><div id=\"PRINTPROJECT_" + hidTbCount + "\" contenteditable=\"true\">" + (hidTbCount + 1) + "</div></td>";
        html += "<td><input type=\"hidden\" name=\"PRINTPROJECT\" value=\"" + hidTbCount + "\" /><div contenteditable=\"true\"></div></td><td><div contenteditable=\"true\"></div></td>";
        html += "<td><div contenteditable=\"true\" id=\"divProductPrice" + (hidTbCount) + "\" name=\"divProductPrice\" onkeyup=\"sum()\"></div></td>";
        html += "<td><div contenteditable=\"true\" id=\"divManHourPrice" + (hidTbCount) + "\" name=\"divManHourPrice\" onkeyup=\"sum()\"></div></td>";
        html += "<td><div contenteditable=\"true\" id=\"divOriginalPrice" + (hidTbCount) + "\" name=\"divOriginalPrice\" onkeyup=\"sum()\"></div></td>";
        //html += "<td><div contenteditable=\"true\" id=\"divActualPrice" + (hidTbCount) + "\" name=\"divActualPrice\" onkeyup=\"sum()\"></div></td>";
        html += "</tr>";

        $("#tab_project").append(html);
        $("#PRINTPROJECTCount").val(hidTbCount + 1);
        //sort("PRINTPROJECT");
    } catch (e) { alert(e); }
}

function addProduct() {
	try {
        var procount = parseInt($("#PRINTPRODUCTCount").val()); //产品个数
        var html = "";
        html += "<tr id=\"tr_product_" + procount + "\">";
        html += "<td style=\"border-right:1px solid #000; text-align:center; position:relative;\"><div name=\"btnDel\" onclick=\"delProduct(" + procount + ")\" ></div><div id=\"PRINTPRODUCT_" + procount + "\" contenteditable=\"true\">" + (procount + 1) + "</div></td>";
        html += "<td style=\"border-right:1px solid #000;\"><input type=\"hidden\" name=\"PRINTPRODUCT\" value=\"" + procount + "\"/><div contenteditable=\"true\"></div></td>";
        html += "<td><div contenteditable=\"true\" id=\"divSalesPrice_" + procount + "\" onkeyup=\"changeProPrice(" + procount + ")\"></div></td>";
        html += "<td><div contenteditable=\"true\" id=\"divSalesCount_" + procount + "\" onkeyup=\"changeProPrice(" + procount + ")\"></div></td>";
        html += "<td><div contenteditable=\"true\" id=\"divSalesAllPrice_" + procount + "\" name=\"divSalesAllPrice\" onkeyup=\"sum()\"></div></td>";
        html += "</tr>";
        $("#PRINTPRODUCTCount").val(procount + 1);
        $("#tab_product").append(html);
        //sort("PRINTPRODUCT");
    } catch (e) { alert(e); }
}
function delProject(i) {
    if (confirm("确定删除吗?")) {
        $("#tr_project_" + i).remove();
        //sort("PRINTPROJECT");
        //sum();
    }
}
function delProduct(id) {
    if (confirm("确定删除吗?")) {
        $("#tr_product_" + id).remove();
        //sort('PRINTPRODUCT');
        //sum();
    }
}
function sum() {
    try {
        var SumMainHourPrice = 0, ProductAllPrice = 0, SumOriginalPrice = 0, SumActualPrice = 0;
        $("div[name='divOriginalPrice']").each(function () {
            var p = parseFloat($(this).html());
            if (!isNaN(p)) {
                SumOriginalPrice += parseFloat(p);
            }
        });
        $("#lblYJ").html(SumOriginalPrice);
        $("div[name='divManHourPrice']").each(function () {
            var p = parseFloat($(this).html());
            if (!isNaN(p)) {
                SumMainHourPrice += parseFloat(p);
            }
        });
        $("#lblGSFHJ").html(SumMainHourPrice);
        $("div[name='divProductPrice']").each(function () {
            var p = parseFloat($(this).html());
            if (!isNaN(p)) {
                ProductAllPrice += parseFloat(p);
            }
        });
        $("#lblCPHJ").html(ProductAllPrice);
        $("div[name='divSalesAllPrice']").each(function () {
            var p = parseFloat($(this).html());
            if (!isNaN(p)) {
                SumActualPrice += parseFloat(p);
            }
        });
        $("#lblSSJE").html(SumActualPrice + ProductAllPrice);
        $("#lblYHJE").html(SumOriginalPrice - ProductAllPrice);
        //PageMethods.RMBAmount(SumActualPrice + "", test);
    } catch (e) { alert(e); }
}

function sumProject() {}
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