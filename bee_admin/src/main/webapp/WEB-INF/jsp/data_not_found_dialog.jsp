<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h3>数据获取失败对话框</h3>
</div>
<div class="modal-body" id="errorMessage">
    <div class="row">
        <div class="col-md-12">
            <div class="error-background">
                <div class="well " id="error_center">
                    <h1 class="grey lighter smaller">
                            <span class="blue bigger-125">
                                <i class="icon-sitemap"></i>400
                            </span>
                        数据获取失败
                    </h1>
                    <hr>
                    <h3 class="lighter smaller error-warning">${errorMessage}</h3>
                    <div class="center" style="margin-top: 15px">
                        <a class="btn btn-grey" href="#" data-dismiss="modal"><i class="icon-eye-close"></i> 关闭</a>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/index"><i class="icon-dashboard"></i> 控制台</a>
                    </div>
                </div>
            </div>

        </div>
        </div>
    </div>
</div>
<script>$(function(){setTimeout(function(){var height = $("#errorMessage").height();var mt=(height/2)-160;if(mt<=0)mt=15;var mb=(height/2)-90;if(mb<=0)mb=15;$("#error_center").css({"margin-top":mt,"margin-bottom":mb});},500);});</script>
