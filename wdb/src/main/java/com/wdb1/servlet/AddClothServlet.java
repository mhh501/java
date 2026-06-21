package com.wdb1.servlet;

import com.wdb1.dao.ClothesDAO;
import com.wdb1.entity.Clothes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/AddClothServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class AddClothServlet extends HttpServlet {

    private ClothesDAO clothesDAO = new ClothesDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("clothName");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");
        String style = request.getParameter("style");
        String size = request.getParameter("size");
        String stockStr = request.getParameter("stock");
        String description = request.getParameter("description");

        // Handle image upload
        Part imagePart = request.getPart("clothImage");
        String imageUrl = "";
        if (imagePart != null && imagePart.getSize() > 0) {
            String submittedFileName = imagePart.getSubmittedFileName();
            String newFileName = UUID.randomUUID().toString().replace("-", "") + "_" + submittedFileName;
            String uploadPath = request.getServletContext().getRealPath("/upload");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            imagePart.write(uploadPath + File.separator + newFileName);
            imageUrl = "upload/" + newFileName;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            Clothes c = new Clothes();
            c.setName(name);
            c.setStyle(style != null ? style : "");
            c.setCategory(category != null ? category : "");
            c.setSize(size != null ? size : "");
            c.setPrice(price);
            c.setStock(stock);
            c.setImageUrl(imageUrl);
            c.setDescription(description != null ? description : "");

            clothesDAO.add(c);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/clothes");
    }
}
