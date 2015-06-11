/**
 * Created by mzllon on 2014/08/01.
 * Updated By mzllon on 2014/08/28.
 * Updated By mzllon on 2014/08/31
 */
var MZ = {
    /**
     * 转换json字符串为JSON对象
     * @param data json字符串
     * @returns Object 返回JSON对象
     */
    jsonEval: function (data) {
        try {
            if ($.type(data) == 'string') return eval('(' + data + ')');
            else return data;
        } catch (e) {
            return {};
        }
    },
    /**
     * 调试工具
     * @param msg 调试信息
     */
    debug: function (msg) {
        if (typeof(console) != "undefined") console.log(msg);
        else alert(msg);
    },
    /**
     * 获取项目的网络路径，比如为http://localhost/project/index
     * @returns {string}
     */
    getFullPath: function () {
        var www = window.document.location.href;
        var pathname = window.document.location.pathname;
        var pos = www.indexOf(pathname);
        var localhost = www.substring(0, pos);
        var project = pathname.substring(0, pathname.substr(1).indexOf('/') + 1);
        return localhost + project;
    },
    /**
     * 将简单JSON数据转为有父子关系的JSON格式
     * @param options JSON数据格式：{idKey:'',name:'',pIdKey:'',children:''},其中pIdKey必须传入
     * @param data 带解析的简单数据格式
     * @returns {*}
     */
    parseSimpleData: function (options, data) {
        if (!data)return[];
        if (!options.pIdKey)return data;
        var idKey, name, pIdKey,children;
        idKey = options.idKey || 'id';
        name = options.name || 'name';
        pIdKey = options.pIdKey;
        children = options.children || 'children';
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idKey]] = data[i];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][pIdKey]] && data[i][idKey] != data[i][pIdKey]) {
                if (!tmpMap[data[i][pIdKey]][children])tmpMap[data[i][pIdKey]][children] = [];
                data[i][name] = data[i][name];
                tmpMap[data[i][pIdKey]][children].push(data[i]);
            } else {
                data[i][name] = data[i][name];
                treeData.push(data[i]);
            }
        }
        return treeData;
    },
    /**
     * 通过Javascript生成UUID
     * @param separator 分隔符，默认不连接分隔符
     * @param upperCase 是否转为大写，如果值为true则转换
     * @returns String
     */
    guid: function (separator, upperCase) {
        function seed() {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        }
        var sep = separator || '';
        var upper = upperCase || false;

        var guid = (seed() + seed() + sep + seed() + sep + seed() + sep +
            seed() + sep + seed() + seed() + seed());
        if (upper) {
            return guid.toUpperCase();
        } else {
            return guid;
        }
    },
    /**
     * 跳转或者打开登录页面
     */
    timeoutLogin: function () {
      if($.mzdialog && MZ._initSet.modalLogin) {
          $.mzdialog.open(this._initSet.loginUrl, 'login', this._initSet.loginTitle, {
              width: 520, height: 300
          });
      } else window.location.href = MZ._initSet.loginUrl;
    },
    /* 内建初始化参数 */
    _initSet:{
        modalLogin:false,
        loginUrl:'',
        loginTitle:''
    },
    keys:{
        successKey:'statusCode',
        messageKey:'message'
    },
    /* 常用键盘对应的ASCII码 */
    keyCode:{
        ENTER:13,ESC:27,END:35,HOME:36,
        SHIFT:16,
        TAB:9,
        LEFT:37,RIGHT:39,UP:38,DOWN:40,
        DELETE:46,BACKSPACE:8
    },
    ajaxMsg:{
        keys:{
            callbackType:'cbType'
        },
        values:{
            callbackType:{
                closedAndRefresh:'100',
                noClosedAndRefresh:'101',
                closedAndNoRefresh:'102',
                tabRefresh:'200'
            }
        }
    },
    successValue:{success:'200',failed:'300',timeout:'3001'},
    /* 默认弹出框的大小 */
    dialogDefault:{width:450,height:300},
    /* ajax请求出现异常处理函数 */
    ajaxError: function (xhr, options, error) {
        if(MZ.alertMsg){
            MZ.alertMsg.error("<div>Http status: " + xhr.status + " " + xhr.statusText + "</div><div>ajaxOptions: " + options + "</div>"
                + "<div>thrownError: " + error + "</div><div>" + xhr.responseText + "</div>");
        } else {
            alert('Http status code:' + xhr.status + '\t' + xhr.statusText + '\nother options:' + options + '\nthrow error:' + error + '\n' + xhr.responseText);
        }
    },
    /* ajax请求完成的处理函数 */
    ajaxDone: function (json) {
        if(json[MZ.keys.successKey] == MZ.successValue.failed) {
            if(json[MZ.keys.messageKey] && MZ.alertMsg) MZ.alertMsg.error(json[MZ.keys.messageKey]);
        } else if(json[MZ.keys.successKey] == MZ.successValue.timeout) {
            if(MZ.alertMsg)MZ.alertMsg.error(json[MZ.keys.messageKey] || $.il8n.msg.timeout, {okClick: MZ.timeoutLogin});
            else MZ.timeoutLogin();
        } else if(json[MZ.keys.successKey] == MZ.successValue.success) {
            if(json[MZ.keys.messageKey] && MZ.alertMsg) MZ.alertMsg.success(json[MZ.keys.messageKey]);
        } else {
            MZ.debug(json);
        }
    },
    /* 提示框弹出封装 */
    alertMsg:{
        _boxId:'#notyAlertMsgBox',
        _boxContent:'<div id="notyAlertMsgBox" class="msgAlert"></div>',
        _boxOkId:'notyAlertMsgBox_ok_btn',
        _currentBox:null,
        _boxCancelId:'notyAlertMsgBox_cancel_btn',
        _closeTimer:null,
        _types:{error:'error',info:'information',warn:'warning',success:'success',confirm:'alert'},
        _keyDownOK: function (event) {
            if(event.keyCode == MZ.keyCode.ENTER)event.data.target.trigger('click');
            return false;
        },
        _keyDownESC: function (event) {
            if(event.keyCode == MZ.keyCode.ESC)event.data.target.trigger('click');
            return false;
        },
        /**
         * 普通消息提示框
         * @param msg 提示消息
         * @param button JSON格式:{okId:'',okClass:'',okValue:'',okClick:null}
         */
        info: function (msg, button) {
            this._alert(this._types.info,msg, button);
        },
        /**
         * 警告消息提示框
         * @param msg 警告信息
         * @param button JSON格式:{okId:'',okClass:'',okValue:'',okClick:null}
         */
        warning: function (msg, button) {
            this._alert(this._types.warn, msg, button);
        },
        /**
         * 错误消息提示框
         * @param msg 错误消息
         * @param button JSON格式:{okId:'',okClass:'',okValue:'',okClick:null}
         */
        error: function (msg, button) {
            this._alert(this._types.error, msg, button);
        },
        /**
         * 成功消息提示框
         * @param msg 成功提示消息
         * @param button JSON格式:{okId:'',okClass:'',okValue:'',okClick:null}
         */
        success: function (msg, button) {
            this._alert(this._types.success, msg, button);
        },
        /**
         * 提示框的内部函数
         * @param type 提示框类型
         * @param msg 提示信息
         * @param buttons 确定按钮，JSON格式:{okId:'',okClass:'',okValue:'',okClick:null}
         * @private 私有内部函数
         */
        _alert: function (type, msg, buttons) {
            var _buttons = {okId: this._boxOkId,okClass: $.il8n.alertMsg.btnClass.ok,okValue: $.il8n.alertMsg.btnMsg.ok,okClick: null};
            $.extend(_buttons, buttons);
            this._build(type, msg, _buttons);
        },
        /**
         * 核心内建函数
         * @param type 提示框类型
         * @param msg 提示消息
         * @param buttons 按钮
         * @private 私有函数
         * @required jquery.noty.js 即需要依赖noty插件
         */
        _build: function (type, msg, buttons) {
            var that = this;
//            $(that._boxId).remove();
            if(this._currentBox != null) {
                this._currentBox.close();
            }
            //如果关闭定时器还存在则清除
            if(that._closeTimer) {
                clearTimeout(that._closeTimer);
                that._closeTimer = null;
            }
            if(that._types.info == type || that._types.success == type) {
                that._closeTimer = setTimeout(function () {
                    MZ.alertMsg.close();
                }, 3500);
            }
            if(type == this._types.confirm) {
                this._currentBox = $(that._boxContent).appendTo('body').noty({
                    layout: $.il8n.alertMsg.location,
                    type: type,
                    killer:true,
                    text: msg,
                    modal: true,
                    buttons: [
                        {
                            id: buttons.okId,
                            addClass: buttons.okClass,
                            text: buttons.okValue,
                            onClick: function ($btn, self) {
                                $btn.close();
                                if (buttons.okClick) buttons.okClick();
                                else return false;
                            }
                        },
                        {
                            id: buttons.cancelId,
                            addClass: buttons.cancelClass,
                            text: buttons.cancelValue,
                            onClick: function ($btn, self) {
                                $btn.close();
                                if (buttons.cancelClick) return buttons.cancelClick();
                                return false;
                            }
                        }
                    ],
                    callback:{
                        afterShow: function () {
                            if(!this.options.modal) {
                                var jDoc = $(document);
                                jDoc.bind('keydown', {target: $('#' + that._boxOkId)}, that._keyDownOK);
                                jDoc.bind('keydown', {target: $('#' + that._boxCancelId)}, that._keyDownESC);
                            }
                        }
                    }
                });
            } else {
                this._currentBox = $(that._boxContent).appendTo('body').noty({
                    layout: $.il8n.alertMsg.location,
                    type: type,
                    text: msg,
                    killer:true,
                    modal: !!(type == that._types.warn || type == that._types.error),
                    buttons: [
                        {
                            id: buttons.okId,
                            addClass: buttons.okClass,
                            text: buttons.okValue,
                            onClick: function ($btn, self) {
                                $btn.close();
                                if (buttons.okClick) buttons.okClick();
                                else return false;
                            }
                        }
                    ],
                    callback:{
                        afterShow: function () {
                            if(!this.options.modal) {
                                var jDoc = $(document);
                                jDoc.bind('keydown', {target: $('#' + that._boxOkId)}, that._keyDownOK);
                            }
                        }
                    }
                });
            }
            //var _modal = (type == this._types.warn || type == this._types.error || type == this._types.confirm) ? true : false;
        },
        /**
         * 询问提示框（是/否）
         * @param msg 提示消息
         * @param options 其它可选项 {okId,okClass,okValue,okClick,cancelId,cancelClass,cancelValue,cancelClick}
         */
        confirm: function (msg, buttons) {
            var _buttons = {okId: this._boxOkId, okValue: $.il8n.alertMsg.btnMsg.ok, okClass: $.il8n.alertMsg.btnClass.ok, okClick: null,
                cancelId: this._boxCancelId, cancelValue: $.il8n.alertMsg.btnMsg.cancel, cancelClass: $.il8n.alertMsg.btnClass.cancel, cancelClick: null};
            $.extend(_buttons, buttons);
            this._build(this._types.confirm, msg, _buttons);
        },
        /**
         * 关闭当前提示框
         */
        close: function () {
            $(document).unbind('keydown', this._keyDownOK).unbind('keydown', this._keyDownESC);
            this._currentBox.close();
            $(this._boxId).remove();
        }
    }
};
window.MZ = MZ;

