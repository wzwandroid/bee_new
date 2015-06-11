/**
 * Created by Administrator on 2014/8/4.
 */

$(function () {
    var option = {
        msg: null,
        msgAlert: function (msg) {
            var $ajaxMsg = $("#ajaxMsg");
            $ajaxMsg.empty();
            var alertDiv = $('<div class="alert alert-danger alert-dismissible" role="alert">' +
                '<button type="button" class="close" data-dismiss="alert">' +
                '<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>' +
                '</button><strong>登录失败：</strong>' + msg + '</div>');
            $ajaxMsg.append(alertDiv);
            this.msg = alertDiv;
        },
        msgClose: function () {
            this.msg.alert("close");
        },
        validUsername: function () {
            var $this = $("#username");
            var value = $this.val();
            if (value == '') {
                this.msgAlert('用户名不能为空！');
                return false;
            } else {
                return true;
            }
        },
        validPasswd: function () {
            var $this = $("#password");
            var value = $this.val();
            if (value == '') {
                this.msgAlert('密码不能为空！');
                return false;
            } else {
                return true;
            }
        }
    };
    $("#username").focus();
    $("#username").keydown(function (e) {
        var keyCode = e.which;
        if (keyCode == 13) {
            if (option.validUsername()) {
                $("#password").focus();
            }
        }
    }).blur(function () {
        return option.validUsername();
    });
    $("#password").keydown(function (e) {
        var keyCode = e.which;
        if (keyCode == 13) {
            if (option.validPasswd()) {
                var btn = $("#btnLogin");
                if (!btn.is(":disabled")) {
                    $("#btnLogin").click();
                }
            }
        }
    });
    $("#btnLogin").click(function (e) {
        e.preventDefault();
        var valid = option.validUsername() && option.validPasswd();
        if (!valid) {
            return;
        }
        var $this = $(this);
        $this.button('loading');

        var $form = $this.closest("form");
        $.ajax({
            type: $form.attr("method"),
            url: $form.attr("action"),
            data: $form.serialize(),
            dataType: 'json',
            success: function (result) {
                if (result.type == 'json') {
                    if (result.success) {
                        var basePath = $("#basePath").val();
                        window.location.href = basePath + result.forwardUrl;
                    } else {
                        setTimeout(function () {
                            $this.button('reset');
                        }, 500);
                        setTimeout(function () {
                            option.msgClose();
                        }, 1500);
                        option.msgAlert(result.message);
                    }
                }
            }
        });
    });
});