package com.project.servlet;

import com.project.dao.OrderDAO;
import com.project.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("ship".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            orderDAO.ship(id);
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }

        String keyword = req.getParameter("keyword");
        String status = req.getParameter("status");
        List<Order> orders = orderDAO.search(keyword, status);
        req.setAttribute("orders", orders);
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedStatus", status);
        req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
    }
}
