/**
 * Created by wzw on 2014/8/31.
 */
(function ($) {
    var basepath = MZ.getFullPath();
    //此处是生成左边的菜单树 生成树之后把树中的各项li的href变为Ajax
    //刚开始时候未用自动加载方式 我们是固定写法 所以此处Ajax暂时去掉
    //代码 开始 1 -15
    $(function () { //1
        $.ajax({
            type: 'post',
            url: basepath + '/admin/loadTrees',
            dataType: 'json',
            success: function (result) {
                var json = MZ.jsonEval(result);
                var lis = "";
                var options = {idKey: 'id', name: 'name', pIdKey: 'parentId'};
                var tdata = MZ.parseSimpleData(options, result.data);
                var lis = _geneateTrees(tdata);
                $(lis).appendTo($('#nav')).find('a').on('click', _initLiEnv);
            }
        });
    }); //15
    //此处是生成左边的菜单树 生成树之后把树中的各项li的href变为Ajax
    //刚开始时候未用自动加载方式 我们是固定写法 所以此处Ajax暂时去掉
    // 代码 结束
//$(function(){
//    $('#nav').find('li a').on('click', _initLiEnv); //ajax 去掉后就在此处href变Ajax
//})


    function _initLiEnv(ev) {
        ev.preventDefault();
        var $this = $(this);
        var url = $this.attr('href');
        if(!url || url == '', $.trim(url) == '') {
            //return false;
        }

        var target = $this.attr('target'),
            rel = $this.attr('rel'), title = $this.text();//rel title 目前作为扩展属性
        if(target == 'dialog') { //目前没有直接从菜单作为dialog的
            if(url.substring(0,1) == '#') {
                $(url).modal();
            } else {
                $.mzdialog.open(url, rel, title,{});
            }
        } else if(target == 'tab') {//点击最底层菜单
            if($(this.parentNode.parentNode).attr("id") == "nav") {//目前框架也不会走此处
                $(this).addClass("open").parent().siblings("li").each(function () {
                    $(this).find("a[class='open']").each(function () {
                        $(this).removeClass("open");
                    });
                });
            } else {
                var li = $(this).closest(".mz-open"); //目前点击二级菜单时以及菜单class为mz-open
                li.siblings("li").each(function () {//siblings 同胞元素 除去自己  所有的一级菜单如果有class=on 则去除 只有此处加上
                    $(this).find("a[class='open']").each(function () {
                        $(this).removeClass("open");
                    });
                });
                li.children("a").addClass("open");
            }
            //在 class="mainbar' body 加载页面
            $('.mainbar', 'body').ajaxUrl({url: url, data: {}});
        }
        //点击时的样式的判断 点击
        var uls = $(this).next("ul");
        if(uls.length == 0) {//判断是否有ul 当前点击的 li  a 下
            return;
        }
        var parent = $(this).parent();
        if(parent.hasClass("mz-open")) {
            uls.slideUp(200);
            parent.removeClass("mz-open");
            $(this).find("span > i").removeClass("icon-arrow-down").addClass("icon-chevron-right");
        } else {
            uls.slideDown(200);
            parent.addClass("mz-open");
            $(this).find("span > i").removeClass("icon-chevron-right").addClass("icon-arrow-down");
            $(this.parentNode).siblings("li.mz-open").each(function (e) {
                $(this).removeClass("mz-open");
                $(this).find("ul").slideUp(200);

            });
        }
        return false;
    }
    function _geneateTrees(data){
        var uls = '';
        for(var i= 0,len =data.length;i<len;i++) {
            var item = data[i];
            var li = '<li class=""><a href="#"><i class="icon-list-alt"></i>' + item.name +
                '<span class="pull-right"><i class="icon-chevron-right"></i></span></a><ul>';
            if(item.children && item.children.length > 0){
                for(var j= 0,jlen = item.children.length;j<jlen;j++) {
                    var jItem = item.children[j];
                    if(jItem.children && jItem.children.length > 0) {
                        alert("系统不支持多级菜单加载！");
                        window.location.href = basepath + "/login";
                        return;
                    }
                    li += '<li><a target="tab" href="' + (basepath + jItem.href) + '">' + jItem.name + '</a></li>';
                }
            }
            li += "</ul></li>";
            uls += li;
        }
        return uls;
    }
    /* modal path starts*/
    $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
        '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
        '<div class="progress progress-striped active">' +
        '<div class="progress-bar" style="width: 100%;"></div>' +
        '</div>' +
        '</div>';
    $.fn.modalmanager.defaults.resize = true;
    /* modal patch ends*/

    /* scroll top top */
    $(".totop").hide();
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) $('.totop').slideDown();
        else $('.totop').slideUp();
    });

    $('.totop a').click(function (e) {
        e.preventDefault();
        $('body,html').animate({scrollTop: 0}, 500);
    });
    /*$('body').on('hidden.bs.modal', '.modal', function () {
        $(this).empty();
    });*/
})(jQuery);

