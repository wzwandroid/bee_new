<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <!-- Title and other stuffs -->
    <title>登陆页面-健康金交易查询管理系统</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--添加360浏览器开启webkit支持--%>
    <meta name="renderer" content="webkit">
    <meta name="author" content="mzllon">
    <!-- Stylesheets -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/style.css" rel="stylesheet">
    <script type="text/javascript">window.history.forward();</script>
    <!-- HTML5 Support for IE -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}resources/bootstrap3-mac/js/html5shim.js"></script>
    <script src="${pageContext.request.contextPath}resources/bootstrap3-mac/js/respond.min.js"></script>
    <![endif]-->
    <style>
        body {background:none #669999;}
        body>.admin-form{margin: 100px auto;}
    </style>
</head>
<body>
<!-- Form area -->
<div class="admin-form">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div style="height: 50px">
                    <div id="ajaxMsg"></div>
                </div>
                <!-- Widget starts -->
                <div class="widget worange">
                    <!-- Widget head -->
                    <div class="widget-head"><i class="icon-lock"></i> 健康金交易查询管理系统 - 登陆页面</div>
                    <div class="widget-content">
                        <div class="padd">
                            <!-- Login form -->
                            <form class="form-horizontal" action="${pageContext.request.contextPath}/admin/login" method="post">
                                <!-- Email -->
                                <div class="form-group">
                                    <label class="control-label col-md-3" for="username">账号</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="username" name="username" value="" placeholder="请输入您的账号">
                                    </div>
                                </div>
                                <!-- Password -->
                                <div class="form-group">
                                    <label class="control-label col-md-3" for="password">密码</label>
                                    <div class="col-md-6">
                                        <input type="password" class="form-control" id="password" name="password" value="" placeholder="请输入您的密码">
                                    </div>
                                </div>
                                <!-- Remember me checkbox and sign in button -->
                                <div class="form-group">
                                </div>
                                <div class="col-md-7 col-md-offset-3">
                                    <button type="button" class="btn btn-primary" id="btnLogin" data-loading-text="正在登录,请稍候..." autocomplete="off">登陆</button>
                                    <button type="reset" class="btn btn-inverse" id="btnForgetPwd">忘记密码</button>
                                </div>
                                <br/>
                            </form>
                        </div>
                    </div>
                    <div class="widget-foot">
                        <%--还没有账号？<a href="#">立即注册</a>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- JS -->
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/login.js" charset="utf-8"></script>
<input type="hidden" id="basePath" value="${pageContext.request.contextPath}">
</body>
</html>