package com.wdb1.servlet;

import com.wdb1.dao.UserDAO;
import com.wdb1.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        User user = userDAO.getById(userId);
        req.getSession().setAttribute("loginUser", user.getUsername());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/user/profile.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            String originalPassword = req.getParameter("originalPassword");
            String newPassword = req.getParameter("newPassword");
            String username = req.getParameter("username");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");

            if (originalPassword != null && !originalPassword.trim().isEmpty()) {
                if (!userDAO.verifyPassword(userId, originalPassword)) {
                    req.setAttribute("error", "原密码错误");
                    doGet(req, resp);
                    return;
                }
            }

            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setPhone(phone);
            user.setAddress(address);
            userDAO.update(user);

            if (newPassword != null && !newPassword.trim().isEmpty()) {
                userDAO.updatePassword(userId, newPassword);
            }

            User updated = userDAO.getById(userId);
            req.getSession().setAttribute("loginUser", updated.getUsername());
            req.setAttribute("success", "修改成功");
        }
        doGet(req, resp);
    }
}
