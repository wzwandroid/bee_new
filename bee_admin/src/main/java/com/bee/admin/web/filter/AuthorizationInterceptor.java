package com.bee.admin.web.filter;

import com.bee.admin.utils.CollectionUtils;
//import com.bee.dba.entity.TUserEntity;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* function: 登陆拦截功能  拦截器 基于反射实现 最简单的实现动态代理
* Created by wzw on 2015/9/12. 17::20
*/
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private List<String> ignoreFullUri;
    private List<String> ignorePrefixUrl;

    public List<String> getIgnoreFullUri() {
        return ignoreFullUri;
    }

    public void setIgnoreFullUri(List<String> ignoreFullUri) {
        this.ignoreFullUri = ignoreFullUri;
    }

    public List<String> getIgnorePrefixUrl() {
        return ignorePrefixUrl;
    }

    public void setIgnorePrefixUrl(List<String> ignorePrefixUrl) {
        this.ignorePrefixUrl = ignorePrefixUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.substring(contextPath.length());
        if (CollectionUtils.isNotEmpty(ignorePrefixUrl)) {
            for (String prefixUrl : ignorePrefixUrl) {
                if (url.contains(prefixUrl)) {
                    return true;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(ignoreFullUri)) {
            if (ignoreFullUri.contains(url)) {
                return true;
            }
        }


        HttpSession session = request.getSession();
//        TUserEntity tUserEntity= (TUserEntity) session.getAttribute(SystemConstant.LOGIN_USER_INFO.getCode());
//        if (tUserEntity == null) {
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write("<script>alert(\"您已经很久没有操作页面，请重新登陆在操作。\");window.location.href='" + contextPath + "/admin/login';</script>");
//            return false;
//        }
        return true;


    }
}
