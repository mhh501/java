package com.project.servlet;

import com.project.dao.UserDAO;
import com.project.entity.User;
import com.project.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            userDAO.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }
        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("editUser", userDAO.getById(id));
        }

        List<User> users = userDAO.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action) || "update".equals(action)) {
            String idStr = req.getParameter("id");
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");

            User user = new User();
            user.setUsername(username);
            user.setPhone(phone);
            user.setAddress(address != null ? address : "");

            if ("add".equals(action)) {
                user.setPassword(MD5Util.md5(password));
                user.setRole("user");
                userDAO.add(user);
            } else {
                user.setId(Integer.parseInt(idStr));
                userDAO.update(user);
                if (password != null && !password.trim().isEmpty()) {
                    userDAO.updatePassword(Integer.parseInt(idStr), MD5Util.md5(password));
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
