package com.project.servlet;

import com.project.dao.UserDAO;
import com.project.entity.User;
import com.project.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            login(req, resp);
        } else if ("register".equals(action)) {
            register(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginType = req.getParameter("loginType");
        String password = MD5Util.md5(req.getParameter("password"));
        User user = null;

        if ("phone".equals(loginType)) {
            String phone = req.getParameter("phone");
            user = userDAO.loginByPhone(phone, password);
        } else {
            String username = req.getParameter("username");
            user = userDAO.login(username, password);
        }

        if (user != null) {
            req.getSession().setAttribute("user", user);
            if ("admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/index");
            }
        } else {
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("/login").forward(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty()) {
            req.setAttribute("error", "请填写所有必填项");
            req.getRequestDispatcher("/register").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "两次密码输入不一致");
            req.getRequestDispatcher("/register").forward(req, resp);
            return;
        }

        if (!phone.matches("^1[3-9]\\d{9}$")) {
            req.setAttribute("error", "手机号格式不正确");
            req.getRequestDispatcher("/register").forward(req, resp);
            return;
        }

        if (userDAO.isUsernameExist(username)) {
            req.setAttribute("error", "用户名已存在");
            req.getRequestDispatcher("/register").forward(req, resp);
            return;
        }

        if (userDAO.isPhoneExist(phone)) {
            req.setAttribute("error", "手机号已注册");
            req.getRequestDispatcher("/register").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.md5(password));
        user.setPhone(phone);
        user.setAddress(address != null ? address : "");

        if (userDAO.register(user)) {
            req.setAttribute("success", "注册成功，请登录");
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            req.setAttribute("error", "注册失败，请稍后重试");
            req.getRequestDispatcher("/register").forward(req, resp);
        }
    }
}
