var obj={
    name:"",
    phone:"",
    address:"",
    num:"",
    province:"",
    city:"",
    county:""
};
var morenProvinceId= 0,morenCityId= 0,morenCountyId= 0,morenAddress="";

var name=false,num=false,dizhi=false,youbian=false,loc_province=false,loc_city=false,loc_county=false;
var selectContent=0;


$(function(){
	
	/*
	$(":checkbox[name=isDefault]").each(function(){
    	$(this).click(function(){
    		$(":checkbox[name=isDefault]").removeAttr('checked'); 
			$(this).attr('checked','checked'); 
    		$.ajax({
        		url: "/mall/updateMallAddressDefault.atc",
        		type: "POST",
        		data:{
        			'address.memId': memId,
        			'address.id': $(this).attr("data-id")
        		},
        		success: function(result){
        			if(result.success && result.address){
        				layer.msg('设置成功！',{
        		            time:1000,
        		            area:['60%','']
        		        });
        				$(".address_link .consignee_l span").html(result.address.name);
        				$(".address_link .consignee_r").html(result.address.mobile);
        				$(".address_link .address span").html(result.address.province + result.address.city + result.address.area + result.address.address);
        				$(".affirm_pay").show();
        		    	$(".affirm_address").hide();
        			}
        		}
        	});
    	}); 
    }); 
	*/
	
    //removeAddMessage();
    //addClickSelect();
    //addChange();
	/*
	$(".affirm").click(function(){
		$(".affirm_pay").hide();
		$(".address_add").show();
	});
	*/
    $(".address_add_main ul li").css({
        height:$(window).height() *.05,
        lineHeight:$(window).height() *.05+"px"
    });
   $(".removeHint").css({
       height:$(window).height()
   });

    $(".address_add_main_but").click(function(){
         name=$(".address_add_main_input").eq(0).val().length>0?$(".address_add_main_input").eq(0).css({border:"1px solid #262626"}):$(".address_add_main_input").eq(0).css({border:"1px solid #red"});
         num=$(".address_add_main_input").eq(1).val().length==11?true:false;
         dizhi=$(".address_add_main_input").eq(2).val().length>0?true:false;
         youbian=$(".address_add_main_input").eq(3).val().length>0?true:false;
         loc_province=$("#loc_province").val()!=0?true:false;
         loc_city=$("#loc_city").val()!=0?true:false;
         loc_county=$("#loc_county").val()!=0?true:false;
          if(name){
          }else{
              console.log(22);
              

          }
        if(num){
            $(".address_add_main_input").eq(1).css({border:"1px solid #262626"});
        }else{
            $(".address_add_main_input").eq(1).css({border:"1px solid red"});

        }
        if(dizhi){
            $(".address_add_main_input").eq(2).css({border:"1px solid #262626"});
        }else{
            $(".address_add_main_input").eq(2).css({border:"1px solid red"});

        }
        if(youbian){
            $(".address_add_main_input").eq(3).css({border:"1px solid #262626"});
        }else{
            $(".address_add_main_input").eq(3).css({border:"1px solid red"});

        }
        if(loc_province){
            $("#loc_province").css({
                border:"1px solid #262626"
            });
        }else{
            $("#loc_province").css({border:"1px solid red"});

        }
        if(loc_city){
            $("#loc_city").css({border:"1px solid #262626"});
        }else{
            $("#loc_city").css({border:"1px solid red"});

        }
        if(loc_county){
            $("#loc_county").css({border:"1px solid #262626"});
        }else{
            $("#loc_county").css({border:"1px solid red"});

        }

         if(name&&num&&dizhi&&youbian&&loc_province&&loc_city&&loc_county){
              if($(".affirm_add_head_new").text()=="修改地址"){
                  addChangeData();

                  $(".affirm_address").show();
                  $(".address_add").hide();
                  $(".nullDiv").hide();
                  $(".content").show();
              }else{
                  $(".affirm_address").show();
                  $(".address_add").hide();
                  $(".nullDiv").hide();
                  $(".content").show();

                  obj.name=$(".address_add_main input").eq(0).val();
                  obj.phone=$(".address_add_main input").eq(1).val();
                  obj.address=$(".address_add_main input").eq(2).val();
                  obj.num=$(".address_add_main input").eq(3).val();
                  morenProvinceId=$(".address_add_main select").eq(0).val();
                  morenCityId=$(".address_add_main select").eq(1).val();
                  morenCountyId=$(".address_add_main select").eq(2).val();

                  morenAddress=obj.address;

                  obj.province=$("#loc_province option[value="+morenProvinceId+"]").text();
                  obj.city=$("#loc_city option[value="+morenCityId+"]").text();
                  obj.county=$("#loc_county option[value="+morenCountyId+"]").text();

                  $(".address_add_main input").val("");
                  $(".address_add_main select").val("");

                  addMessage();
                  addClickSelect();
                  removeAddMessage();
                  addChange();
                  $('.p;').click();
              }
         }else{

         }
    });

    $(".payStyle li").click(function(){
        $(".payStyle li").removeClass("liClick");
        $(this).addClass("liClick");

    });
    
    $("#addAddress").click(function(){
    	if($("#name").val() == ''){
    		alert("收货人姓名不能为空！");
    		return false;
    	} else if($("#mobile").val() == '') {
    		alert("收货人电话不能为空！");
    		return false;
    	} else if($("#loc_province").find("option:selected").text() == '') {
    		alert("省份不能为空！");
    		return false;
    	} else if($("#loc_city").find("option:selected").text() == '') {
    		alert("城市不能为空！");
    		return false;
    	} else if($("#loc_county").find("option:selected").text() == '') {
    		alert("所在区不能为空！");
    		return false;
    	} else {
    		var aid = $("#aid").val();
    		$.ajax({
        		url: aid == '' ? "/mall/insertMallAddress.atc" : "/mall/updateMallAddress.atc",
        		type: "POST",
        		data:{
        			'address.id': aid,
        			'address.memId': memId,
        			'address.name': $("#name").val(),
        			'address.mobile': $("#mobile").val(),
        			'address.province': $("#loc_province").find("option:selected").text(),
        			'address.city': $("#loc_city").find("option:selected").text(),
        			'address.area': $("#loc_county").find("option:selected").text(),
        			'address.address': $("#address").val(),
        			'address.postCode': $("#postCode").val()
        		},
        		success: function(result){
        			if(result.success){
        				layer.msg('添加成功！',{
        		            time:1000,
        		            area:['60%','']
        		        });
        				if(result.address){
        					var $add=$('<div class="weui-form-preview" data-id="' + result.address.id + '"><div class="weui-form-preview__bd"><div class="weui-form-preview__item tsm_head">'+
        							'<label class="weui-form-preview__label">'+ result.address.name + '</label><span class="weui-form-preview__value">' + 
        							result.address.mobile + '</span></div><div class="weui-form-preview__item"><span class="weui-form-preview__value">' + 
        							result.address.province + result.address.city + result.address.area + result.address.address + '</span></div></div>' + 
        							'<div class="weui-form-preview__ft"><div class="weui-cells weui-cells_checkbox"><label class="weui-cell weui-check__label" for="s11">' + 
        							'<div class="weui-cell__hd"><input type="checkbox" class="weui-check" name="isDefault" onclick="setDefault(this);" data-id="' + result.address.id + '">'+
        							'<i class="weui-icon-checked"></i></div><div class="weui-cell__bd"><p>设为默认地址</p></div></label></div><div class="tsm_button">'+
        							'<button onclick="initEdtAddress(this);" data-id="' + result.address.id + '">编辑</button> ' +
        							'<button onclick="delAddress(this);" data-id="' + result.address.id + '">删除</button></div></div></div>');  
        					if(aid != '')
        						$("div[data-id='" + aid + "']").remove();
        					$(".addContent").prepend($add);
        				}
        				$(".affirm_address").show();
            			$(".address_add").hide();
        			}
        		}
        	});
    	}
    });
    
    
    
});

