package com.pyh1.servlet;

import com.pyh1.dao.CartDAO;
import com.pyh1.dao.ClothesDAO;
import com.pyh1.dao.OrderDAO;
import com.pyh1.entity.Cart;
import com.pyh1.entity.Clothes;
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
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if ("add".equals(action)) {
            String clothesIdStr = req.getParameter("clothesId");
            String quantityStr = req.getParameter("quantity");
            if (clothesIdStr == null || clothesIdStr.trim().isEmpty() ||
                quantityStr == null || quantityStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/clothes");
                return;
            }
            int clothesId = Integer.parseInt(clothesIdStr);
            int quantity = Integer.parseInt(quantityStr);

            // size param format: "S|79" -- extract size name and price
            String size = req.getParameter("size");
            if (size == null || size.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/detail?id=" + clothesId);
                return;
            }
            int pipeIdx = size.indexOf('|');
            String sizeOnly = pipeIdx > 0 ? size.substring(0, pipeIdx) : size;

            Clothes clothes = clothesDAO.getById(clothesId);
            if (clothes == null || clothes.getStock() < quantity) {
                resp.sendRedirect(req.getContextPath() + "/detail?id=" + clothesId);
                return;
            }

            // Use size-specific price if available, fall back to clothes price
            double sizePrice = clothes.getPrice();
            if (pipeIdx > 0) {
                try {
                    sizePrice = Double.parseDouble(size.substring(pipeIdx + 1));
                } catch (NumberFormatException e) {
                    // fall back to base price
                }
            }

            Cart cart = new Cart();
            cart.setUserId(userId); cart.setClothesId(clothesId);
            cart.setQuantity(quantity); cart.setSize(sizeOnly); cart.setPrice(sizePrice);
            cartDAO.add(cart);
            resp.sendRedirect(req.getContextPath() + "/cart");

        } else if ("update".equals(action)) {
            String idStr = req.getParameter("id");
            String quantityStr = req.getParameter("quantity");
            if (idStr == null || idStr.trim().isEmpty() ||
                quantityStr == null || quantityStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            int id = Integer.parseInt(idStr);
            int quantity = Integer.parseInt(quantityStr);
            cartDAO.updateQuantity(id, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
