
$(function(){
    $(".pay p").css("width",parseInt($(".pay").width())-80+"px");
    $("footer a").click(function () {
        $(this).addClass("active");
        $(this).siblings("a").removeClass("active");
    });
    $(".title").click(function(){
        $(this).toggleClass("select");
        if($(this).hasClass("select")){
            $(this).siblings().children().children(".commodity").addClass("select");
            $(this).text("取消");
        }else{
            $(this).siblings().children().children(".commodity").removeClass("select");
            $(this).text("全选");
        }
        if($(".title").hasClass("select")||$(".commodity").hasClass("select")){
            $(".pay").css("display","block");
            $(".pay a span").text($(".main .product").length);
        }else{
            $(".pay").css("display","none");
        }
        for(var i= 0,p_total=0;i<$(".commodity.select").length;i++){
            var x=$(".commodity.select")[i];
            p_total+=$(x).children(".detail ").children(".price_num").children(".price").children("span").text()*$(x).children(".detail ").children(".price_num").children(".num").children("span").text()
        }
        $(".pay p span").text(parseFloat(p_total).toFixed(2));
    });
    $(".commodity").click(function(){
        $(this).toggleClass("select");
        //$(this).parent().siblings(".title").toggleClass("select");
        if($(".main .commodity.select").length == '0'){
            $(".title").removeClass("select")
        }
        if($(".title").hasClass("select")||$(".commodity").hasClass("select")){
            $(".pay").css("display","block");
            $(".pay a span").text($(".main .commodity.select").length);
            for(var i= 0,p_total=0;i<$(".commodity.select").length;i++){
                var x=$(".commodity.select")[i];
                p_total+=$(x).children(".detail ").children(".price_num").children(".price").children("span").text()*$(x).children(".detail ").children(".price_num").children(".num").children("span").text()
            }
            $(".pay p span").text(parseFloat(p_total).toFixed(2));
        }else{
            $(".pay").css("display","none");
        }
    });
    $(".num button").click(function(event) {
        var elem = event.target;
        if ($(elem).text() == "+") {
            var snum = parseInt($(elem).prev().text());
            $(elem).prev().text(snum + 1);

            if (snum == 99) {
                $(elem).next().text(99);
                layer.msg('您一次可以购买的商品为1~99件',{
                    time:1000,
                    area:['80%','']
                })
            }
            $.ajax({
        		url: "/mall/updateMallCart.atc",
        		type: "POST",
        		data:{
        			'cart.memId': memId,
        			'cart.goodsId': $(elem).parent().attr("data-gid"),
        			'cart.number': snum + 1,
        		},
        		success: function(result){
        			for(var i= 0,p_total=0;i<$(".commodity.select").length;i++){
                        var x=$(".commodity.select")[i];
                        p_total+=$(x).children(".detail ").children(".price_num").children(".price").children("span").text()*$(x).children(".detail ").children(".price_num").children(".num").children("span").text()
                    }
                    $(".pay p span").text(parseFloat(p_total).toFixed(2));
        		}
        	});
        } else {
            var snum = parseInt($(elem).next().text());
            $(elem).next().text(snum - 1);
            if (snum == 1) {
                $(elem).next().text(1);
                layer.msg('您一次可以购买的商品为1~99件',{
                    time:1000,
                    area:['80%','']
                })
            }
            $.ajax({
        		url: "/mall/updateMallCart.atc",
        		type: "POST",
        		data:{
        			'cart.memId': memId,
        			'cart.goodsId': $(elem).parent().attr("data-gid"),
        			'cart.number': snum - 1,
        		},
        		success: function(result){
        			for(var i= 0,p_total=0;i<$(".commodity.select").length;i++){
                        var x=$(".commodity.select")[i];
                        p_total+=$(x).children(".detail ").children(".price_num").children(".price").children("span").text()*$(x).children(".detail ").children(".price_num").children(".num").children("span").text()
                    }
                    $(".pay p span").text(parseFloat(p_total).toFixed(2));
        		}
        	});
        }
        event.stopPropagation();
    });
    $(".edit").click(function(){
        if($(this).text()=="编辑"){
           $(this).text("取消");
            //$(".commodity").css({"transition":"all 0.5s ease"});
            $(".commodity").css("right","60px");
            $(".del").css("right","0");
        }else{
            $(this).text("编辑");
            //$(".commodity").css({"transition":"all 0.5s ease"});
            $(".commodity").css("right","0");
            $(".del").css("right","-60px");
        }
    });
    $(".del").click(function(){
        $(this).parents(".product").remove();
        $.ajax({
    		url: "/mall/deleteMallCart.atc",
    		type: "POST",
    		data:{
    			'cart.memId': memId,
    			'cart.goodsId': $(this).attr("data-gid")
    		},
    		success: function(result){
    			for(var i= 0,p_total=0;i<$(".commodity.select").length;i++){
    	            var x=$(".commodity.select")[i];
    	            p_total+=$(x).children(".detail ").children(".price_num").children(".price").children("span").text()*$(x).children(".detail ").children(".price_num").children(".num").children("span").text()
    	        }
    	        $(".pay p span").text(parseFloat(p_total));
    		}
    	});
        if($(".main .product").length == 0){
            $(".main ").css("display","none");
            $(".empty").css("display","block");
        }
    });
    
    $(".pay a").click(function(){
//    	var goodsIds = "";
//    	for(var i = 0,p_total=0;i<$(".commodity.select").length;i++){
//            var x = $(".commodity.select")[i];
//            goodsIds += $(x).attr("data-gid") + ",";
//        }
//    	window.location.href="/mall/initPay.atc?type=" + type + "&goodsIds=" + goodsIds + "&sid=" + sid;
    	var buy = "";
    	for(var i = 0,p_total=0;i<$(".commodity.select").length;i++){
			var x = $(".commodity.select")[i];
			buy += $(x).attr("data-gid") + "|" + $(x).find("span[id='data-gnum']").html()+ "|" + $(x).attr("data-mid") + "|" + $(x).attr("data-mname") + ",";
		}
    	window.location.href="/mall/initPay.atc?type=" + type + "&buy=" + buy + "&sid=" + sid;
    	
    	
    });
    
    
    
    
    
});