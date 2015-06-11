/**
 * Created by mzllon on 2014/8/29.
 * 此处涉及闭包函数概念 此函数在index中 每次进入首页会执行 所有的函数 然后initUI 是内部函数
 */
function initUI(_bs) {
    var $box = $(_bs || document);
    //调用此js的地方都会预执行下列函数------下
    /**
     * 初始化dialog 给所有的按钮
     * 如新增删除等 a标签或者button标签添加click函数
     * 点击后的dialog样式等
     */
    initBSDialog($box);
    initForm($box); // 各种样式
    _initPager($box);
    _initTableGrid($box);
    //_initWminimize($box);//-------------------------
    _initRecrodDeleted($box);
    //_initDatetimePicker($box);
    //_initDlgSlkBack($box);
    //_initSelect($box);
    //_initUpdateStatus($box);
    _initMtree($box);
    //调用此js的地方都会预执行下列函数------上
    /* init datetime picker */
    function _initDatetimePicker($box) {
        if($.fn.datetimepicker) {
            $('div.date,input.date', $box).each(function () {
                var $this = $(this);
                var opts = {};
                if($this.attr('format')) opts.format = $this.attr('format');
                else opts.format = 'yyyy-mm-dd';
                if($this.attr('weekStart')) opts.weekStart = $this.attr('weekStart');
                if($this.attr('startDate')) opts.startDate = $this.attr('startDate');
                if($this.attr('endDate')) opts.endDate = $this.attr('endDate');
                if($this.attr('daysOfWeekDisabled')) opts.daysOfWeekDisabled = $this.attr('daysOfWeekDisabled');
                if($this.attr('autoclose')) opts.autoclose = $this.attr('autoclose');
                else opts.autoclose = true;
                if($this.attr('startView')) opts.startView = $this.attr('startView');
                if($this.attr('minView')) opts.minView = $this.attr('minView');
                if($this.attr('maxView')) opts.maxView = $this.attr('maxView');
                if($this.attr('todayBtn')) opts.todayBtn = eval($this.attr('todayBtn'))
                else opts.todayBtn =true;
                if($this.attr('todayHighlight')) opts.todayHighlight = $this.attr('todayHighlight');
                if($this.attr('keyboardNavigation')) opts.keyboardNavigation = $this.attr('keyboardNavigation');
                if($this.attr('forceParse')) opts.forceParse = $this.attr('forceParse');
                if($this.attr('minuteStep')) opts.minuteStep = $this.attr('minuteStep');
                if($this.attr('pickerPosition')) opts.pickerPosition = $this.attr('pickerPosition');
                if($this.attr('viewSelect')) opts.viewSelect = $this.attr('viewSelect');
                if($this.attr('showMeridian')) opts.showMeridian = $this.attr('showMeridian');
                if($this.attr('initialDate')) opts.initialDate = $this.attr('initialDate');
                opts.maxDateField = $this.attr('max-date-field') || '';
                opts.language = 'zh-CN';
                var show = $this.attr('onShow'),
                    hide = $this.attr('onHide'),
                    changeDate = $this.attr('onChangeDate'),
                    changeYear = $this.attr('onChangeYear'),
                    changeMonth = $this.attr('onChangeMonth'),
                    outOfRange = $this.attr('onOutOfRange');

                var _datetimepicker = $this.datetimepicker(opts);
                if(hide) {
                    hide = eval(hide || '');
                    _datetimepicker.on('hide',function(ev){
                        if($.isFunction(hide)) hide(ev);
                    });
                }

                if(changeDate) {
                    changeDate = eval(changeDate || '');
                    if($.isFunction(changeDate)) {
                        _datetimepicker.on('changeDate', function (ev) {
                            changeDate(ev);
                        });
                    }
                }
                if(changeYear) {
                    changeYear = eval(changeYear || '');
                    if($.isFunction(changeYear)) {
                        _datetimepicker.on('changeYear', function (ev) {
                            changeYear(ev);
                        });
                    }
                }
                if(changeMonth) {
                    changeMonth = eval(changeMonth || '');
                    if($.isFunction(changeMonth)) {
                        _datetimepicker.on('changeMonth', function (ev) {
                            changeMonth(ev);
                        });
                    }
                }
                if(outOfRange) {
                    outOfRange = eval(changeDate || '');
                    if($.isFunction(outOfRange)) {
                        _datetimepicker.on('outOfRange', function (ev) {
                            outOfRange(ev);
                        });
                    }
                }

                _datetimepicker.on('show', function (ev) {
                    if (show) {
                        show = eval(show || '');
                        if ($.isFunction(show)) show(ev);
                    }
                    //是否设定了时间控件最大值指向了某个标签-
                    if (opts.maxDateField != '') {
                        var $maxDateInput = $(opts.maxDateField, $box);
                        var maxdate = $maxDateInput.val();
                        if (maxdate != '') _datetimepicker.datetimepicker('setEndDate', maxdate);
                        $maxDateInput.on('hide', function (ev) {
                            maxdate = $maxDateInput.val();
                            if(maxdate < _datetimepicker.val()) {
                                _datetimepicker.val('').datetimepicker('show');
                            }
                        });
                    }
                });
            });
        }
    }

    /*function _initSwitch($box){
        $("div.switch",$box).each(function (idx,item) {
            var $item = $(item);
            $item.on('beforeClick', function () {
                alert('executing');
                $item.data('bft', false);
            });
            $item.on('switch-change', function (e, data) {
                e.preventDefault();
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
                var requestType = $item.attr("param-method") || 'get';
                var $n = $item.data("notiy.warning");
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
                *//*var n = noty({
                    text: $item.attr("title") || '确定要执行？',
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
                });*//*
                //$item.data("notiy.warning", n);

            });
            $item.bootstrapSwitch();
        });
    }*/

    //单条删除和多条删除
    function _initRecrodDeleted($box) {
        $('a[target="oneRecordDeleted"],button[target="oneRecordDeleted"]', $box).oneRecordDeleted();
        $('a[target="moreRecoredDeleted"],button[target="moreRecoredDeleted"]', $box).moreRecoredDeleted();
    }

    /* Widget minimize and close */
    function _initWminimize($box) {
        $('.wminimize', $box).off('click').on('click',function (e) {
            e.preventDefault();
            var $this = $(this);
            var $wcontent = $this.parent().parent().next('.widget-content');
            if ($wcontent.is(':visible')) {
                $this.children('i').removeClass('icon-chevron-up');
                $this.children('i').addClass('icon-chevron-down');
            } else {
                $this.children('i').removeClass('icon-chevron-down');
                $this.children('i').addClass('icon-chevron-up');
            }
            $wcontent.toggle(500);
        });
        $('.wclose', $box).off('click').on('click',function (e) {
            e.preventDefault();
            var $wbox = $(this).parent().parent().parent();
            $wbox.hide(100);
        });
    }

    /* init table grid
    * 目前主要初始化table list页面或者弹出框中的
    * */
    function _initTableGrid($box) {
        $('table.table', $box).each(function () {
            var $this = $(this);
            var $body = $this.find('>tbody');
            var $thead = $this.find('>thead');
            var $theadOfChk = $('>tr>th.center>label>:checkbox', $thead);
            var $trs = $body.find('>tr');
            var $firChks = $('>td.center>label>:checkbox', $trs);

            //行点击时候
            $trs.each(function () {
                var $tr = $(this);
                //var $ctd = $('>td.center', this);
//                $tr.off('click').on('click',function () {
                $tr.on('click',function () {
                    $trs.filter('.mz-table-highlight').removeClass('mz-table-highlight');
                    $tr.addClass('mz-table-highlight');
                    var sTarget = $tr.attr('target');
                    if (sTarget) {
                        if ($('#' + sTarget, $this).size() == 0) {
                            $this.prepend('<input id="' + sTarget + '" type="hidden"/>');
                        }
                        $('#' + sTarget, $this).val($tr.attr('rel'));
                    }
                });
            });
            //勾选时候 全选 反选
            $firChks.each(function () {
                var $ftd = $(this);
//                $ftd.off('click').on('click',function (eve) {
                $ftd.on('click',function (eve) {
//                    eve.stopPropagation();
                    var checked = $ftd.is(':checked');
                    if(checked) {
                        var allCheckd = true;
                        for (var i = 0, len = $firChks.length; i < len; i++) {
                            if (!($firChks.eq(i)).is(":checked")) {
                                allCheckd = false;
                                break;
                            }
                        }
                        if (allCheckd) {
                            $theadOfChk.prop('checked', 'checked');
                        }
                    } else {
                        $theadOfChk.prop('checked', '');
                    }
                });
            });
//            $theadOfChk.off('click').on('click',function (eve) {
            // 选中某行时候
            $theadOfChk.on('click',function (eve) {
//                eve.stopPropagation();
                if ($theadOfChk.is(':checked')) {
                    $firChks.each(function () {
                        var $chk = $(this);
                        if($chk.is(":enabled")) $chk.prop('checked', 'checked');
                    });
                } else {
                    $firChks.each(function () {
                        $(this).prop('checked', '');
                    });
                }
            });

            //extra detail view
            var isDataDetailView = $this.hasClass('dataDetailView');
            if(isDataDetailView) {
                //代表需要查看详细
                var $expanderTds = $(">td.row-expander", $trs);
                var detailview_url = $this.attr('data-detail-view'),
                    relId = $this.attr('relId'),
                    rowFresh = $this.attr('rowFresh');

                var tableWidth = $this.width(), thFirWidth = $thead.find('tr>td:first').width();
                if($expanderTds && $expanderTds.size() > 0) {
                    $expanderTds.each(function () {
                        var $td = $(this);
                        function _loadInnerTableView() {
                            var $closetTr = $td.closest('tr');
                            var tdCount = $td.siblings('td').size();
                            var _id = $closetTr.attr('rel');
                            var params = {};
                            params[relId] = _id;
                            var innerHtml = MZ.templates.dataDetailView.replaceAll('#colspan#', tdCount).
                                replaceAll('#width#', (tableWidth - thFirWidth - (tdCount) * 4)).replaceAll('#height#', 200).
                                replaceAll('#id#',_id);
                            var $innerHtml = $(innerHtml).insertAfter($closetTr);
                            $.ajax({
                                type: 'post',
                                url: detailview_url,
                                data: params,
                                dataType: 'html',
                                success: function (responseData) {
//                                    innerHtml = innerHtml.replaceAll('#detailView#', responseData);
                                    $('#ddv-' + $closetTr.attr('rel'), $innerHtml).html(responseData);

                                    _initPager($innerHtml);
                                    var $innerTable = $innerHtml.find('div.tableScroller>table');
                                    var $ul= $innerHtml.find("div.widget-foot");
                                    var _height = $innerTable.height();
                                    $innerHtml.find("td>div").height(_height+50);
                                }
                            });
                        }

                        $td.off('click').on('click', function () {
                            var hasRowExpand = $td.hasClass('row-expand'),//折叠
                                hasRowCollpase = $td.hasClass("row-collpase");//展开
                            if (hasRowExpand) {
                                if(eval(rowFresh||'')) {
                                    _loadInnerTableView();
                                }
                                $td.removeClass('row-expand').addClass('row-collpase');
                                $td.closest('tr').next("tr.row-detail-inserted").fadeIn('slow');
                                $td.find('i').removeClass('icon-plus').addClass('icon-minus');
                            } else if (hasRowCollpase) {
                                $td.removeClass('row-collpase').addClass('row-expand');
                                $td.closest('tr').next("tr.row-detail-inserted").fadeOut('slow');
                                $td.find('i').removeClass('icon-minus').addClass('icon-plus');
                            } else {
                                //未初始化,计算当前行有n个td
                                $td.addClass('row-collpase');
                                $td.find('i').removeClass('icon-plus').addClass('icon-minus');
                                _loadInnerTableView();
                            }
                        });
                    });
                }
            }
        });
        //init export
        $('a[target="mzExport"],button[target="mzExport"]', $box).each(function () {
            var $this = $(this);
            return $this.off('click').on('click', function (e) {
                e.preventDefault();
                var href = $this.attr('href'), existedParamed = href.indexOf('?') >= 0;
                if(existedParamed) href += '&';
                else href += '?';
                var $search = $this.closest('.mz-toolbar').prev('.mz-pager-search');
                var $form = $search.find('form');
                href += $form.serialize();
                if($this.attr('targetType')=='tab') window.open(href);
            });
        });
    }
    /* init pagination */
    function _initPager($box){
        $('div.mz-pages', $box).each(function () {
            var $this = $(this);
            $this.selfPager({
                targetType: $this.attr('targetType'),
                rel: $this.attr('rel'),
                totalCount: $this.attr('totalCount'),
                pageSize: $this.attr('pageSize'),
                pageNumShown: $this.attr('pageNumShown'),
                pageNo: $this.attr('pageNo')
            });
        });
    }

    function initForm($box) {
        $('form.required-validate', $box).each(function () {
            var $form = $(this);
            $form.validate({
                onsubmit: false,
                focusInvalid: false,
                focusCleanup: true,
                errorElement: "span",
                ignore: ".ignore,:hidden",
                invalidHandler: function (event, validator) {
                    var errors = validator.numberOfInvalids;
                    //alert(errors);
                },
                success: function ($error,ele) {
                    var $ele = $(ele);
                    var $formgroup = $ele.parents('.form-group'),$i=$ele.siblings('i'),$poptip=$ele.siblings('div.poptip');
                    $formgroup.removeClass('has-error').addClass('has-success');
                    $i.addClass('icon-ok').removeClass('icon-remove');
                    $poptip.hide();
                },
                errorPlacement: function ($error, $ele) {
                    var $formgroup = $ele.parents('.form-group'),$i=$ele.siblings('i'),$poptip=$ele.siblings('div.poptip');
                    $formgroup.removeClass('has-success').addClass('has-error');

                    if($i.size() ==0) {
                        $ele.after('<i class="icon-remove form-control-feedback"></i>');
                    } else {
                        if(!$i.removeClass('icon-ok'))$i.addClass('icon-remove');
                    }
                    if($poptip.length ==0 ){
                        $poptip = $('<div class="poptip"><span class="poptip-arrow poptip-arrow-left poptip-arrow-left-center"><em>◆</em><i>◆</i></span><div class="poptip-info"></div></div>');
                        $ele.after($poptip);
                    } else {
                        $poptip.show();
                    }
                    $error.appendTo('body');
                    $poptip.find('.poptip-info').text($error.text()).width($error.width());
                    $error.remove();

                    var position = $ele.position();
                    var pr = $ele.css('padding-right');
                    pr = pr.substring(0, pr.length - 2);
                    pr = parseInt(pr, 10);
                    pr += position.left + $ele.width();
                    pr += 20;
                    $poptip.css({top: 0,left: pr});
                }
            });
            $form.find('input[customvalid]').each(function () {
                var $input = $(this);
                $input.rules('add', {
                    customvalid: $input.attr('customvalid')
                });
            });
            $('input,select,textarea', $form).each(function () {
                var $this = $(this);
                //init other
                var $formgroup = $this.parents('div.form-group');//,$i=$this.siblings('i'),$poptip=$this.siblings('div.poptip');
                $formgroup.addClass('has-feedback');

                $this.focus(function () {
                    $this.siblings('div.poptip').hide();
                });
            });
        });
    }
    /**
     * 初始化dialog 给所有的按钮 弹出框
     * 如新增删除等 a标签或者button标签添加click函数
     * 点击后的dialog样式等
     * 用处：在框架中的 新增修改等按钮处：
     * 主要属性 title,href,target，width，height
     * warn:如果不符合规则一般是没有选中等则报错
     */
    function initBSDialog($box) {
        $('a[target="dialog"],button[target="dialog"]', $box).each(function () {
            //off('click') 移除click事件 防止每次 的click事件 移除后添加
            $(this).off('click').on('click',function (event) {
                var $this = $(this);
                event.preventDefault();//阻止提交事件
                var title = $this.attr('title');
                var dialogId = $this.attr('dialogId'); //此属性目前没用到，作为备用
                var options = {};
                var _w = $this.attr('width');
                var _h = $this.attr('height');
                _w = parseInt(_w, 10);
                _h = parseInt(_h, 10);
                if (isNaN(_w)) options.width = MZ.dialogDefault.width;
                else options.width = _w;
                if (isNaN(_h)) options.height = MZ.dialogDefault.height;
                else options.height = _h;
                options.backdrop = $this.attr('backdrop') || 'static';
                options.keyboard = eval($this.attr('keyboard') || 'true');
                options.fresh = eval($this.attr('fresh') || 'true');
                options.onHidden = eval($this.attr('onHidden') || '');
                options.onShown = eval($this.attr('onShown') || '');
                options.defaultBtns = eval($this.attr('defaultBtns') || 'true');
                options.formSubmit = eval($this.attr('formSubmit') || 'true');

                var $parent = $box.find('table');
                var url = unescape($this.attr('href').replaceTmById($parent));
                if(!url.isFinishedTm()) {
                    MZ.alertMsg.warning($this.attr('warn') || $.il8n.msg.alertSelectedMsg, {});
                    return;
                }
                //init dialog
                $.mzdialog.open(url, dialogId, title, options);
                return;
            });
        });
    }

    function _initDlgSlkBack($box){
        if($.fn.inputSearch) {
            $('a[inputSearchGroup]', $box).inputSearch();
        }
        if($.fn.multiInputSearch) {
            $('[multiInputSearch]:button', $box).multiInputSearch();
        }
    }

    function _initSelect($box) {
        if($.fn.linkage) {
            $('select[linkage]', $box).linkage();
        }
        if($.fn.combotree) {
            $(('input.combotree'), $box).each(function () {
                var $this = $(this);
                var options = {treeId: $this.data('tree-id'),
                    dblClickExpand: eval($this.data('db-click-expand') || '') || true,
                    isMultiSelect: eval($this.data('multi-select') || '') || false,
                    selectBoxWidth: $this.data('box-width') || $this.outerWidth(),
                    selectBoxHeight: $this.data('box-height') || 'auto',
                    valueField: $this.data('value-field') || 'id',
                    textField: $this.data('text-field') || 'name',
                    parentField: $this.data('parent-field') || 'pid',
                    simpleData: eval($this.data('simple-data') || '') || true,
                    children: $this.data('children') || 'children',
                    slide: $this.data('slide') || 'fast',
                    split: $this.data('split') || ',',
                    url: $this.data('url'),
                    params: eval($this.data('params')) || [],
                    nodes: eval($this.data('nodes')) || null,
                    asyncEnable: eval($this.data('async-enable')) || true,
                    ajaxType: $this.data('ajax-type') || 'post',
                    ajaxDataType: $this.data('ajax-data-type') || 'json',
                    fillParent: eval($this.data('fill-parent' || '')) || false,
                    initValue: $this.data('init-value') || ''
                };
                $this.combotree(options);
            });
        }
    }

    function _initUpdateStatus($box) {
        if($.fn.spanUpdateStatus) {
            $('tr>td>span.update-status', $box).each(function () {
                var $this = $(this);
                var onClass = $this.data('on-class'), offClass = $this.data('off-class'),
                    title = $this.attr('title'), idField = $this.data('id-field'), valueFiled = $this.data('value-field');
                var options = {offClass: offClass, onClass: onClass, title: title, idField: idField, valueFiled: valueFiled};
                $this.spanUpdateStatus(options);
            });
        }
    }

    function _initMtree($box){
        if($.fn.mtree) {
            return $('ul.mtree,div.mtree',$box).each(function () {
                var $this = $(this);
                var opts = {
                    expandAll: $this.data('expand-all')===undefined?true:eval($this.data('expand-all')),
                    simpleData: $this.data('simple-data')===undefined?true:eval($this.data('simple-data')),
                    onClick: $this.data('cb-click') || null,
                    onCheck: $this.data('cb-check') || null,
                    beforeClick:$this.data('cb-before-click')||null,
                    editable: $this.data('editable')===undefined?false:eval($this.data('editable')),
                    nodes: MZ.jsonEval($this.data("nodes")) || null,
                    treeId: $this.data('tree-id'),
                    dblClickExpand: $this.data('dblClickExpand')===undefined?true:eval($this.data('dblClickExpand')),
                    isMultiSelect: $this.data('multi-select')===undefined?false:eval($this.data('multi-select')),
                    valueField: $this.data('value-field') || 'id',
                    textField: $this.data('text-field') || 'name',
                    parentField: $this.data('parent-field') || 'pid',
                    children: $this.data('children') || 'children',
                    split: $this.data('split') || ',',
                    enableCheck: $this.data('chk-style')===undefined?false:eval($this.data('chk-style')),
                    isCheckedChildren:$this.data('check-children')===undefined?true:eval($this.data('check-children')),
                    isCheckedParent:$this.data('check-parent')===undefined?true:eval($this.data('check-parent')),
                    isUncheckedChildren:$this.data('uncheck-children')===undefined?true:eval($this.data('uncheck-children')),
                    isUncheckedParent:$this.data('uncheck-parent')===undefined?true:eval($this.data('uncheck-parent')),
                    async: {
                        enable: $this.data('async-enable')===undefined?false:eval($this.data('async-enable')),
                        url: $this.data('url') || null,
                        type: $this.data('type') || null,
                        params: $this.data('params') || null,
                        dataType: $this.data('data-type') || 'json'
                    }};
                return $this.mtree(opts);
            });
        }
    }
}



