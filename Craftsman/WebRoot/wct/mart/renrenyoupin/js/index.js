$(function(){
    var swiper = new Swiper('.swiper-container-1', {
        slidesPerView: 5
    });
    var swiper1 = new Swiper('.swiper-container-2', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        slidesPerView: 1,
        autoplayDisableOnInteraction: false,
        loop: true,
        autoplay: 2000,
        observer:true,
        observeParents:true,
        preloadImages: true,
        setWrapperSize :true
    });

    $(".navbar .switch").click(function () {
        var swiper2 = new Swiper('.swiper-container-3', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            slidesPerView: 1,
            autoplayDisableOnInteraction: false,
            loop: true,
            autoplay: 2000,
            observer:true,
            observeParents:true,
            preloadImages: true,
            setWrapperSize :true
        });
        var swiper1 = new Swiper('.swiper-container-2', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            slidesPerView: 1,
            autoplayDisableOnInteraction: false,
            loop: true,
            autoplay: 2000,
            observer:true,
            observeParents:true,
            preloadImages: true,
            setWrapperSize :true
        });
        $(".navbar .switch").removeClass("active");
        $(this).addClass("active");
        $(".main").css("display","none");
        $(".main").eq($(this).index()).css("display","block");
    });
    $("footer a").click(function () {
        $(this).addClass("active");
        $(this).siblings("a").removeClass("active");
    });

    setInterval(function(){
        var s=$(document).scrollTop();
        setTimeout(function(){
            var h=$(document).scrollTop();
            if(h-s>0){
                $(".navbar-inner").hide();
            }else if(h-s<0){
                $(".navbar-inner").show();
            }else if(h-s==0){
                if($(".navbar-inner").is(":hidden")){
                    $(".navbar-inner").hide();
                }else{
                    $(".navbar-inner").show();
                }
            }
        },200);
    },200);
});
//$(".main_index").css("right",0);
//$(".main_else").css("right",-$(".main").width());
//$(".navbar .switch").click(function () {
//    $(".navbar .switch").removeClass("active");
//    $(this).addClass("active");
//});
//$(".navbar .switch:not(:first-child)").click(function(){
//    $(".main_index").css("right",-$(".main").width());
//    $(".main_else").css("right",0);
//});
//$(".navbar .switch:first-child").click(function(){
//    $(".main_else").css("right",-$(".main").width());
//    $(".main_index").css("right",0);
//});