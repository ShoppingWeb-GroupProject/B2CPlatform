package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class UploadUtil {
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
          "cloud_name", "dsnzdecej",
          "api_key", "588179336638767",
          "api_secret", "8frJ3t9Cb_-CEPhDcNTVFNLZsAA"
    ));

    public static String uploadImage(Part imagePart) throws IOException {
        // 將 Part 轉為 byte[]
        byte[] fileBytes = toByteArray(imagePart.getInputStream());

        // 上傳 byte[] 給 Cloudinary
        @SuppressWarnings("rawtypes")
		Map uploadResult = cloudinary.uploader().upload(fileBytes, ObjectUtils.emptyMap());

        return uploadResult.get("secure_url").toString();
    }

    private static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] temp = new byte[4096];
        int n;
        while ((n = in.read(temp)) != -1) {
            buffer.write(temp, 0, n);
        }
        return buffer.toByteArray();
    }
    
    public static void deleteImageByUrl(String imageUrl) throws Exception {
        // 例：從 https://res.cloudinary.com/.../upload/v1234567890/abcxyz.jpg 擷取 abcxyz
        String publicId = extractPublicId(imageUrl);

        if (publicId != null) {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
    }

    // 幫你從 imageUrl 中擷取出 public_id
    private static String extractPublicId(String url) {
        // 去掉開頭與副檔名
        // Ex: https://res.cloudinary.com/xxx/image/upload/v1234567890/abcxyz.png → abcxyz
        if (url == null) return null;
        try {
            String filename = url.substring(url.lastIndexOf('/') + 1);
            return filename.replaceFirst("\\.[^.]+$", ""); // 去掉副檔名
        } catch (Exception e) {
            return null;
        }
    }

}


