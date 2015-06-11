package com.bee.admin.exception.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mark
 * @date 2015-07-12
 */
public class AuthmanageExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(AuthmanageExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error("程序出现异常错误：已经被springMVC的异常处理拦截器并且交给了自定义异常处理器：AuthmanageExceptionHandler", e);
        logger.error("错误{}"+e.getMessage());
        return new ModelAndView("error").addObject("exception", e);
    }
}
