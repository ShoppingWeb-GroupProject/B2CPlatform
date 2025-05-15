package controller;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import dao.ProductImageDAO;
import model.ProductImage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.InputStream;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("serial")
@WebServlet("/UploadImageServlet")
@MultipartConfig
public class UploadImageServlet extends HttpServlet {
    private Cloudinary cloudinary;

    @Override
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dsnzdecej",
                "api_key", "588179336638767",
                "api_secret", "8frJ3t9Cb_-CEPhDcNTVFNLZsAA"
        ));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Part filePart = request.getPart("image");
            InputStream fileStream = filePart.getInputStream();

            // 上傳到 Cloudinary
            @SuppressWarnings("rawtypes")
			Map uploadResult = cloudinary.uploader().upload(fileStream, ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");

            // 建立圖片物件
            ProductImage image = new ProductImage();
            image.setProductId(productId);
            image.setImageUrl(imageUrl);
            image.setMain(true); // 預設為主圖

            // 儲存到資料庫
            ProductImageDAO dao = new ProductImageDAO();
            dao.addProductImage(image);

            response.sendRedirect("product.jsp?id=" + productId);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("圖片上傳失敗：" + e.getMessage());
        }
    }
}
