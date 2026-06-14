package com.project.servlet;

import com.project.dao.CartDAO;
import com.project.dao.OrderDAO;
import com.project.entity.Cart;
import com.project.entity.Order;
import com.project.entity.OrderItem;
import com.project.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("pay".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            orderDAO.pay(id);
            resp.sendRedirect(req.getContextPath() + "/order");
            return;
        }
        if ("receive".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            orderDAO.receive(id);
            resp.sendRedirect(req.getContextPath() + "/order");
            return;
        }

        List<Order> orders = orderDAO.findByUserId(user.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/user/orders.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            List<Cart> cartItems = cartDAO.findByUserId(user.getId());
            if (cartItems.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            int orderId = orderDAO.createOrder(user.getId(), cartItems);
            if (orderId > 0) {
                cartDAO.deleteByUserId(user.getId());
                resp.sendRedirect(req.getContextPath() + "/order");
            } else {
                req.setAttribute("error", "下单失败");
                req.getRequestDispatcher("/user/cart.jsp").forward(req, resp);
            }
        }
    }
}