function setDefault(obj){
	$(":checkbox[name=isDefault]").removeAttr('checked'); 
	$(obj).attr('checked','checked'); 
	$.ajax({
		url: "/mall/updateMallAddressDefault.atc",
		type: "POST",
		data:{
			'address.memId': memId,
			'address.id': $(obj).attr("data-id")
		},
		success: function(result){
			if(result.success && result.address){
				layer.msg('设置成功！',{
		            time:1000,
		            area:['60%','']
		        });
				$(".address_link .consignee_l span").html(result.address.name);
				$(".address_link .consignee_r").html(result.address.mobile);
				$(".address_link .address span").html(result.address.province + result.address.city + result.address.area + result.address.address);
				$(".affirm_pay").show();
		    	$(".affirm_address").hide();
			}
		}
	});
}

function initEdtAddress(aid){
	$.ajax({
		url: "/mall/initUpdateMallAddress.atc",
		type: "POST",
		data:{
			'address.id': aid
		},
		success: function(result){
			if(result.success && result.address){
				$("#aid").val(result.address.id);
				$("#name").val(result.address.name);
				$("#mobile").val(result.address.mobile);
				$("#address").val(result.address.address);
				$("#postCode").val(result.address.postCode);
				$(".affirm_address").hide();
		        $(".address_add").show();
			}
		}
	});
}

function delAddress(aid){
	$.ajax({
		url: "/mall/deleteMallAddress.atc",
		type: "POST",
		data:{
			'address.id': aid
		},
		success: function(result){
			if(result.success && result.address){
				layer.msg('删除成功！',{
		            time:1000,
		            area:['40%','']
		        });
				$(obj).parent().parent().parent().remove();
			}
		}
	});
}

