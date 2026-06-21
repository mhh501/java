package com.wdb1.servlet;

import com.wdb1.dao.CartDAO;
import com.wdb1.dao.OrderDAO;
import com.wdb1.entity.Cart;
import com.wdb1.entity.Order;
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
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String action = req.getParameter("action");

        if ("pay".equals(action)) {
            orderDAO.pay(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/order");
            return;
        }
        if ("receive".equals(action)) {
            orderDAO.receive(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/order");
            return;
        }

        List<Order> orders = orderDAO.findByUserId(userId);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/user/orders.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            List<Cart> cartItems = cartDAO.findByUserId(userId);
            if (cartItems.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            int orderId = orderDAO.createOrder(userId, cartItems);
            if (orderId > 0) {
                cartDAO.deleteByUserId(userId);
                resp.sendRedirect(req.getContextPath() + "/order");
            } else {
                req.setAttribute("error", "下单失败");
                req.getRequestDispatcher("/user/cart.jsp").forward(req, resp);
            }
        }
    }
}
