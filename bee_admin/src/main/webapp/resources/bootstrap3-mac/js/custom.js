/*inner function*//*

function _initSwitch(){
    $(".mainbar table").find("div.switch").each(function (idx,item) {
        var $item = $(item);
        $item.on('switch-change', function (e, data) {
            var purl = $item.attr("param-url");
            if(purl === undefined || purl == '') {
                return;
            }
            var checked = data.value;
            var ptrue = $item.attr("param-true");
            var pfalse = $item.attr("param-false");
            var name = $item.attr("param-name");
            var idx = purl.indexOf("?");
            if(checked) {
                if(pfalse!='') {
                    if(idx>=0) {purl += "&" + name + "=" + pfalse;} else {purl += "?" + name + "=" + pfalse;}
                }
            } else {
                if(ptrue != '') {
                    if(idx >=0) { purl += "&" + name + "=" + ptrue;} else {purl += "?" + name + "=" + ptrue;}
                }
            }
            MZ.debug(purl);
            var requestType = $item.attr("param-method") || 'get';
            $.ajax({
                type: requestType,
                url: purl,
                dataType: 'json',
                success: function (ajaxMsg) {
                    noty({
                        text: ajaxMsg.message,
                        layout: 'topCenter',
                        type: ajaxMsg.success ? 'success' : 'error',
                        timeout: 2000
                    });
                }
            });
        });
        $item.bootstrapSwitch();
    });
}

*/
/* JS *//*


(function ($) {
    */
