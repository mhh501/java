package com.pyh1.servlet;

import com.pyh1.dao.ClothesDAO;
import com.pyh1.entity.Clothes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/clothes")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class AdminClothesServlet extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                clothesDAO.delete(Integer.parseInt(idStr));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/clothes");
            return;
        }
        if ("add".equals(action)) {
            req.setAttribute("categories", clothesDAO.getCategories());
            req.getRequestDispatcher("/admin/clothes_form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                req.setAttribute("clothes", clothesDAO.getById(Integer.parseInt(idStr)));
                req.setAttribute("categories", clothesDAO.getCategories());
                req.getRequestDispatcher("/admin/clothes_form.jsp").forward(req, resp);
                return;
            }
        }
        String keyword = req.getParameter("keyword");
        String category = req.getParameter("category");
        List<Clothes> list = clothesDAO.search(keyword, category, null);
        req.setAttribute("clothesList", list);
        req.setAttribute("categories", clothesDAO.getCategories());
        req.getRequestDispatcher("/admin/clothes.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String name = req.getParameter("clothName");
        String style = req.getParameter("style");
        String category = req.getParameter("category");
        String size = req.getParameter("size");
        String priceStr = req.getParameter("price");
        String stockStr = req.getParameter("stock");
        String description = req.getParameter("description");
        String imageUrl = req.getParameter("existingImage");

        // Validate required numeric fields
        if (priceStr == null || priceStr.trim().isEmpty() ||
            stockStr == null || stockStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/clothes");
            return;
        }
        double price = Double.parseDouble(priceStr);
        int stock = Integer.parseInt(stockStr);

        Part filePart = req.getPart("clothImage");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + getFileName(filePart);
            String uploadPath = getServletContext().getRealPath("/upload");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(uploadPath + File.separator + fileName);
            imageUrl = "upload/" + fileName;
        }

        if ("add".equals(action)) {
            Clothes c = new Clothes();
            c.setName(name); c.setStyle(style != null ? style : "");
            c.setCategory(category != null ? category : "");
            c.setSize(size != null ? size : "");
            c.setPrice(price); c.setStock(stock);
            c.setImageUrl(imageUrl != null ? imageUrl : "");
            c.setDescription(description != null ? description : "");
            clothesDAO.add(c);
        } else if ("update".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                Clothes c = new Clothes();
                c.setId(Integer.parseInt(idStr));
                c.setName(name); c.setStyle(style != null ? style : "");
                c.setCategory(category != null ? category : "");
                c.setSize(size != null ? size : "");
                c.setPrice(price); c.setStock(stock);
                c.setImageUrl(imageUrl != null ? imageUrl : "");
                c.setDescription(description != null ? description : "");
                clothesDAO.update(c);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/clothes");
    }

    private String getFileName(Part part) {
        String header = part.getHeader("content-disposition");
        for (String token : header.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "unknown";
    }
}
