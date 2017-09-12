package com.zj.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (!"/login".equals(path)) {
            boolean isBadRequest = true;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("login".equals(cookie.getName()) && cookie.getValue() != null) {
                        isBadRequest = false;
                        break;
                    }
                }
            }
            if (isBadRequest) {
                servletResponse.setContentType("text/html;charset=utf-8");
                PrintWriter writer = servletResponse.getWriter();
                String node = request.getServletContext().getInitParameter("node");
                writer.write(node + " 非法请求！");
                return;
            }
        }

        if ("/logout".equals(path)) {
            Cookie cookie = new Cookie("login", "logout");
            cookie.setMaxAge(0);//设置为0为立即删除该Cookie
            response.addCookie(cookie);

            servletResponse.setContentType("text/html;charset=utf-8");
            PrintWriter writer = servletResponse.getWriter();
            String node = request.getServletContext().getInitParameter("node");
            writer.write(node + " 退出！");
            return;
        }

        // 非登录请求或有效的请求，每次重新刷新Cookie超时，用户不操作10分钟超时
        Cookie cookie = new Cookie("login", "success");
        cookie.setMaxAge(10 * 60); // cookie缓存10分钟
        response.addCookie(cookie);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
