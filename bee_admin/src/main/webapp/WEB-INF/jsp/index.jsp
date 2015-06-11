<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <!-- Title and other stuffs -->
    <title>小蜜蜂后台管理系统</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--添加360浏览器开启webkit支持--%>
    <meta name="renderer" content="webkit">
    <meta name="author" content="">

    <!-- Stylesheets -->
    <%--bootstrap3-mac start--%>
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/bootstrap.css" rel="stylesheet">
    <!-- Font awesome icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/font-awesome.css">
    <!-- jQuery UI -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/jquery-ui.css">
    <!-- Calendar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/fullcalendar.css">
    <!-- prettyPhoto -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/prettyPhoto.css">
    <!-- Star rating -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/rateit.css">
    <!-- Date picker -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/bootstrap-datetimepicker.min.css">
    <!-- CLEditor -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/jquery.cleditor.css">
    <!-- Uniform -->
    <!--<link rel="stylesheet" href="resources/bootstrap3-mac/style/uniform.default.css">-->

    <!-- Main stylesheet -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/style.css" rel="stylesheet">
    <!-- Widgets stylesheet -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/widgets.css" rel="stylesheet">

    <!--customs style-->
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/custom.css" rel="stylesheet">

    <!--path for bootstrap modal-->
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/modal-patch/css/bootstrap-modal-bs3patch.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/modal-patch/css/bootstrap-modal.css" rel="stylesheet">

    <!-- Bootstrap toggle -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap3-mac/style/bootstrap-switch.css" rel="stylesheet">
    <!-- Include the multiselect's CSS -->
    <link href="${pageContext.request.contextPath}/resources/multiselect/css/bootstrap-multiselect.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/zTreev3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"><!-- zTree CSS -->
    <link href="${pageContext.request.contextPath}/resources/mz/css/mz.core.css" rel="stylesheet"> <!-- custom core css -->

    <!-- HTML5 Support for IE -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/html5shim.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/bootstrap3-mac/img/favicon/favicon.png">
</head>

<body>

<div class="navbar navbar-fixed-top bs-docs-nav" role="banner">
    <div class="conjtainer">
        <!-- Navigation starts -->
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <div class="nav navbar-nav pull-left">
                <a href="#" class="navbar-brand">
                    <small>
                        <i class="icon-leaf"></i>
                        后台管理系统
                    </small>
                </a>
            </div>
            <!-- Links -->
            <ul class="nav navbar-nav pull-right">
                <li class="nav">
                    <a class="" href="${pageContext.request.contextPath}/admin/logout"><i class="icon-off"></i>退出</a>
                </li>
            </ul>
        </nav>

    </div>
</div>

<!-- Main content starts -->

<div class="content">

