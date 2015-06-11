<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Page heading -->
<div class="page-head">
    <!-- Breadcrumb -->
    <div class="bread-crumb pull-left mz-breadcrumb">
        <i class="icon-home mz-home-icon"></i>
        <a href="index.html">首页</a>
        <!-- Divider -->
        <span class="divider">/</span>
        <a href="#" class="">系统管理</a>
        <span class="divider">/</span>
        <a href="#" class="bread-current">权限管理</a>
    </div>
    <div class="clearfix"></div>
</div>
<!-- Page heading ends -->
<!-- Matter -->
<div class="matter">
    <div class="container">
        <!-- Table -->
        <div class="widget">
            <div class="widget-content">
                <div class="panel-group mz-pager-search" id="accordion-search">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion-searcha" href="#accordion-search-1">条件搜索</h4>
                        </div>
                        <div class="panel-collapse collapse in" id="accordion-search-1">
                            <div class="panel-body">
                                <form action="${pageContext.request.contextPath}/admin/permission/list" method="post" onsubmit="return listSearch(this,'tab');">

                                <label>
                                    <span>权限名称：</span>
                                    <input type="text" name="name" value="${params['name']}">
                                </label>
                                <label>
                                    <span>权限所属菜单：</span>
                                    <input type="text" name="menuName" value="${params['menuName']}">
                                </label>
                                    <label>
                                        <span>权限代码：</span>
                                        <input type="text" name="code" value="${params['code']}">
                                    </label>
                                    <div class="buttonActive">

                                        <button type="submit" class="btn btn-default">搜索</button>
                                    </div>
                                    <input type="hidden" name="pageSize" value="${pager.pageSize}">
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="mz-toolbar">
                    <div class="btn-group">
                        <%--<jkjTag:permission code="add">--%>
                        <button class="btn btn-primary" target="dialog" href="${pageContext.request.contextPath}/admin/test/add"
                                width="850" height="350" title="新增菜单权限"><i class="icon-plus"></i> 新增-${test1111}</button>
                        <%--</jkjTag:permission>--%>
                        <%--<jkjTag:permission code="edit">--%>
                        <%--<button class="btn btn-info" target="dialog" href="${pageContext.request.contextPath}/admin/permission/update/{sid_permissionId}"--%>
                                <%--width="850" height="350" title="修改菜单权限" warn="请选择权限" rel=""><i class="icon-edit"></i> 修改</button>--%>
                        <%--</jkjTag:permission>--%>
                        <%--<jkjTag:permission code="show">--%>
                        <%--<button class="btn btn-default" target="dialog" href="${pageContext.request.contextPath}/admin/permission/{sid_permissionId}"--%>
                                <%--width="850" height="350" title="查看权限所属菜单" warn="请选择权限" formSubmit="false"><i class="icon-edit"></i> 详细查看</button>--%>
                        <%--</jkjTag:permission>--%>
                        <%--<jkjTag:permission code="deleteMore">--%>
                            <%--<button class="btn btn-danger" target="oneRecordDeleted" href="${pageContext.request.contextPath}/admin/permission/deleteIds?ids={sid_permissionId}"--%>
                                    <%--title="确定要删除？" warn="请选择要删除的应用"><i class="icon-remove"></i> 删除</button>--%>
                            <%--<button class="btn btn-danger" target="moreRecoredDeleted" href="${pageContext.request.contextPath}/admin/permission/deleteIds"--%>
                                    <%--title="确定要删除?" warn="请选择要删除的应用" rel="ids"><i class="icon-remove"></i>批量删除 </button>--%>
                        <%--</jkjTag:permission>--%>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div >
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr class="mz-table-thead">
                            <th class="center" style="width: 10px;">
                                <label>
                                    <input class="mz-chk" type="checkbox" >
                                    <span class="mz-lbl"></span>
                                </label>
                            </th>
                            <th  >#</th>
                            <th >权限代码</th>
                            <th  >权限名称</th>
                            <th  >权限所属菜单</th>
                            <th  >深度</th>
                            <th  >创建时间</th>

                        </tr>
                        </thead>
                        <tbody class="mzGridTbody">
                        <c:set var="rowNum" value="${(params['pager'].pageNo - 1) * params['pager'].pageSize}" />
                        <c:forEach items="${params['pager'].result}" var="permissionConf">
                            <c:set var="rowNum" value="${rowNum + 1}"/>
                            <tr target="sid_permissionId" rel="${permissionConf.id}">
                                <td class="center">
                                    <label>
                                            <%--通过商户基本信息表中的id来取得对应的值 而非商户编号--%>
                                        <input type="checkbox" class="mz-chk" value="${permissionConf.id}" name="ids">
                                        <span class="mz-lbl"></span>
                                    </label>
                                </td>
                                <td>${rowNum}</td>
                                <td>${permissionConf.code}</td>
                                <td>${permissionConf.name}</td>
                                <td>
                                        ${permissionConf.menuItemConfName}
                                </td>

                                <td>${permissionConf.lvl}</td>
                                <td>
                                    <c:if test="${permissionConf.createTime != null}">
                                        ${jkjFn:swapDatePattern(permissionConf.createTime,"yyyyMMddHHmmss" ,"yyyy-MM-dd HH:mm:ss")}
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                    <div class="widget-foot">
                        <div class="mz-pages" totalCount="${params['pager'].totalCount}"  pageSize="${params['pager'].pageSize}" pageNo="${params['pager'].pageNo}" pageNumShown="10"></div>
                    </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //    function regTimeStartFn (ev) {
    //        var regTimeEndTime = $('#regTimeEnd').val();
    //        $(ev.target).datetimepicker('setEndDate', regTimeEndTime);
    //    }
    function getSubMenus(parentId,subId) {
        var pid = $("#"+parentId).val();
        if (pid != "") {
            $.ajax({
                type : "POST",
                dataType : "json",
                url : "${pageContext.request.contextPath}/admin/permission/getSubMenus",
                data : {'pid' : pid},
                success : function(data) {
                    if(data.length > 0){
                        $("#"+subId).empty();
                        $("#"+subId).append("<option value=''>......</option>");
                        var results = eval(data);
                        for ( var i = 0; i < results.length; i++) {
                            var paramName = results[i].id;
                            var paramValue = results[i].name;
                            $("#"+subId).append(
                                            "<option value='"+paramName+"'>" + paramValue
                                            + "</option");
                        }
                    }else{
                        MZ.alertMsg.error("没有二级子菜单，不能添加权限，请到菜单管理中添加");
//                        $("#subMenuDiv").empty();
                    }

                }
            })
        } else {
            $("#"+subId).empty();
            $("#"+subId).append("<option value=''>......</option>");
        }
    }



</script>