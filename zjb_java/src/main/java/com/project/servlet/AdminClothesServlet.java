package com.project.servlet;

import com.project.dao.ClothesDAO;
import com.project.entity.Clothes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/clothes")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminClothesServlet extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            clothesDAO.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/clothes");
            return;
        }
        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("clothes", clothesDAO.getById(id));
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

        if ("add".equals(action) || "update".equals(action)) {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String style = req.getParameter("style");
            String category = req.getParameter("category");
            String size = req.getParameter("size");
            double price = Double.parseDouble(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            String description = req.getParameter("description");

            String image = req.getParameter("existingImage");
            Part filePart = req.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = UUID.randomUUID().toString() + "_" + getFileName(filePart);
                String uploadPath = getServletContext().getRealPath("/uploads");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                filePart.write(uploadPath + File.separator + fileName);
                image = fileName;
            }

            Clothes c = new Clothes();
            c.setName(name);
            c.setStyle(style);
            c.setCategory(category);
            c.setSize(size);
            c.setPrice(price);
            c.setStock(stock);
            c.setImage(image != null ? image : "");
            c.setDescription(description != null ? description : "");

            if ("add".equals(action)) {
                clothesDAO.add(c);
            } else {
                c.setId(Integer.parseInt(idStr));
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
