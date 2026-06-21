package com.zax1.servlet;

import com.zax1.dao.UserDAO;
import com.zax1.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty()) {
            request.setAttribute("msg", "请填写所有必填项");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("^1[3-9]\\d{9}$")) {
            request.setAttribute("msg", "手机号格式不正确");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (userDAO.isUsernameExist(username)) {
            request.setAttribute("msg", "用户名已存在");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (userDAO.isPhoneExist(phone)) {
            request.setAttribute("msg", "手机号已注册");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address != null ? address : "");

        if (userDAO.register(user)) {
            request.setAttribute("msg", "注册成功，请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", "注册失败，请稍后重试");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