/**
     * 获得项目的觉得路径地址
     * @return {}
     *//*

    $.getFullPath = function(){
        var www = window.document.location.href;
        var pathname = window.document.location.pathname;
        var pos = www.indexOf(pathname);
        var lochost = www.substring(0,pos);
        var project = pathname.substring(0,pathname.substr(1).indexOf('/') + 1);
        return lochost + project;
    }
})(jQuery);

*/
/* bind table check all *//*

$(function () {
    $("body").on("click", "th[class='center'] :checkbox", function (e) {
        var checked = $(this).is(":checked");
        var thead = $(this).closest("thead");
        var tbody = thead.next("tbody");
        if(tbody.length <= 0) {
            return;
        }
        var tds = tbody.find("td[class='center']");
        tds.each(function () {
            var tr = $(this).parent();
            $(this).find(":checkbox").prop("checked", checked);
            if(checked) {
                tr.addClass("mz-table-highlight");
            } else{
                tr.removeClass("mz-table-highlight");
            }

        });
    });
    $("body").on("click", "tbody>tr>td[class='center']>label>:checkbox", function (e) {
        var checked = $(this).is(":checked");
        var tr = $(this).closest("tr");
        var theadChk = tr.parent().prev().find("th[class='center']>label>:checkbox");
        if(checked) {
            tr.addClass("mz-table-highlight");
            var checkAll = true;
            var checkboxes = tr.siblings("tr").find("td>label>:checkbox");
            for(var i= 0,len=checkboxes.length;i<len;i++) {
                if(!$(checkboxes[i]).is(":checked")) {
                    checkAll = false;
                    break;
                }
            }
            if(checkAll) {
                theadChk.prop("checked", checked);
            }
        } else {
            theadChk.prop("checked", checked);
            tr.removeClass("mz-table-highlight");
        }
    });

    $(".mainbar").on("click",".mz-toolbar .mz-add", function (e) {
        var rel = $(this).attr("rel");
        if(rel === "modal") {
            mzModal(this,$(this).attr("href"));
        }
    });
    $(".mainbar").on("click", ".mzGridTbody .btn", function (e) {
        e.preventDefault();
        var $this = $(this);
        if($this.attr("target") == 'dialog') {
            mzModal($this, $this.attr("href"), "");
        }

    });
    function mzModal(target,href,modalType){
        if(href.substring(0,1) == '#') {
            $(href).modal();
        } else {
            var $target = $(target)
            var height = $target.attr("height");
            var width = $target.attr("width");

            var intW = parseInt(width);
            var intH = parseInt(height);
            if(isNaN(intW)) {
                intW = 650;
            }
            if(isNaN(intH)) {
                intH = 350;
            }
            var bindModal = $target.data("bindModal");
            if(bindModal) {
                bindModal.empty();
            } else{
                bindModal = $("<div class='modal fade' data-backdrop='static' style='display: none' tabindex='-1'/>");
                $(".mainbar").append(bindModal);
                $target.data("bindModal", bindModal);
            }
            if(bindModal.attr("aria-hidden") == true) {
                return;
            }

            setTimeout(function () {
                $.get(href, 'html', function (data) {
                    bindModal.append(data).modal({
                        width: intW,
                        height: intH
                    });
                    bindModal.on("shown.bs.modal", function (ev) {
                        $(this).find(".modal-footer>button[ajaxSubmit='true'],.modal-footer>a[ajaxSubmit='true']").on("click", function (e) {
                            var form = bindModal.find("form");
                            $.ajax({
                                type: form.attr("method"),
                                dataType: "json",
                                data: form.serialize(),
                                url: form.attr("action"),
                                cache: false,
                                success: function (ajaxMsg) {
                                    noty({
                                        text: ajaxMsg.message,
                                        layout: 'topCenter',
                                        type: ajaxMsg.success?'success':'error',
                                        timeout:2000
                                    });
                                    if (ajaxMsg.success) {
                                        if (ajaxMsg.forwardUrl) {
                                            bindModal.on("hidden.bs.modal", function (e) {
                                                var

                                                    url = ajaxMsg.forwardUrl;
                                                if (modalType == 'loadByPager') {
                                                    var $pagination = $target.closest(".widget-content").find(".mz-pages>ul.pagination");
                                                    if ($pagination.length > 0) {
                                                        var pageNo = $pagination.attr("currentpage");
                                                        var pageSize = $pagination.attr("pagenumshown");
                                                        var idx = url.indexOf("?");
                                                        if (idx >= 0) {
                                                            url += "&pageNo=" + pageNo + "&pageSize=" + pageSize;
                                                        } else {
                                                            url += "?pageNo=" + pageNo + "&pageSize=" + pageSize;
                                                        }
                                                    }
                                                }
                                                $(".mainbar").load($.getFullPath() + url, function () {
                                                    _initSwitch();
                                                });
                                            });
                                        }
                                        bindModal.modal("hide");
                                    }
                                }
                            });
                        });
                    });
                });
            },500);
        }
    }

    $(".mainbar").on("click", ".mz-toolbar .mz-edit,.mz-toolbar .mz-view", function (e) {
        var $this = $(this);
        var $table = $this.closest(".mz-toolbar").siblings("table");
        var tbodyChks = $table.find("tbody>tr>td[class='center']>label>:checkbox");
        var num = 0;
        var $checkbox;
        for (var i = 0, len = tbodyChks.length; i < len; i++) {
            if (num >= 2) {
                noty({
                    text: '不能同时选择多条记录!',
                    layout: 'center',
                    type: 'warning',
                    modal: true
                });
                return;
            }
            if ($(tbodyChks[i]).is(":checked")) {
                $checkbox = $(tbodyChks[i]);
                num++;
            }
        }
        if (num == 0) {
            noty({
                text: '必须选择一条记录!',
                layout: 'center',
                type: 'warning',
                modal: true
            });
            return;
        }
        var target = $checkbox.closest("tr").attr("target");
        var targetValue = $checkbox.val();

        var href = $this.attr("href");
        var idx = href.indexOf("{" + target + "}");
        if (idx >= 0) {
            href = href.replace("{" + target + "}", targetValue);

        }
        var rel = $this.attr("rel");
        if (rel === "modal") {
            mzModal(this, href,"loadByPager","");
        }
    });
    $(".mainbar").on("click", ".mz-toolbar .mz-delete", function (e) {
        var $this = $(this);
        var $table = $this.closest(".mz-toolbar").siblings("table");
        var tbodyChks = $table.find("tbody>tr>td[class='center']>label>:checkbox");
        var num = 0;
        var $checkboxes=[];
        for (var i = 0, len = tbodyChks.length; i < len; i++) {
            if ($(tbodyChks[i]).is(":checked")) {
                $checkboxes.push($(tbodyChks[i]));
                num++;
            }
        }
        if (num == 0) {
            noty({
                text: '请选择信息!',
                layout: 'center',
                type: 'warning',
                modal: true
            });
            return;
        }
        var target = $this.attr("delIds");
        var targetValue = "";
        for(var i = 0,len = $checkboxes.length;i  < len;i++) {
            targetValue += $($checkboxes[i]).val()+",";
        }
        targetValue = targetValue.substring(0, targetValue.length - 1);
        var href = $this.attr("href");
        var rel = $this.attr("rel");
        if(rel == "selectedTodo") {
            var n = noty({
                text: $this.attr("title"),
                type: "warning",
                layout: "center",
                theme: 'defaultTheme',
                modal: true,
                buttons: [
                    {
                        addClass: 'btn btn-primary',
                        text: '确认',
                        onClick: function ($noty) {
                            $noty.close();
                            $.ajax({
                                type: "get",
                                dataType: "json",
                                data: target + "=" + targetValue,
                                url: href,
                                cache: false,
                                success: function (ajaxMsg) {
                                    var url = ajaxMsg.forwardUrl;
                                    var $pagination = $this.closest(".widget-content").find(".mz-pages>ul.pagination");
                                    if ($pagination.length > 0) {
                                        var trs = $this.closest(".mz-toolbar").next(".table").children("tbody").children();
                                        var checkedAll = true;
                                        for(var i= 0,len = trs.length;i < len;i++){
                                            if(!$(trs[i]).is(":checked")) {
                                                checkedAll = false;
                                                break;
                                            }
                                        }

                                        var pageNo = $pagination.attr("currentpage");
                                        var pageSize = $pagination.attr("pagenumshown");
                                        if(checkedAll) {
                                            var intPN = parseInt(pageNo);
                                            intPN -= 1;
                                            pageNo = intPN;
                                        }
                                        var idx = url.indexOf("?");
                                        if (idx >= 0) {
                                            url += "&pageNo=" + pageNo + "&pageSize=" + pageSize;
                                        } else {
                                            url += "?pageNo=" + pageNo + "&pageSize=" + pageSize;
                                        }
                                    }
                                    if (ajaxMsg.success) {
                                        if (ajaxMsg.forwardUrl) {
                                            noty({
                                                text: ajaxMsg.message,
                                                layout: 'topCenter',
                                                type: 'success',
                                                timeout: 2000
                                            });
                                            $(".mainbar").load($.getFullPath() + ajaxMsg.forwardUrl, function () {
                                                _initSwitch();
                                            });
                                        }
                                    } else {
                                        noty({
                                            text: ajaxMsg.message,
                                            layout: 'topCenter',
                                            type: 'error',
                                            timeout: 2000
                                        });
                                    }
                                }
                            });
                        }
                    },
                    {
                        addClass: 'btn btn-danger',
                        text: '取消',
                        onClick: function ($noty) {
                            $noty.close();
                        }
                    }
                ]
            });
        }
    });

    //Pager
    $(".mainbar").on("click", ".mz-pages>ul>li:not(.disabled)>a", function (e) {
        var $this = $(this);
        var $ul = $this.closest("ul");
        var url = $ul.attr("url");
        var pagenumshown = $ul.attr("pagenumshown");
        var currentpage = $ul.attr("currentpage");
        var pageNo;
        var $li = $this.parent();
        if($li.hasClass("mz-next")) {
             pageNo = parseInt(currentpage);
            pageNo += 1;
        } else if($li.hasClass(".mz-prev")) {
            pageNo = parseInt(currentpage);
            pageNo -= 1;
        } else if($li.hasClass("mz-first")){
            pageNo = 1;
        } else if($li.hasClass("mz-last")){
            currentpage = $li.attr("last");
            pageNo = parseInt(currentpage);
        } else {
            currentpage = $this.text();
            pageNo = parseInt(currentpage);
        }
        if(url.indexOf("?") >= 0) {
            url += "&pageNo=" + pageNo + "&pageSize=" + pagenumshown;
        } else {
            url += "?pageNo=" + pageNo + "&pageSize=" + pagenumshown;
        }
        $(".mainbar").load(url, function () {
            _initSwitch();
        });
    });

    $(".mainbar").on("click", ".buttonActive>:submit", function (e) {
        e.preventDefault();
        var $form = $(this).closest("form");
        var params = $form.serialize();
        var action = $form.attr("action");
        if(action.indexOf("?") >= 0) {
            action += "&" + params;
        } else {
            action += "?" + params;
        }
        $(".mainbar").load(action, function () {
            _initSwitch();
        });

    });
});

*/
/* Navigation *//*


$(document).ready(function(){

  $(window).resize(function()
  {
    if($(window).width() >= 765){
      $(".sidebar #nav").slideDown(350);
    }
    else{
      $(".sidebar #nav").slideUp(350); 
    }
  });


  */
