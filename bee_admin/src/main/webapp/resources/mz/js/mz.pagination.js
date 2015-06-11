/**
 * Created by mzllon on 2014/8/31.
 *分页
 */
(function ($) {
    $.fn.extend({
        selfPager: function (options) {
            var pagerTemplate = '<div class="pagination pull-left">每页显示#pageSize#条记录，共#totalCount#条记录</div>';
            pagerTemplate += '<ul class="pagination pull-right">';
            pagerTemplate += '<li class="mz-first"><a href="javascript:;">首页</a><span>首页</span></li>';
            pagerTemplate += '<li class="mz-prev"><a href="javascript:;">上一页</a><span>上一页</span></li>';
            pagerTemplate += '#pagerNumTemplates#';
            pagerTemplate += '<li class="mz-next"><a href="javascript:;">下一页</a><span>下一页</span></li>';
            pagerTemplate += '<li class="mz-last"><a href="javascript:;">末页</a><span>末页</span></li>';
//            pagerTemplate += '<li class="redirct-to"><input type="text" size="4" value="#currentPage#"></li>';
            var settings = {
                first: 'li.mz-first',
                prev: 'li.mz-prev',
                next: 'li.mz-next',
                num: 'li.mz-num>a',
                last: 'li.mz-last',
                redirect: 'li.redirct-to',
                pagerNumTemplate: '<li class="#liClass#"><a href="javascript:void(0);">#pagerNum#</a></li>',
                pagerTemplate: pagerTemplate
            };
            return this.each(function () {
                var $this = $(this);
                var pagination = new Pagination(options);
                var interval = pagination.getInterval();
                var pageNumHtml='';
                for(var i = interval.start;i < interval.end;i ++) {
                    pageNumHtml += settings.pagerNumTemplate.replaceAll('#pagerNum#', i)
                        .replaceAll('#liClass#', i == pagination.getCurrentPage() ? 'mz-num active' : 'mz-num');
                }
                $this.html(settings.pagerTemplate.replaceAll('#pagerNumTemplates#', pageNumHtml).replaceAll('#currentPage#', pagination.getCurrentPage())
                    .replaceAll('#pageSize#',pagination.getPageSize()).replaceAll('#totalCount#',options.totalCount)).append('<div class="clearfix"></div>');
                var $first = $this.find(settings.first), $prev = $this.find(settings.prev),
                    $next = $this.find(settings.next), $last = $this.find(settings.last);
                if(pagination.prev()) {
                    $first.add($prev).find(">span").hide();
                    _bindEvent($prev, pagination.getCurrentPage() - 1, pagination.targetType(), pagination.rel());
                    _bindEvent($first, 1, pagination.targetType(), pagination.rel());
                } else {
                    $first.add($prev).addClass('disabled').find('>a').hide();
                }
                if(pagination.next()) {
                    $next.add($last).find('>span').hide();
                    _bindEvent($next, pagination.getCurrentPage() + 1, pagination.targetType(), pagination.rel());
                    _bindEvent($last, pagination.countTotalPage(), pagination.targetType(), pagination.rel());
                } else {
                    $next.add($last).addClass('disabled').find('>a').hide();
                }
                $this.find(settings.num).each(function (i) {
                    _bindEvent($(this), i + interval.start, pagination.targetType(), pagination.rel());
                });
                $this.find(settings.redirect).each(function () {
                    //emtpty()
                });

            });

            function _bindEvent($target,pageNum,targetType,rel) {
                $target.on('click', {pageNum: pageNum}, function (event) {
                    mzPagerFunc({targetType: targetType, rel: rel, data: {pageNo: event.data.pageNum}});
                    event.preventDefault();
                    return false;
                });
            }
        }
    });
    /**
     * 自定义分页对象
     * @param opts 分页参数
     * @constructor
     */
    var Pagination = function (opts) {
        this.opts = $.extend({
            targetType: "tab",	// tab, dialog
            rel: "", //用于局部刷新div id号
            totalCount: 0,
            pageSize: 10,
            pageNumShown: 10,
            pageNo: 1,
            callback: function () {
                return false;
            }
        }, opts);
    };
    $.extend(Pagination.prototype, {
        targetType: function () {
            return this.opts.targetType;
        },
        rel: function () {
            return this.opts.rel;
        },
        getInterval:function(){
            var ne_half = Math.ceil(this.opts.pageNumShown/2);
            var np = this.countTotalPage();
            var upper_limit = np - this.opts.pageNumShown;
            var start = this.getCurrentPage() > ne_half ? Math.max( Math.min(this.getCurrentPage() - ne_half, upper_limit), 0 ) : 0;
            var end = this.getCurrentPage() > ne_half ? Math.min(this.getCurrentPage()+ne_half, np) : Math.min(this.opts.pageNumShown, np);
            return {start:start+1, end:end+1};
        },
        countTotalPage: function () {
            if (this.opts.totalCount == 0) return 1;
            if (this.opts.totalCount <= this.opts.pageSize) return 1;
            return Math.floor((this.opts.totalCount - 1) / this.opts.pageSize + 1);
        },
        getPageSize: function () {
            return this.opts.pageSize;
        },
        getCurrentPage: function () {
            var curr = parseInt(this.opts.pageNo);
            if (isNaN(curr)) return 1;
            return curr;
        },
        prev: function () {
            return this.getCurrentPage() > 1;
        },
        next: function () {
            return this.getCurrentPage() < this.countTotalPage();
        }
    });
})(jQuery);

