package com.kk.filter;

/*
@author kzj
@date 2020/3/16 - 0:13
*/


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class MyFilter implements Filter {
    private static final Logger log= LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        System.out.println(url);
        log.info("MyFilter过滤器，url:",url);

        //静态资源放行
        if(url.endsWith(".css")||url.endsWith(".js")||url.endsWith(".jpg")||url.endsWith(".gif")||url.endsWith(".png")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        //放行登录请求
        if(url.contains("login")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            if(request.getSession() == null) {
                System.out.println("未登录");
                response.sendRedirect(request.getContextPath() + "/login");
            }
            filterChain.doFilter(request, response);
        }
    }
}
