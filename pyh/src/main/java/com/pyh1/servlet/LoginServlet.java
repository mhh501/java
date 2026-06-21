package com.pyh1.servlet;

import com.pyh1.dao.UserDAO;
import com.pyh1.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String loginType = request.getParameter("loginType");
        String password = request.getParameter("password");
        User user = null;

        if ("phone".equals(loginType)) {
            String phone = request.getParameter("phone");
            if (phone == null || phone.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                request.setAttribute("msg", "请输入手机号和密码");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            user = userDAO.loginByPhone(phone, password);
        } else if ("admin".equals(loginType)) {
            String username = request.getParameter("username");
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                request.setAttribute("msg", "请输入管理员账号和密码");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            user = userDAO.login(username, password);
            if (user != null && !"admin".equals(user.getRole())) {
                request.setAttribute("msg", "该账号不是管理员，请使用管理员账号登录");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
        } else {
            String username = request.getParameter("username");
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                request.setAttribute("msg", "请输入用户名和密码");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            user = userDAO.login(username, password);
        }

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user.getUsername());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userId", user.getId());

            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/clothes");
            }
        } else {
            request.setAttribute("msg", "用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
