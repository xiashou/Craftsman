$(function() {
	$(".main .left").css("height", $(window).height() - 99);
	$(".main .right").css("height", $(window).height() - 99);
	$(".main ul li").click(function() {
		$(this).addClass("current");
		$(this).siblings().removeClass("current");
		$(".right_content").css("display", "none");
		$(".right_content").eq($(this).index()).css("display", "block")
		$(".right_content").eq($(this).index()).children().children("p").html($(this).html());
	});
});
