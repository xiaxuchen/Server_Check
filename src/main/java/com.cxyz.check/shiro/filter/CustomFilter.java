package com.cxyz.check.shiro.filter;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CustomFilter extends UserFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(logger.isErrorEnabled()) {
            logger.error("account need login for: {}",  ((HttpServletRequest)request).getServletPath());
        }

        // 请求被拦截后直接返回json格式的响应数据
        response.getWriter().write( "account not logined");
        response.getWriter().flush();
        response.getWriter().close();
        return false;
    }
}
