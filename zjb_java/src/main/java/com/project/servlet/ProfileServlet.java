package com.project.servlet;

import com.project.dao.UserDAO;
import com.project.entity.User;
import com.project.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = (User) req.getSession().getAttribute("user");
        User user = userDAO.getById(sessionUser.getId());
        req.getSession().setAttribute("user", user);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/user/profile.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User sessionUser = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            String originalPassword = req.getParameter("originalPassword");
            String newPassword = req.getParameter("newPassword");
            String username = req.getParameter("username");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");

            // verify original password
            if (originalPassword != null && !originalPassword.trim().isEmpty()) {
                if (!userDAO.verifyPassword(sessionUser.getId(), MD5Util.md5(originalPassword))) {
                    req.setAttribute("error", "原密码错误");
                    doGet(req, resp);
                    return;
                }
            }

            User user = new User();
            user.setId(sessionUser.getId());
            user.setUsername(username);
            user.setPhone(phone);
            user.setAddress(address);
            userDAO.update(user);

            if (newPassword != null && !newPassword.trim().isEmpty()) {
                userDAO.updatePassword(sessionUser.getId(), MD5Util.md5(newPassword));
            }

            User updated = userDAO.getById(sessionUser.getId());
            req.getSession().setAttribute("user", updated);
            req.setAttribute("success", "修改成功");
        }
        doGet(req, resp);
    }
}
