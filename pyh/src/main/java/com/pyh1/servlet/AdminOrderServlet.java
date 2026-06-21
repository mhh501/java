package com.pyh1.servlet;

import com.pyh1.dao.OrderDAO;
import com.pyh1.entity.Order;
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
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                orderDAO.ship(Integer.parseInt(idStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }

        String keyword = req.getParameter("keyword");
        String status = req.getParameter("status");
        List<Order> orders = orderDAO.search(keyword, status);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
    }
}
