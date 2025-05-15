package controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
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
            // 🔽 從表單取得商品 ID（用於更新該商品的 image_url 欄位）
            int productId = Integer.parseInt(request.getParameter("productId"));

            // 🔽 取得使用者上傳的圖片
            Part filePart = request.getPart("image");
            InputStream fileStream = filePart.getInputStream();

            // 🔽 上傳到 Cloudinary
            @SuppressWarnings("rawtypes")
			Map uploadResult = cloudinary.uploader().upload(fileStream, ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");

            // 🔽 更新該商品的 imageUrl 欄位
            ProductDAO productDAO = new ProductDAO();
            productDAO.updateImageUrl(productId, imageUrl);

            response.sendRedirect("ProductController?action=modify&productId=" + productId);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("圖片上傳失敗：" + e.getMessage());
        }
    }
}
