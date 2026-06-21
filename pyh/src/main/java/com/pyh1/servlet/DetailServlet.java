package com.pyh1.servlet;

import com.pyh1.dao.ClothesDAO;
import com.pyh1.entity.Clothes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Clothes clothes = clothesDAO.getById(id);
            if (clothes != null) {
                req.setAttribute("cloth", clothes);
            // Parse size field (format: "S|79,M|89,L|99") into a list
            String sizeStr = clothes.getSize();
            if (sizeStr != null && !sizeStr.trim().isEmpty()) {
                req.setAttribute("sizeList", sizeStr.split(","));
            } else {
                req.setAttribute("sizeList", new String[0]);
            }
                req.getRequestDispatcher("/user/detail.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
