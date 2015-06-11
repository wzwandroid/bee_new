/**
 * 参照bootstrap底层函数 重写
 * Created by mzllon on 2014/8/27.
 */
(function ($) {
    $.mzdialog = {
        _op: {
            height: 300,
            width: 580,
            minH: 40,
            minW: 50,
            total: 20,
            keyboard:true,
            backdrop:'static',
            max: false,
            resizable: true,
            drawable: true,
            maxable: true,
            minable: true,
            fresh: true,
            onShown:{},
            onHidden:{},
            defaultBtns:false,
            formSubmit:true,
            buttons:null
        },
        _current: null,
        _zIndex: 42,
        getCurrent: function () {
            return this._current;
        },
        /**
         * 打开一个模态框
         * @param url 请求的地址
         * @param dlgid 对话框唯一id 一般为空
         * @param title 对话框标题
         * @param options 其它可选项
         */
        open: function (url, dlgid, title, options) {
            var op = $.extend({}, $.mzdialog._op, options);
            if(!dlgid) dlgid = '';
            var dialog = $("body").data(dlgid);
            if(dialog && dialog.length > 0){
                if(op.fresh || url != dialog.data('url')){
                    dialog.find('.modal-header').find('h3').html(title);
                    dialog.data('url', url);
                    var jDialogContent = $('.modal-body', dialog);
                    //load data
                    jDialogContent.loadUrl(url, {});
                }
                if(dialog.is(':hidden')) {
                    dialog.modal({
                        width: op.width,
                        height: op.height
                    });
                }
                return;
            }
            // null == undefined :true null === undefined : false
            if(dlgid === undefined || dlgid == '') {
                $('body>div.modal').remove();
            }
            dlgid = dlgid || MZ.guid('', false);
            $('body').append(this._init(op.backdrop));
            dialog = $(">.modal:last-child", "body");
            dialog.data('id', dlgid);
            dialog.data('url', url);

            dialog.find('.modal-header').find('h3').html(title);
            $('body').data(dlgid, dialog);
            var jDialogContent = $('.modal-body', dialog);

            var $_modal_footer = dialog.find('.modal-footer');
            if(op.defaultBtns==true){
                $_modal_footer.append('<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>');
                var $button;
                if(op.formSubmit) {
                    $button= $('<button type="submit" class="btn btn-primary">提交</button>');
                    $button.appendTo($_modal_footer).on('click', function () {
                        jDialogContent.find('form').submit();
                    });
                } /*else {
                    $button = $('<button type="button" class="btn btn-primary" rel="callback">确定</button>');
                    $button.appendTo($_modal_footer).on('click', function (event) {
                        var cb = $button.attr('callback');
                        if(cb && $.isFunction(cb)) cb(event);
                    });
                }*/
            } else {
                var _btns=  op.buttons;
                if(_btns && _btns.length > 0) {
                    $.each(_btns, function (i, button) {
                        var $btn = $('<button class="btn"></button>').addClass(button.addClass ? button.addClass : 'btn-default').html(button.text)
                            .attr('id', button.id ? button.id : 'dlg-btn-' + i);
                        if(button.close){
                            $btn.attr('data-dismiss', 'modal');
                        }
                        $btn.appendTo($_modal_footer).on('click', function (e) {
                            if($.isFunction(button.onClick)) {
                                button.onClick($btn, dialog);
                            }
                        });
                    })
                }
            }
            $.mzdialog._current = dialog;
            if(url.indexOf('#')==0) {
                //初始化dialog 添加 header和footer
                jDialogContent.append($(url).show());
                $.mzdialog._initDialog(dialog, op);
            } else {
                //load remote data
                jDialogContent.loadUrl(url, {}, function () {
                    //初始化dialog 添加 header和footer
                    $.mzdialog._initDialog(dialog, op);
                });
            }
            return dialog;
        },
        reload: function (url, options) {
            var op = $.extend({data: {}, dlgId: "", callback: null}, options);
            var dialog = (op.dlgId && $('body').data(op.dlgId)) || this._current;
            if(dialog) {
                var jDialogContent = $('.modal-body', dialog);
                jDialogContent.ajaxUrl({
                    url:url, data:op.data, callback:function (responseData) {
                        dialog.modal({
                            width: op.width,
                            height: op.height,
                            keyboard:op.keyboard,
                            backdrop:op.backdrop
                        });
                        dialog.on('hidden.bs.modal', function () {
                            if (op.onHidden && $.isFunction(op.onHidden)) {
                                op.onHidden(dialog);
                            }
                            $.mzdialog.close(dialog);
                        });
                        if ($.isFunction(op.callback)) op.callback(responseData);
                    }
                });
            }
        },
        close: function (dialog) {
            if(typeof dialog == 'string') dialog = $('body').data(dialog);
            dialog.modal('destroy');
            $('body').removeData($(dialog).data('id'));
            $(dialog, 'body').remove();
        },
        closeCurrent: function () {
            this.close(this._current);
        },
        _init: function (backdrop) {
            var modal='';
            modal+='<div class="modal fade" data-backdrop="'+backdrop+'" style="display: none" tabindex="-1">';
            modal += '<div class="modal-header">';
            //以后可能放放大或者重置等按钮
            modal += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
            modal += '<h3></h3>';
            modal += '</div>';
            modal += '<div class="modal-body">';
            modal += '</div>';
            modal += '<div class="modal-footer">';
            modal += '</div>';
            modal += "</div>";
            return modal;
        },
        _initDialog: function (dialog,op) {
            dialog.modal({
                width: op.width,
                height: op.height,
                keyboard:op.keyboard,
                backdrop:op.backdrop
            });
            dialog.on('shown.bs.modal', function () {
                if (op.onShown && $.isFunction(op.onShown)) {
                    op.onShown(dialog);
                }
            });
            dialog.on('hidden.bs.modal', function () {
                if (op.onHidden && $.isFunction(op.onHidden)) {
                    op.onHidden(dialog);
                }
                //$(this).empty();
//                $('.modal-body', this).empty();
                $.mzdialog.close(dialog);
            });
        }
    };
})(jQuery);

function dialogAjaxDone(responseData) {
    var json = MZ.jsonEval(responseData);
    MZ.ajaxDone(json);
    if(json[MZ.keys.successKey] == MZ.successValue.success) {
        if(json[MZ.ajaxMsg.keys.callbackType] == MZ.ajaxMsg.values.callbackType.closedAndRefresh) {
            $.mzdialog.closeCurrent();
            var basepath = MZ.getFullPath();
            var url = json.forwardUrl;
            if(url.indexOf(basepath) < 0) {
                url = basepath + url;
            }
            $('.mainbar', 'body').loadUrl(url, {}, null);
        } else if(json[MZ.ajaxMsg.keys.callbackType] == MZ.ajaxMsg.values.callbackType.closedAndNoRefresh) {
            $.mzdialog.closeCurrent();
        }
    }
}








