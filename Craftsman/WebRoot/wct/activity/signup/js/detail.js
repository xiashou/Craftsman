
$(function(){
	var specId = "";
    var s_dj=$("#affirm_join p.total span").text();
    var dj=parseFloat($("#affirm_join p.total span").text());
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        slidesPerView: 1,
        autoplay: 3000,
        autoplayDisableOnInteraction: false,
        loop: true
    });
    $(".join").click(function(){
        layer.open({
            type: 1,
            title:false,
            closeBtn: 1, //显示关闭按钮
            shift: 2,
            scrollbar: false,
            shadeClose: false, //开启遮罩关闭
            content: $("#affirm_join"),
            area: ['100%','287px'],
            offset: 'rb'
        });
        $(".affirm_jieshao").css("width",parseInt($(".affirm_up").width())-parseInt($(".img_box").width())+"px");
    });
    
    $(".buy").click(function(){
        layer.open({
            type: 1,
            title:false,
            closeBtn: 1, //显示关闭按钮
            shift: 2,
            scrollbar: false,
            shadeClose: false, //开启遮罩关闭
            content: $("#affirm_buy"),
            area: ['100%','287px'],
            offset: 'rb'
        });
        $("#affirm_buy .affirm_jieshao").css("width",parseInt($("#affirm_buy .affirm_up").width())-parseInt($("#affirm_buy .img_box").width())+"px");
    });
    
    $("#affirm_join .affirm_ok").click(function(){
    	location.href= "/mall/initActJoin.atc?actId=" + $("#actId").val() + "&specIds=" + specId + "&number=" + $("#affirm_join .affirm_num span").text() + "&price=" + $(".affirm_up .total span").html() + "&sid=" + sid;
    });
    
    $(".norms a").click(function(){
    	if($(this).hasClass("active")) {
    		$(this).removeClass("active");
    		$(".affirm_up .total span").html(parseFloat(parseFloat($(".affirm_up .total span").html()) - parseFloat($(this).attr("data-price"))).toFixed(2));
    		specId = specId.replace($(this).attr("data-specId") + ",", "");
    	} else {
    		$(this).addClass("active");
    		$(".affirm_up .total span").html(parseFloat(parseFloat($(".affirm_up .total span").html()) + parseFloat($(this).attr("data-price"))).toFixed(2));
    		specId += $(this).attr("data-specId") + ",";
    	}
    });
    
    $("#affirm_join  .affirm_num button").click(function(event) {
        var elem = event.target;
        if ($(elem).text() == "+") {
            var snum = parseInt($(elem).prev().text());
            $(elem).prev().text(snum + 1);
            $("#affirm_join .total span").text((dj * parseInt($("#affirm_join .affirm_num span").text())).toFixed(2));
//            $("#affirm_buy .affirm_num span").text($("#affirm_join .affirm_num span").text());
//            $("#affirm_buy .total span").text((dj * parseInt($("#affirm_buy .affirm_num span").text())).toFixed(2));
        } else {
            var snum = parseInt($(elem).next().text());
            $(elem).next().text(snum - 1);
            $("#affirm_join .total span").text(parseFloat(dj * parseInt($("#affirm_join .affirm_num span").text())).toFixed(2));
//            $("#affirm_buy .affirm_num span").text($("#affirm_join .affirm_num span").text());
//            $("#affirm_buy .total span").text((dj * parseInt($("#affirm_buy .affirm_num span").text())).toFixed(2));
            if (snum == 1) {
                layer.msg('一件起购！',{
                    time:1000
                });
                $(elem).next().text(1);
                $(".total span").text(s_dj);
                $("#affirm_buy .affirm_num span").text(1);
            }
        }
        event.stopPropagation();
    });
});
