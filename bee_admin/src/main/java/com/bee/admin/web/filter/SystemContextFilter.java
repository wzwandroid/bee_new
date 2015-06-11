package com.bee.admin.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 参数设置过滤器  一般只执行一次 参数设置 最好用拦截器
 * 而登陆限制等最好用过滤器 过滤器基于回调函数的 看dofilter
 * Created by mzllon on 2014/7/24.
 */
public class SystemContextFilter implements Filter {
    private static final Integer defaultPageSize= 10;
    private static final Integer defaultPageNo = 1;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        Integer offset;
        Integer pageSize;
        try {
            offset = Integer.valueOf(req.getParameter("pageNo"));
        } catch (NumberFormatException e) {
            //ignore
            offset = defaultPageNo;
        }
        try {
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        } catch (NumberFormatException e) {
            //ignore
            pageSize = defaultPageSize;
        }

        try {
            SystemContext.setSortName(req.getParameter("sortName"));
            SystemContext.setSortType(req.getParameter("sortType"));
            SystemContext.setPageNo(offset);
            SystemContext.setPageSize(pageSize);
            SystemContext.setRealPath(request.getSession().getServletContext().getRealPath("/"));
            SystemContext.setProjectUri(request.getContextPath() + "/");

            chain.doFilter(req, resp);
        } finally {
            SystemContext.removeSortName();
            SystemContext.removeSortType();
            SystemContext.removePageNo();
            SystemContext.removePageSize();
            SystemContext.removeRealPath();
            SystemContext.removeProjectUri();
        }
    }

    @Override
    public void init(FilterConfig cfg) throws ServletException {
    }
}