<!-- Sidebar -->
<div class="sidebar">
    <div class="sidebar-dropdown"><a href="#">导航</a></div>

    <!--- Sidebar navigation -->
    <!-- If the main navigation has sub navigation, then add the class "has_sub" to "li" of main navigation. -->
    <ul id="nav">
        <li style="background-color: #fff"><a href="${pageContext.request.contextPath}/admin/index" class="open" mz-index="true">
            <i class="icon-home"></i> 首页</a>
        </li>
        <li style="background-color: #fff"><a href="${pageContext.request.contextPath}/api" class="open" mz-index="true">
            <i class="icon-home"></i> 接口管理测试</a>
        </li>
        <%--<li class=""><a href="#"><i class="icon-file-text"></i>功能测试<span class="pull-right"><i--%>
                <%--class="icon-chevron-right"></i></span></a>--%>
            <%--<ul>--%>
                <%--<li><a href="${pageContext.request.contextPath}/function/test/list" target="tab">下载列表</a></li>--%>
                <%--<li><a href="${pageContext.request.contextPath}/function/test/download/list" target="tab">下载管理</a></li>--%>
            <%--</ul>--%>
        <%--</li>--%>
        <!-- Main menu with font awesome icon -->
        <%--<li style="background-color: #fff"><a href="${pageContext.request.contextPath}/admin/index" class="open" mz-index="true">
            <i class="icon-home"></i> 首页</a>
        </li>
        <li class=""><a href="#"><i class="icon-list-alt"></i>网关管理<span class="pull-right"><i
                class="icon-chevron-right"></i></span></a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/channel/list" target="tab">支付网关管理</a></li>
            </ul>
        </li>
        <li class=""><a href="#"><i class="icon-file-text"></i>商户管理<span class="pull-right"><i
                class="icon-chevron-right"></i></span></a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/merchant/listInit?id=12&url=/sys/menus" target="tab">商户管理</a></li>
            </ul>
        </li>
        <li class=""><a href="#"><i class="icon-file-text"></i>交易管理<span class="pull-right"><i
                class="icon-chevron-right"></i></span></a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/trading/search/searchList" target="tab">交易明细管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/summary/search/onLoadList" target="tab">交易统计查询管理</a></li>
            </ul>
        </li>
        <li class=""><a href="#"><i class="icon-file-text"></i>操作员管理<span class="pull-right"><i
                class="icon-chevron-right"></i></span></a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/person/modifyPassword" target="tab">个人密码修改</a></li>
            </ul>
        </li>

        <li class=""><a href="#"><i class="icon-file-text"></i>系统管理<span class="pull-right"><i
                class="icon-chevron-right"></i></span></a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/system/list" target="tab">菜单管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/permission/list" target="tab">权限管理</a></li>
            </ul>

        </li>
          <li class=""><a href="#"><i class="icon-file-text"></i>商户门户管理<span class="pull-right"><i
                class="icon-chevron-right"></i></span></a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/merchant/front/operUserInfo/listInit" target="tab">操作员管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/merchant/front/operUserAndMer/listInit" target="tab">操作员和商户管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/merchant/front/menu/list" target="tab">商户菜单管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/merchant/front/permission/list" target="tab">商户权限管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/merchant/front/role/list" target="tab">角色管理</a></li>
            </ul>

        </li>--%>
    </ul>
</div>

<!-- Sidebar ends -->

<!-- Main bar -->
<div class="mainbar">

<!-- Page heading -->
    <div class="page-head">
        <!--<h2 class="pull-left"><i class="icon-home"></i> 首页</h2>-->

        <!-- Breadcrumb -->
        <div class="pull-left bread-crumb mz-breadcrumb">
            <i class="icon-home mz-home-icon"></i>
            <a href="${pageContext.request.contextPath}/admin/index"> 首页</a>
            <!-- Divider -->
            <span class="divider">/</span>
            <a href="#" class="bread-current">控制台</a>
        </div>

        <div class="clearfix"></div>

    </div>
<!-- Page heading ends -->


<!-- Matter -->

    <div class="matter">
        <div class="container">
            <div class="row">
                <div class="col-md-12" style="border-bottom: 1px solid #DDD;">
                    <h2>欢迎使用小蜜蜂后台管理系统</h2>
                </div>
            </div>
            <!-- dashboard starts -->
            <div class="row">
                <div class="col-sm-6">
                    <!-- widget for user info starts -->
                    <div class="widget">
                        <!-- widget head -->
                        <div class="widget-head">
                            <div class="pull-left">账号信息</div>
                            <div class="widget-icons pull-right"></div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- widget content -->
                        <div class="widget-content">
                            <div class="padd">
                                <div class="form-horizontal accountInfo">
                                    <div class="form-group">
                                        <label class="control-label">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
                                        <span>admin</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">所属角色：</label>
                                        <span>管理员角色</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">姓名：</label>
                                        <span>admin</span>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">上次登陆时间：</label>
                                        <span>2018-13-32-16</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- widget for user info ends -->
                </div>
                <div class="col-sm-5">
                    <!-- widget for website info starts -->
                    <div class="widget">
                        <!-- widget head -->
                        <div class="widget-head">
                            <div class="pull-left">站内消息</div>
                            <div class="widget-icons pull-right"></div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- widget content -->
                        <div class="widget-content">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <td>时间</td>
                                    <td>发布者</td>
                                    <td>内容</td>
                                    <td>注意菜单</td>
                                    <td>注意内容</td>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>2014-09-10</td>
                                    <td>admin</td>
                                    <td>截至今日需要完成三个模块1%内容</td>
                                    <td><span class="label label-info">小区管理</span></td>
                                    <td><label class="red">参照 增删改以及modal中分页</label></td>
                                </tr>
                                <tr>
                                    <td>2014-09-10</td>
                                    <td>admin</td>
                                    <td>截至今日需要完成三个模块1%内容</td>
                                    <td><span class="label label-info">继续努力中</span></td>
                                    <td><label class="red">参照 增删改以及modal中分页</label></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- widget for website info ends -->
                </div>
                <div class="col-sm-11">
                    <!-- widget for login info starts -->
                    <div class="widget">
                        <!-- widget head -->
                        <div class="widget-head">
                            <div class="pull-left">最近登陆记录</div>
                            <div class="widget-icons pull-right"></div>
                            <div class="clearfix"></div>
                        </div>
                        <!-- widget content -->
                        <div class="widget-content">
                            <div class="padd">
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <td>时间</td>
                                        <td>登陆账号</td>
                                        <td>IP地址</td>
                                        <td>状态</td>
                                        <td>描述</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>2016-09-10 09:30:44</td>
                                        <td>admin</td>
                                        <td>127.0.0.1</td>
                                        <td><span class="green">登录成功</span></td>
                                        <td>login success !</td>
                                    </tr>
                                    <tr>
                                        <td>2014-09-10 09:11:13</td>
                                        <td>admin</td>
                                        <td>127.0.0.1</td>
                                        <td><span class="green">登录成功</span></td>
                                        <td>login success !</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- widget for login info ends -->
                </div>
            </div>
        </div>
            <!-- dashboard ends -->
        </div>

