package com.project.servlet;

import com.project.dao.CartDAO;
import com.project.dao.ClothesDAO;
import com.project.entity.Cart;
import com.project.entity.Clothes;
import com.project.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private ClothesDAO clothesDAO = new ClothesDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("cartList", cartDAO.findByUserId(user.getId()));
        req.getRequestDispatcher("/user/cart.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int clothesId = Integer.parseInt(req.getParameter("clothesId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String size = req.getParameter("size");

            Clothes clothes = clothesDAO.getById(clothesId);
            if (clothes == null || clothes.getStock() < quantity) {
                req.setAttribute("error", "库存不足");
                req.setAttribute("clothes", clothes);
                req.getRequestDispatcher("/user/detail.jsp").forward(req, resp);
                return;
            }

            Cart cart = new Cart();
            cart.setUserId(user.getId());
            cart.setClothesId(clothesId);
            cart.setQuantity(quantity);
            cart.setSize(size);
            cartDAO.add(cart);

            resp.sendRedirect(req.getContextPath() + "/cart");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            cartDAO.updateQuantity(id, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart");

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            cartDAO.delete(id);
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
