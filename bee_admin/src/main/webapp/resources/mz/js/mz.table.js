/**
 * 暂时没用主要作为 bootstrap的swith开关用
 * Created by tomi on 2014/9/14.
 */
(function ($) {
    /**
     * 为表格的span标签增加状态修改的方法，该方法仅仅支持状态值只有两种值的情况，对于多种值的状态修改目前暂未支持。
     * @param options JSON对象，可选属性:offClass,onClass,title,ajaxType,idField,valueFiled,on,off,url
     * @returns {*|HTMLElement} 返回jQuery对象
     */
    $.fn.spanUpdateStatus = function (options) {
        var opts = $.extend({'offClass': 'label-danger', 'onClass': 'label-success',
            'title': '确定要修改状态', 'ajaxType': 'post', 'idField': 'id', 'valueFiled': 'status'}, options);
        var $this = $(this),url = $this.data('url'),
            off = MZ.jsonEval($this.data('off')),on = MZ.jsonEval($this.data('on'));
        if ($.isEmptyObject(on) || $.isEmptyObject(off) || url == '') {
            return $this;
        }
        var offClz = opts.offClass, onClz = opts.onClass;
        if (on.show == 'true') $this.addClass('label').addClass(onClz).text(on.label);
        else if (off.show == 'true') $this.addClass('label').addClass(offClz).text(off.label);

        return $this.css('cursor', 'pointer').off('click').on('click', function (e) {
            e.stopPropagation();
            off = MZ.jsonEval($this.data('off'));
            on = MZ.jsonEval($this.data('on'));
            if ($.isEmptyObject(on) || $.isEmptyObject(off) || url == '') {
                return;
            }
            MZ.alertMsg.confirm(opts.title, {okClick: function () {
                var $tr = $this.parent().parent();
                var _data = {};
                _data[opts.idField] = $tr.attr('rel');
                if (on.show == 'true') _data[opts.valueField] = off.value;
                else if (off.show == 'true') _data[opts.valueField] = on.value;
                $.ajax({type: opts.ajaxType, url: url, cache: false, data: _data, success: function (responseData) {
                    var json = MZ.jsonEval(responseData);
                    if (json && !$.isEmptyObject(json)) {
                        if (json[MZ.keys.successKey] == MZ.successValue.success) {
                            MZ.alertMsg.success(json[MZ.keys.messageKey]);
                            var $table = $this.closest('table'), refreshOnUpdateStatus = $table.attr('refreshOnUpdateStatus');
                            if (refreshOnUpdateStatus == 'true' || refreshOnUpdateStatus == true) {
                                var $form = $table.closest('div.widget-content').find('.mz-pager-search').find('form');
                                listSearch($form, $table.attr('targetType'));
                                return;
                            }
                            var _cloneOn = {'label': on.label, 'value': on.value, 'show': ''},
                                _cloneOff = {'label': off.label, 'value': off.value, 'show': ''};
                            if (on.show == 'true') {
                                $this.removeClass(onClz).addClass(offClz).text(off.label);
                                _cloneOff.show = 'true';
                                $this.data('off', _cloneOff).data('on', _cloneOn);
                            } else if (off.show == 'true') {
                                $this.removeClass(offClz).addClass(onClz).text(on.label);
                                _cloneOn.show = 'true';
                                $this.data('on', _cloneOn).data('off', _cloneOff);
                            }
                        } else if (json[MZ.keys.successKey] == MZ.successValue.failed) {
                            MZ.alertMsg.error(json[MZ.keys.messageKey]);
                        } else if (json[MZ.keys.successKey] == MZ.successValue.timeout) {
                            //TODO 超时需要登录
                        }
                    }
                }});
            }});
        });
    }
})(jQuery);