function mzPagerFunc(options) {
    var _options = $.extend({targetType: '', rel: '', data: {pageNo: '',pageSize:''}, callback: null}, options);
    var $form,$box;
    if(_options.targetType == 'dialog') {
        var $parent = $.mzdialog.getCurrent();
        $box = $('.modal-body', $parent);
    } else if(_options.targetType == 'tab') {
        if(_options.rel != '') {
            $box = $('#' + _options.rel);
        } else {
            $box = $('div.mainbar');
        }
    }
    $form = $box.find('.mz-pager-search form');
    if($form.length > 0) {
        var _$pageNo = $form.find(':hidden[name="pageNo"]');
        var _$pageSize = $form.find(':hidden[name="pageSize"]');
        if(_options.data.pageNo && _$pageNo.size()>0) _$pageNo.val( _options.data.pageNo);
        else $form.append('<input type="hidden" name="pageNo" value="' + _options.data.pageNo + '">');
        var params = $form.serializeArray();
        $box.ajaxUrl({url: $form.attr('action'), data: params, callback: options.callback});
    }

    /*var $parent = _options.targetType == 'dialog'? $.mzdialog.getCurrent():$('div.mainbar');
    var $form,$box;
    if(_options.rel) {
        //TODO EMPTY
        $box = $('#' + options.rel);
        $form = $box.find('div.mz-pager-search form');
    } else {
        //直接查找div.mainbar div.mz-pager-search
        var $box = $('div.mainbar');
        var $form = $('div.mz-pager-search',$box).find('form');
    }
    var $form = $parent.find('div.mz-pager-search form');
    if($form.length >0) {
        var _$pageNo = $form.find(':hidden[name="pageNo"]');
        var _$pageSize = $form.find(':hidden[name="pageSize"]');
        console.info(_$pageNo, _$pageSize);
        if(_options.data.pageNo && _$pageNo.size()>0) _$pageNo.val( _options.data.pageNo);
        else $form.append('<input type="hidden" name="pageNo" value="' + _options.data.pageNo + '">');
        //if(_options.data.pageSize && _$pageSize.length > 0) _$pageSize.val(_options.data.pageSize);
        //else $form.append('<input type="hidden" name="pageSize " value="' + _options.data.pageSize + '">');
        var params = $form.serializeArray();
//            $box.loadUrl($form.attr('action'), params, options.callback);
        $parent.ajaxUrl({url: $form.attr('action'), data: params, callback: options.callback});
    }*/
}