<!-- Matter ends -->

</div>

<!-- Mainbar ends -->
<div class="clearfix"></div>

</div>
<!-- Content ends -->

<!-- Footer starts -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-offset-5 col-md-7 ">
                <!-- Copyright info -->
                <p class="copy">Copyright &copy; 2016 | <a href="#">小蜜蜂</a></p>
            </div>
        </div>
    </div>
</footer>
<!-- Footer ends -->

<!-- Scroll to top -->
<span class="totop"><a href="#"><i class="icon-chevron-up"></i></a></span>
<!-- JS -->
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.js"></script>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/bootstrap.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery-ui-1.9.2.custom.min.js"></script>
<!-- jQuery UI -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/fullcalendar.min.js"></script>--%>
<!-- Full Google Calendar - Calendar -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.rateit.min.js"></script>--%>
<!-- RateIt - Star rating -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.prettyPhoto.js"></script>--%>
<!-- prettyPhoto -->

<!-- jQuery Flot -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/excanvas.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.flot.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.flot.resize.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.flot.pie.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.flot.stack.js"></script>--%>


<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/sparklines.js"></script>--%>
<!-- Sparklines -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/jquery.cleditor.min.js"></script>--%>
<!-- CLEditor -->
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/bootstrap-datetimepicker.min.js"></script>
<!-- Date picker -->
<!--<script src="js/jquery.uniform.min.js"></script>-->

<!-- Bootstrap Toggle -->

<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/custom.js"></script>--%>
<!-- Custom codes -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/charts.js"></script>--%>
<!-- Charts & Graphs -->
<!-- Modal Patch -->
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/modal-patch/js/bootstrap-modalmanager.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/modal-patch/js/bootstrap-modal.js"></script>

<!-- jQuery Notification Plugin -->
<script src="${pageContext.request.contextPath}/resources/noty-2.2.4/js/noty/packaged/jquery.noty.packaged.min.js"></script>

<!-- Include the multiselect's JS -->
<script src="${pageContext.request.contextPath}/resources/multiselect/js/bootstrap-multiselect.js"></script>
<!-- Bootstrap toggle -->
<%--<script src="${pageContext.request.contextPath}/resources/bootstrap3-mac/js/bootstrap-switch.min.js"></script>--%>
<script src="${pageContext.request.contextPath}/resources/zTreev3/js/jquery.ztree.core-3.5.min.js"></script><!-- zTree JS -->
<script src="${pageContext.request.contextPath}/resources/zTreev3/js/jquery.ztree.excheck-3.5.min.js"></script>
<!-- Script for this page -->
<script src="${pageContext.request.contextPath}/resources/mz/js/jquery.validate.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/mz/js/mz.core.js"></script><!-- 引入工具类，必须要基于jQuery之下 -->
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.dialog.js"></script><!-- self plugin -->
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.form.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.pagination.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.ajax.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.tab.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.select.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.dlgSlkBack.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.tree.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.index.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.validate.method.js"></script>
<script src="${pageContext.request.contextPath}/resources/mz/js/mz.messages.zh.js"></script>

</body>
</html>