/*
$("body").on("click","#nav123123 a", function (e) {
    var mz_index = $(this).attr("mz-index");
    if(mz_index) {
        //$(this).off("click");
        return;
    }
    e.preventDefault();

    var href = $(this).attr("href");
    var rel = $(this).attr("rel");
    if(rel == 'modal') {
        if(href.substring(0,1) == '#') {
            $(href).modal();
        }
        return;
    }

    if(href && href != '#') {
        if($(this.parentNode.parentNode).attr("id") == "nav") {
            $(this).addClass("open").parent().siblings("li").each(function () {
                $(this).find("a[class='open']").each(function () {
                    $(this).removeClass("open");
                });
            });
        } else {
            var li = $(this).closest(".mz-open");
            li.siblings("li").each(function () {
                $(this).find("a[class='open']").each(function () {
                    $(this).removeClass("open");
                });
            });
            li.children("a").addClass("open");
        }
        $(".mainbar").load(href, function () {
            _initSwitch();
        });
    }

    var uls = $(this).next("ul");
    if(uls.length == 0) {
        return;
    }
    var parent = $(this).parent();
    if(parent.hasClass("mz-open")) {
        uls.slideUp(200);
        parent.removeClass("mz-open");
        $(this).find("span > i").removeClass("icon-arrow-down").addClass("icon-chevron-right");
    } else {
        uls.slideDown(200);
        parent.addClass("mz-open");
        $(this).find("span > i").removeClass("icon-chevron-right").addClass("icon-arrow-down");
        $(this.parentNode).siblings("li.mz-open").each(function (e) {
            $(this).removeClass("mz-open");
            $(this).find("ul").slideUp(200);

        });
    }
});
*/
/* Navigation */

$(document).ready(function() {

    $(window).resize(function () {
        if ($(window).width() >= 765) {
            $(".sidebar #nav").slideDown(350);
        }
        else {
            $(".sidebar #nav").slideUp(350);
        }
    });
});
$(document).ready(function(){
    $(".sidebar-dropdown a").on('click',function(e){
        e.preventDefault();

        if(!$(this).hasClass("open")) {
            // hide any open menus and remove all other classes
            $(".sidebar #nav").slideUp(350);
            $(".sidebar-dropdown a").removeClass("open");

            // open our new menu and add the open class
            $(".sidebar #nav").slideDown(350);
            $(this).addClass("open");
        }

        else if($(this).hasClass("open")) {
            $(this).removeClass("open");
            $(".sidebar #nav").slideUp(350);
        }
    });

});

/* Widget close */

$('.wclose').click(function(e){
    e.preventDefault();
    var $wbox = $(this).parent().parent().parent();
    $wbox.hide(100);
});

/* Widget minimize */

$('.wminimize').click(function(e){
    e.preventDefault();
    var $wcontent = $(this).parent().parent().next('.widget-content');
    if($wcontent.is(':visible'))
    {
        $(this).children('i').removeClass('icon-chevron-up');
        $(this).children('i').addClass('icon-chevron-down');
    }
    else
    {
        $(this).children('i').removeClass('icon-chevron-down');
        $(this).children('i').addClass('icon-chevron-up');
    }
    $wcontent.toggle(500);
});