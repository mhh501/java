package com.pyh1.servlet;

import com.pyh1.dao.UserDAO;
import com.pyh1.entity.User;
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
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                userDAO.delete(Integer.parseInt(idStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }
        if ("add".equals(action)) {
            req.getRequestDispatcher("/admin/user_form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr);
                req.setAttribute("editUser", userDAO.getById(id));
                req.setAttribute("action", "edit");
                req.getRequestDispatcher("/admin/user_form.jsp").forward(req, resp);
                return;
            }
        }
        List<User> users = userDAO.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action) || "update".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");

            if (username == null || username.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPhone(phone);
            user.setAddress(address != null ? address : "");

            if ("add".equals(action)) {
                if (password == null || password.trim().isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                    return;
                }
                user.setPassword(password);
                user.setRole("user");
                userDAO.add(user);
            } else {
                String idStr = req.getParameter("id");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    user.setId(Integer.parseInt(idStr));
                    userDAO.update(user);
                    if (password != null && !password.trim().isEmpty()) {
                        userDAO.updatePassword(user.getId(), password);
                    }
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