function addMessage(){
    var str="<div class='content contentSlect' data-num="+obj.num+" morenProvinceId="+morenProvinceId+" morenCityId="+morenCityId+" morenCountyId="+morenCountyId+" morenAddress="+morenAddress+">"
+"<div class='contentLeft'>"
+"<span class='add_name'>"+obj.name+"</span><br>"
+"<span class='add_address'>"+obj.province+obj.city+obj.county+obj.address+"</span>"
+"</div>"
+"<div class='contentRight'>"
+"<span class='add_phone'>"+obj.phone+"</span><br>"
+"<img src='images/gou.png' class='selectGou' alt=''/>"
+"</div>"
+"<div class='change'>更改</div>"
+"<div class='delect'>删除</div>"
+"</div>";
    $(".addContent .content").removeClass("contentSlect");
    $(".addContent").append(str);
    $(".content,.addContent").show();
    var lastBox=parseInt($(".addContent .content").size())-1;
	$(".addContent .content").eq(lastBox).click()

};

function removeAddMessage(){
    $(".content .delect").each(function(e){
        $(".content .delect").eq(e).click(function(){
            $(".removeHint").show();
            $(".noBtn").click(function(){
                $(".removeHint").hide();
            });
            $(".yesBtn").click(function(){
                $(".removeHint").hide();
                $(".affirm_address_main .content").eq(e).remove();
                if($(".consignee_l").val().length==0){
                    $(".nullDiv").show();
                    $(".addContent").hide();
                }else{
                    $(".nullDiv").hide();
                    $(".addContent").show();
                }
            });
        });
    });
}

function addClickSelect(){
    $(".affirm_address_main .content").each(function(){
        $(this).click(function(){
            $(".content").removeClass("contentSlect");
            $(this).addClass("contentSlect");
//          $(this).find(".add_name").text();
            var data=$(this).find(".add_name").text();
           $(".consignee_data .consignee_l  span").txt(data);
//         $(this).find(".add_address").text();
//          var dat=$(this).find(".add_address").text();
           $(".address span").text($(this).find(".add_address").text());

        });
    });
}

function addChange(){
    $(".content .change").each(function(e){
        $(".content .change").eq(e).click(function(){
            $(".affirm_add_head_new").text("修改地址");
            $(".affirm_address").hide();
            $(".address_add").show();
            selectContent=e;

            var a1=$(".content .add_name").eq(e).text();
            var a2=$(".content .add_phone").eq(e).text();
            var a3=$(".content").eq(e).attr("data-num");

            var a4=$(".content").eq(e).attr("morenProvinceId");
            var a5=$(".content").eq(e).attr("morenCityId");
            var a6=$(".content").eq(e).attr("morenCountyId");
            var a7=$(".content").eq(e).attr("morenAddress");

            $(".address_add_main input").eq(0).val(a1);
            $(".address_add_main input").eq(1).val(a2);
            $(".address_add_main input").eq(3).val(a3);

            $(".address_add_main #loc_province option").removeAttr("selected");
            $(".address_add_main #loc_city option").removeAttr("selected");
            $(".address_add_main #loc_county option").removeAttr("selected");
            $(".address_add_main #loc_province option[value="+a4+"]").attr("selected","selected");
            $(".address_add_main #loc_city option[value="+a5+"]").attr("selected","selected");
            $(".address_add_main #loc_county option[value="+a6+"]").attr("selected","selected");

            $(".address_add_main input").eq(2).val(a7);
        });
    });
}

function addChangeData(){

    obj.name=$(".address_add_main input").eq(0).val();
    obj.phone=$(".address_add_main input").eq(1).val();
    obj.address=$(".address_add_main input").eq(2).val();
    obj.num=$(".address_add_main input").eq(3).val();
    obj.province=$(".address_add_main select").eq(0).val();
    obj.city=$(".address_add_main select").eq(1).val();
    obj.county=$(".address_add_main select").eq(2).val();

    obj.province=$("#loc_province option[value="+obj.province+"]").text();
    obj.city=$("#loc_city option[value="+obj.city+"]").text();
    obj.county=$("#loc_county option[value="+obj.county+"]").text();



    $(".addContent .add_name").eq(selectContent).text(obj.name);
    $(".addContent .add_address").eq(selectContent).text(obj.province+obj.city+obj.county+obj.address);
    $(".addContent .add_phone").eq(selectContent).text(obj.phone);
    $(".addContent .content").eq(selectContent).attr("data-num",obj.num);

    $(".addContent .content").eq(selectContent).attr("morenProvinceId",obj.province);
    $(".addContent .content").eq(selectContent).attr("morenCityId",obj.city);
    $(".addContent .content").eq(selectContent).attr("morenCountyId",obj.county);
    $(".addContent .content").eq(selectContent).attr("morenAddress",obj.address)

}
//function show_now(){
//	$(".selectGou").click(function({
//		
//		
//		
//	});
//};
