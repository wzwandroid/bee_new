/**
 * Created by mzllon on 2014/8/29.
 */

/* ajax submit */
// 此处做的validate
/**
 * 此处是关于授权的验证 checkbox 为简单checkbox的验证
 * ajax表单提交
 * @param form form表单
 * @param confirmMsg 提示确认信息
 * @param ajaxBefore 表单提交前事件，如果返回false则中止表单提交
 * @param callback 表单提交成功处理事件
 * @returns {boolean}
 */


function validateCheckBoxForm(form,callback,ajaxBefore,confirmMsg,checkGroupName,checkAlertMsg){
    var $form = $(form);

    if(checkGroupName != ""){
        if(!$form.valid()) {
            return false;
        }
        var checkGroup = $("input:checkbox[name="+checkGroupName+"]:checked");
        if(checkGroup.length <= 0){
            MZ.alertMsg.warning(checkAlertMsg);
            return false
        }
    }

    var _submitFunc = function () {
        $.ajax({
            type: $form.attr('method') || 'post',
            url: $form.attr('action'),
            data: $form.serializeArray(),
            dataType: 'json',
            cache: false,
            success: function (responseData) {
                if (callback && $.isFunction(callback)) {
                    callback(responseData);
                } else {
                    MZ.ajaxDone(json);
                }
            }
        });
    };
    if(ajaxBefore && $.isFunction(ajaxBefore)) {
        ajaxBefore(form);
    }
    if(confirmMsg) {
        //TODO need do
        if(confirm(confirmMsg)) {
            _submitFunc();
        }
    } else {
        _submitFunc();
    }
    return false;
}

//wzw 新增
function uploadForm(form,callback,ajaxBefore,confirmMsg,uploadFileName){
    var $form = $(form);
    if(!$form.valid()) {
        return false;
    }
    var _submitFunc = function () {
        $.ajax({
            type: $form.attr('method') || 'post',
            secureuri:false,
            fileElementId:uploadFileName,
            url: $form.attr('action'),
            data: $form.serializeArray(),
            dataType: 'json',
            cache: false,
            success: function (responseData) {
                if (callback && $.isFunction(callback)) {
                    callback(responseData);
                } else {
                    MZ.ajaxDone(json);
                }
            }
        });
    };
    if(ajaxBefore && $.isFunction(ajaxBefore)) {
        ajaxBefore(form);
    }
    if(confirmMsg) {
        //TODO need do
        if(confirm(confirmMsg)) {
            _submitFunc();
        }
    } else {
        _submitFunc();
    }
    return false;
}
/**
 * ajax表单提交
 * @param form form表单
 * @param confirmMsg 提示确认信息
 * @param ajaxBefore 表单提交前事件，如果返回false则中止表单提交
 * @param callback 表单提交成功处理事件
 * @returns {boolean}
 */


function validateForm(form,callback,ajaxBefore,confirmMsg){
    var $form = $(form);
    if(!$form.valid()) {
        return false;
    }
    var _submitFunc = function () {
        $.ajax({
            type: $form.attr('method') || 'post',
            url: $form.attr('action'),
            data: $form.serializeArray(),
            dataType: 'json',
            cache: false,
            success: function (responseData) {
                if (callback && $.isFunction(callback)) {
                    callback(responseData);
                } else {
                    MZ.ajaxDone(json);
                }
            }
        });
    };
    if(ajaxBefore && $.isFunction(ajaxBefore)) {
        ajaxBefore(form);
    }
    if(confirmMsg) {
        //TODO need do
        if(confirm(confirmMsg)) {
            _submitFunc();
        }
    } else {
        _submitFunc();
    }
    return false;
}
/**
 * iframe表单提交，表单里面带有了文件上传
 * @param form form表单
 * @param callback 回调函数
 * @returns {boolean}
 */
function iframeForm(form,callback){
    var $form = $(this), $ife = $('#mz_frame_form');
    if(!$form.valid()){return false;}
    if($ife.size() <=0) {
        $ife = $('<iframe id="mz_frame_form" name="mz_frame_form"style="display: none;"></iframe>').appendTo('body');
        $ife.attr('src', window.ActiveXObject ? 'javascript:return false' : 'about:blank');
    }
    if(!$form.find(":hidden[name='_mz_ajax_lv']")) {
        $form.append('<input type="hidden" name="_mz_ajax_lv" value="1"/>');
    }
    form.target = 'mz_frame_form';
    _iframeCallback($ife[0], callback || MZ.ajaxDone);
}
function clearForm(form){
    $('input,select,textarea', form).each(function () {
        var _type = this.type;
        if (_type == 'text' || _type == 'hidden' || _type == 'password' || _type == 'textarea') {
            this.value = '';
        } else if (_type == 'file') {
            var $file = $(this);
            var $nf = $file.clone().val('');
            $nf.insertAfter($file);
            $file.remove();
        } else if (_type == 'radio' || _type == 'checkbox') {
            this.checked = false;
        } else if (_type == 'select') {
            this.selectedIndex = -1;
        }
    });
}
function _iframeCallback(iframe,callback) {
    var $ife = $(iframe), $dct = $(document);
    $dct.trigger('ajaxStart');
    $ife.bind('load', _cb);
    function _cb(event) {
        $ife.unbind('load');
        $dct.trigger('ajaxStop');
        if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || // For Safari
            iframe.src == "javascript:'<html></html>';") { // For FF, IE
            return;
        }
        var doc = iframe.contentDocument || iframe.document;
        // fixing Opera 9.26,10.00
        if (doc.readyState && doc.readyState != 'complete') return;
        // fixing Opera 9.64
        if (doc.body && doc.body.innerHTML == "false") return;

        var resp;
        if(doc.XMLDocument) {
            resp = doc.XMLDocument;
        } else if(doc.body) {
            try{
                resp = $ife.contents().find('body').text();
                resp = jQuery.parseJSON(resp);
            }catch (e){
                resp = doc.body.innerHTML;
            }
        } else {
            resp = doc;
        }
        callback(resp);
    }
}

//add jQuery plugin
(function ($) {
    $.fn.form = function (options, params) {
        if (typeof options == 'string') {
            this.each(function () {

            });
            return $.fn.form.methods[options](this, params);
        }
        return this.each(function () {
            //init

        });
    };
    $.fn.form.methods = {
        submit: function (jq, options) {
            options = options || {};
            return jq.each(function () {
                if (options.containsFile) {
                    return iframeForm(this, options.callback);
                } else {
                    return validateForm(this, options.confirmMsg, options.ajaxBefore, options.callback);
                }
            });
        },
        clear:function(jq) {
            return jq.each(function () {
                clearForm(this);
            });
        },
        validate: function (jq) {
            return jq.each(function () {
                return $(this).valid();
            });
        }
    };

})(jQuery);

