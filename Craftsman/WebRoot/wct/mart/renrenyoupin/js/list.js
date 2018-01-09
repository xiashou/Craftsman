$(function(){
    $(".list_show").css("height", $(window).height() - 84);
    $(".one_one ul.category_list").css("height", $(window).height() - 84);
    $(".navbar a").click(function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        if ($(".price").hasClass("active")) {
            if ($(".sort_up").css("border-bottom-color") == "rgb(189, 0, 0)") {
                $(".sort_up").css("border-bottom-color", "#A0A0A0");
                $(".sort_down").css("border-top-color", "#BD0000");
            } else if ($(".sort_up").css("border-bottom-color") == "rgb(160, 160, 160)") {
                $(".sort_up").css("border-bottom-color", "#BD0000");
                $(".sort_down").css("border-top-color", "#A0A0A0");
            }
        }
        if ($(".filter").hasClass("active")) {
            $(".side_right").animate({"right": 0})
        }

    });
    $("li.fl").click(function () {
        $("div.one").css({"display": "none"});
        $("div.one_one").css({"display": "block"});
    });
    $("li.jg").click(function () {
        $("div.one").css({"display": "none"});
        $("div.one_two").css({"display": "block"});
    });
    $(".close").click(function () {
        $(".side_right").animate({"right": -260})
    });
    $(".ok").click(function () {
        $(".side_right").animate({"right": -260})
    });
    $(".to_one").click(function () {
        $("div.one_one").css({"display": "none"});
        $("div.one_two").css({"display": "none"});
        $("div.one_two").css({"display": "none"});
        $("div.one").css({"display": "block"});
    });
    $(".one_two ul li").click(function () {
        $(this).addClass("select");
        $(this).siblings().removeClass("select");
        var self = this;
        setTimeout(function () {
            $("div.one_two").css({"display": "none"});
            $("div.one").css({"display": "block"});
            $(".one .jg div span").text($(self).text())
        }, 500)
    });
    $(".one_one ul.category_list li.category_list_item:first-child").click(function () {
        $(this).addClass("select");
        $(this).siblings().removeClass("select");
        var self = this;
        setTimeout(function () {
            $("div.one_one").css({"display": "none"});
            $("div.one").css({"display": "block"});
            $(".one .fl div span").text($(self).text())
        }, 500)
    });
    $(".one_one ul.category_list li.category_list_item:not(:first-child)").click(function () {
        $(this).toggleClass("select");
        $(this).siblings().removeClass("select");
        $(".category_list_item ul").css({"display": "none"});
        if ($(this).hasClass("select")) {
            $(this).children("ul").css({"display": "block"});
        }
    });
    $(".category_list_item ul li").click(function (event) {
        $(this).addClass("select");
        $(this).siblings().removeClass("select");
        var self = this;
        setTimeout(function () {
            $("div.one_one").css({"display": "none"});
            $("div.one").css({"display": "block"});
            $(".one .fl div span").text($(self).text())
        }, 500);
        event.stopPropagation();
    });
});
