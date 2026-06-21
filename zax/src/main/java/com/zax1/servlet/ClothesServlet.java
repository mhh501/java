package com.zax1.servlet;

import com.zax1.dao.ClothesDAO;
import com.zax1.entity.Clothes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/clothes")
public class ClothesServlet extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        String category = req.getParameter("category");
        String style = req.getParameter("style");

        List<Clothes> list = clothesDAO.search(keyword, category, style);
        List<String> categories = clothesDAO.getCategories();
        List<String> styles = clothesDAO.getStyles();

        req.setAttribute("clothesList", list);
        req.setAttribute("categories", categories);
        req.setAttribute("styles", styles);
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedCategory", category);
        req.setAttribute("selectedStyle", style);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