/*$("#nav li > a").on('click',function(e){
      if($(this).parent().hasClass("has_sub")) {
        e.preventDefault();
      }   

      if(!$(this).hasClass("subdrop")) {
        // hide any open menus and remove all other classes
        $("#nav li ul").slideUp(350);
        $("#nav li a").removeClass("subdrop");
        
        // open our new menu and add the open class
        $(this).next("ul").slideDown(350);
        $(this).addClass("subdrop");
      }
      
      else if($(this).hasClass("subdrop")) {
        $(this).removeClass("subdrop");
        $(this).next("ul").slideUp(350);
      }

  });*//*


    $("body").on("click","#nav a", function (e) {
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
/* $("#nav a").on("click", function (e) {
        var mz_index = $(this).attr("mz-index");
        if(mz_index) {
            //$(this).off("click");
            return;
        }
        e.preventDefault();

        var hasClass = $(this).parent().hasClass("has_sub");
        var uls = $(this).next("ul");
        if(uls.length > 0) {//表示存在子节点
            if(hasClass) {

            }
            if (!hasClass) {
                uls.slideDown(200);
                $(this).
            } else {
                uls.slideUp(200);
            }
        } else {
            //uls.slideToggle(200);
        }

        //$(this).parent().slideUp(200);

        var href = $(this).attr("href");
        if(href && href != '#') {
            var mz_index = $(this).attr("mz-index");
            if(!mz_index) {
                $(".mainbar").load(href);
                e.preventDefault();
            }
        }
    });*//*

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

*/
/* Widget close *//*


$('.wclose').click(function(e){
  e.preventDefault();
  var $wbox = $(this).parent().parent().parent();
  $wbox.hide(100);
});

*/
/* Widget minimize *//*


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

*/
/* Calendar *//*


  $(document).ready(function() {
  
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    
    $('#calendar').fullCalendar({
      header: {
        left: 'prev',
        center: 'title',
        right: 'month,agendaWeek,agendaDay,next'
      },
      editable: true,
      events: [
        {
          title: 'All Day Event',
          start: new Date(y, m, 1)
        },
        {
          title: 'Long Event',
          start: new Date(y, m, d-5),
          end: new Date(y, m, d-2)
        },
        {
          id: 999,
          title: 'Repeating Event',
          start: new Date(y, m, d-3, 16, 0),
          allDay: false
        },
        {
          id: 999,
          title: 'Repeating Event',
          start: new Date(y, m, d+4, 16, 0),
          allDay: false
        },
        {
          title: 'Meeting',
          start: new Date(y, m, d, 10, 30),
          allDay: false
        },
        {
          title: 'Lunch',
          start: new Date(y, m, d, 12, 0),
          end: new Date(y, m, d, 14, 0),
          allDay: false
        },
        {
          title: 'Birthday Party',
          start: new Date(y, m, d+1, 19, 0),
          end: new Date(y, m, d+1, 22, 30),
          allDay: false
        },
        {
          title: 'Click for Google',
          start: new Date(y, m, 28),
          end: new Date(y, m, 29),
          url: 'http://google.com/'
        }
      ]
    });
    
  });





*/
/* Support *//*


$(document).ready(function(){
  $("#slist a").click(function(e){
     e.preventDefault();
     $(this).next('p').toggle(200);
  });
});

*/
/* Scroll to Top *//*



  $(".totop").hide();

  $(function(){
    $(window).scroll(function(){
      if ($(this).scrollTop()>300)
      {
        $('.totop').slideDown();
      } 
      else
      {
        $('.totop').slideUp();
      }
    });

    $('.totop a').click(function (e) {
      e.preventDefault();
      $('body,html').animate({scrollTop: 0}, 500);
    });

  });

*/
/* jQuery Notification *//*


$(document).ready(function(){

  //setTimeout(function() {noty({text: '<strong>Howdy! Hope you are doing good...</strong>',layout:'topRight',type:'information',timeout:15000});}, 7000);

  //setTimeout(function() {noty({text: 'This is an all in one theme which includes Front End, Admin & E-Commerce. Dont miss it. Grab it now',layout:'topRight',type:'alert',timeout:13000});}, 9000);

});





*/
/* Modal fix *//*


$('.modal').appendTo($('body'));

*/
/* Pretty Photo for Gallery*//*


jQuery("a[class^='prettyPhoto']").prettyPhoto({
overlay_gallery: false, social_tools: false
});*/



