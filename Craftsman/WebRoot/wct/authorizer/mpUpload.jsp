<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head id="Head1">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>配置文件上传</title>
    <style>
        .text_name{float:left;width:80px;font-size:16px;height:40px;line-height:40px;font-family:微软雅黑;text-align:right;padding-right:20px;}
        .text_inputs{float:left;width:320px;height:40px;line-height:40px;text-align:left;font-family:微软雅黑;}
        .text_desc{float:left;width:730px;height:40px;line-height:40px;}
        .text_desc label{color:red;font-size:12px;}
        .text_input{height:24px;line-height:24px;border:1px solid #919191;font-family:微软雅黑;text-indent:5px;width:200px;}
        .main_nav{width: 700px; margin: 0 auto;}
    </style>
    <script>
        window.onload = function () {
            $("#div666").css("height", ($(document).height() - 262) + "px");
            $("#div666").show();
        }

        function check() {
        	var val= $("#file").val();
        	if(!val) {
        		$.alert("请选择上传文件！");
        		return false;
        	}
        	var k = val.substr(val.indexOf("."));
        	if(".txt" != k) {
        		$.alert("文件格式不正确！");
        		return false;
        	}
        	
            $.confirm("确认上传吗？", function() {
          	  //点击确认后的回调函数
          	  var fileName = getFileName(val);
          	  $("#fileName").val(fileName);
          	  $("#form1").submit();
          	  }, function() {
          	  //点击取消后的回调函数
          	  });
        }
        
      //获取文件名称  
        function getFileName(path) {  
            var pos1 = path.lastIndexOf('/');  
            var pos2 = path.lastIndexOf('\\');  
            var pos = Math.max(pos1, pos2);  
            if (pos < 0) {  
                return path;  
            }  
            else {  
                return path.substring(pos + 1);  
            }  
        }
    </script>
</head>
<body style="overflow: auto;">
    <form method="post" action="<%=basePath %>/open/wechat/sys/mpFileUpload.atc" id="form1" enctype="multipart/form-data">

    <div class="main_nav" id="div666">
        <div class="nav_content">
        <div style="font-size:24px;font-weight:bold;height:50px;line-height:50px;font-family:微软雅黑;">请上传域名配置文件</div>
        <div class="text_name">选择文件：</div>
        <div class="text_inputs">
        <input type="file" name="file" id="file" style="margin-top:7px;border:1px solid #dadada;height:25px;width:300px;" />
        <input type="hidden" id="fileName" name="fileName"/>
        </div>
        <div class="text_desc"><input type="button" name="btnUpload" value="立即上传" onclick="return check();" id="btnUpload" style="margin-top:7px;width:100px;height:25px;border:1px solid #dadada;" /></div>

        <div class="text_inputs" style="width:650px;">注意1：请先上传后再做微信公众后台的保存动作</div>
        <div class="text_inputs" style="width:650px;">注意2：如果上传后保存无效，请退出微信后台再重新刷新后获取新的文件后重新上传</div>
        <div class="text_inputs" style="width:650px;">注意3：如果仍无效，请联系管理员</div>
        </div>
    </div>
    </form>
</body>
</html>