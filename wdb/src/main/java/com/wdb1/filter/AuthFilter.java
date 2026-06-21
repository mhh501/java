package com.wdb1.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/css/") || path.startsWith("/upload/") ||
            path.equals("/login.jsp") || path.equals("/register.jsp") ||
            path.equals("/login") || path.equals("/register") ||
            path.equals("/LoginServlet") || path.equals("/RegisterServlet") ||
            path.equals("/clothes") || path.equals("/clothes") ||
            path.equals("/clothes") || path.equals("/detail")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}
