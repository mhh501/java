package com.zax1.servlet;

import com.zax1.dao.CartDAO;
import com.zax1.dao.ClothesDAO;
import com.zax1.dao.OrderDAO;
import com.zax1.entity.Cart;
import com.zax1.entity.Clothes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private ClothesDAO clothesDAO = new ClothesDAO();
    private OrderDAO orderDAO = new OrderDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
            cartDAO.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        if ("checkout".equals(action)) {
            List<Cart> cartItems = cartDAO.findByUserId(userId);
            if (!cartItems.isEmpty()) {
                orderDAO.createOrder(userId, cartItems);
                cartDAO.deleteByUserId(userId);
            }
            resp.sendRedirect(req.getContextPath() + "/order");
            return;
        }
        req.setAttribute("cartItems", cartDAO.findByUserId(userId));
        req.getRequestDispatcher("/user/cart.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer userId = (Integer) req.getSession().getAttribute("userId");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int clothesId = Integer.parseInt(req.getParameter("clothesId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String size = req.getParameter("size");
            Clothes clothes = clothesDAO.getById(clothesId);
            if (clothes == null || clothes.getStock() < quantity) {
                resp.sendRedirect(req.getContextPath() + "/detail?id=" + clothesId);
                return;
            }
            Cart cart = new Cart();
            cart.setUserId(userId); cart.setClothesId(clothesId);
            cart.setQuantity(quantity); cart.setSize(size); cart.setPrice(clothes.getPrice());
            cartDAO.add(cart);
            resp.sendRedirect(req.getContextPath() + "/cart");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            cartDAO.updateQuantity(id, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}