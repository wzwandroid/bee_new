/**
 * Created by mzllon on 2014/8/31.
 */
(function ($) {
    $.fn.extend({
        oneRecordDeleted: function () {
            return this.each(function () {
                var $this = $(this);
                $this.click(function (event) {
                    var url = unescape($this.attr('href').replaceTmById($(event.target).parents('div.widget')));
                    console.info(url);
                    if (!url.isFinishedTm()) {
                        MZ.alertMsg.warning($this.attr('warn') || $.il8n.msg.alertSelectedMsg, {});
                        return false;
                    }
                    var title = $this.attr('title');
                    if (title) {
                        MZ.alertMsg.confirm(title, {
                            okClick: function () {
                                ajaxTodo(url, $this.attr('callback'));
                            }
                        });
                    } else {
                        ajaxTodo(url, $this.attr('callback'));
                    }
                    event.preventDefault();
                });
            });
        },
        moreRecoredDeleted: function () {
            function _getIds(selectedIds, targetType,$this) {
                var ids = '';
                var $box = (targetType == 'dialog' ? $.mzdialog.getCurrent() : $this.closest('div.widget'));
                $box.find('input:checked').filter('[name="' + selectedIds + '"]').each(function (i) {
                    var value = this.value;
                    ids += (i == 0 ? value : ',' + value);
                });
                return ids;
            }

            function _innerPost(url,postType,ids,selectedIds,_callback) {
                $.ajax({
                    type: 'post',
                    url: url,
                    dataType: 'json',
                    cache: false,
                    data: function () {
                        if (postType == 'map') {

                        } else if (postType == 'string') {
                            var _data = {};
                            _data[selectedIds] = ids;
                            return _data;
                        }
                    }(),
                    success: _callback,
                    error: MZ.ajaxError
                });
            }
            return this.each(function () {
                var $this = $(this);
                var selectedIds = $(this).attr('rel') || 'ids';
                var postType = $this.attr('postType') || 'string';
                $this.click(function () {
                    var targetType = $this.attr('targetType');
                    var ids = _getIds(selectedIds, targetType, $this);
                    if(!ids) {
                        MZ.alertMsg.error($this.attr('warn') || MZ.il8n.msg.alertSelectedMsg,{});
                        return false;
                    }
                    var _callback = $this.attr('callback') || (targetType == 'dialog' ? dialogAjaxDone : tabAjaxDone);
                    if(!$.isFunction(_callback)) _callback = eval('(' + _callback + ')');
                    var title = $this.attr('title');
                    var url = $this.attr('href');
                    if(title) {
                        MZ.alertMsg.confirm(title, {okClick: function () {
                            _innerPost(url, postType, ids, selectedIds, _callback);
                        }});
                    } else {
                        _innerPost(url, postType, ids, selectedIds, _callback);
                    }
                });
            });
        }
    });

})(jQuery);

function ajaxTodo(url, callback) {
    var $callback = callback || tabAjaxDone;
    if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
    $.ajax({
        type: 'POST',
        url: url,
        dataType: "json",
        cache: false,
        success: $callback,
        error: MZ.ajaxError
    });
}
function tabAjaxDone(json) {
    MZ.ajaxDone(json);
    if(json[MZ.keys.successKey] == MZ.successValue.success) {
        if(json[MZ.ajaxMsg.keys.callbackType] == MZ.ajaxMsg.values.callbackType.tabRefresh) {
            var basepath = MZ.getFullPath();
            var url = json.forwardUrl;
            if(url.indexOf(basepath) < 0) {
                url = basepath + url;
            }
            $('.mainbar', 'body').loadUrl(url, {}, null);
        }
    }
}