//extend jQuery
(function ($) {
    /**
     * 用于设置国际化支持
     * @param key 键名
     * @param value 值
     */
    $.setIl8n = function (key, value) {
        if (!$.il8n)$.il8n = {};
        $.il8n[key] = value;
    };
    $.fn.extend({
        /**
         * 封装Ajax提交函数
         * @param options '{type:GET/POST,url:ajax请求地址,data:ajax请求参数,callback:回调函数,ajaxBefore:在ajax提交前的执行函数}'
         */
        ajaxUrl: function (options) {
            var $this = $(this);
            //ajax before function
            var before = true;
            if ($.isFunction(options.ajaxBefore)) {
                before = options.ajaxBefore(this);
            }
            if (!before) {
                return false;
            }
            $.ajax({
                type: options.type || 'POST',
                url: options.url,
                data: options.data,
                cache: false,
                success: function (responseData) {
                    var json = MZ.jsonEval(responseData);
                    if (json[MZ.keys.successKey] == MZ.successValue.failed) {
                        if (json[MZ.keys.messageKey]) MZ.alertMsg.error(json[MZ.keys.messageKey]);
                    } else {
                        $this.html(responseData).initUI();
                        if ($.isFunction(options.callback)) options.callback(responseData);
                    }
                    //timeout
                    /*if (json[MZ.keys.successKey] == MZ.successValue.timeout) {
                        //提示登录
                        //是否应该关闭刚刚打开的对话框、及其其它页面碎片
                        MZ.alertMsg.error(json[MZ.keys.messageKey] || $.il8n.msg.timeout, {
                            okClick: function () {
                                MZ.timeoutLogin();
                            }
                        });
                    }*/
                },
                error: MZ.ajaxError
            });
        },
        loadUrl: function (url, data, callback) {
            $(this).ajaxUrl({url: url, data: data, callback: callback,type:'GET'});
        },
        initUI: function () {
            return this.each(function () {
                if ($.isFunction(initUI))initUI(this);
            });
        },
        hoverClass: function (className, speed) {

        }
    });
    //扩展String方法
    $.extend(String.prototype, {
        replaceAll: function (os, ns) {
            return this.replace(new RegExp(os, "gm"), ns);
        },
        replaceTmById:function(_box){
            var $parent = _box || $(document);
            return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
                var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
                return $input.val() ? $input.val() : $1;
            });
        },
        isFinishedTm:function(){
            return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this));
        }
    });
})(jQuery);
