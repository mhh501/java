package com.zax1.servlet;

import com.zax1.dao.ClothesDAO;
import com.zax1.entity.Clothes;
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
                req.setAttribute("clothes", clothes);
                req.getRequestDispatcher("/user/detail.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
