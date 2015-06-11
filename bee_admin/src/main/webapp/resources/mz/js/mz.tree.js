/**
 * Created by tomi on 2014/9/22.
 */
(function ($) {
    $.fn.mtree = function (opts) {
        var _yp = opts.isCheckedParent ? 'p' : '', _ys = opts.isCheckedChildren ? 's' : '';
        var _np = opts.isUncheckedParent ? 'p' : '', _ns = opts.isUncheckedChildren ? 's' : '';
        var ztreeSettings = {
            data: {
                simpleData: {
                    enable: opts.simpleData, idKey: opts.valueField, pIdKey: opts.parentField, rootPId: null
                },
                key: {
                    children: opts.children, name: opts.textField, title: ''
                }
            },
            check: {
                chkStyle: opts.isMultiSelect ? 'checkbox' : 'radio',
                enable: opts.enableCheck,
                chkboxType:{"Y":_yp+_ys,"N":_np+_ns}
            },
            async: {
                enable: opts.async.enable, url: opts.async.url, type: opts.async.type,
                otherParam: opts.async.params, dataType: opts.async.dataType
            },
            view: {
                selectedMulti: opts.isMultiSelect,
                dblClickExpand: opts.dblClickExpand
            },
            callback: {
                beforeClick: function (treeId,treeNode,clickFlag) {
                    var func = eval(opts.beforeClick || '');
                    if($.isFunction(func)) {
                        return func(treeId, treeNode);
                    }
                },
                onClick: function (event, treeId, treeNode) {
                    var func = eval(opts.onClick || '');
                    if ($.isFunction(func)) {
                        return func(event, treeId, treeNode);
                    }
                },
                onCheck: function (event, treeId, treeNode) {
                    var func = eval(opts.onCheck || '');
                    if ($.isFunction(func)) {
                        return func(event, treeId, treeNode);
                    }
                }
            }
        };
        var $this = $(this);
        var treeId = opts.treeId || MZ.guid('', false);
        var $ztree = $('<ul class="ztree" id="' + treeId + '"></ul>');
        var ztreeObj = $.fn.zTree.init($ztree, ztreeSettings, opts.nodes);
        ztreeObj.expandAll(true);
        $this.after($ztree);
        $this.hide();
        return ztreeObj;
    }
})(jQuery);
