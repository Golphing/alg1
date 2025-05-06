package other.common;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

public class ImageUploaderToCloud {

    // FreeImage.Host 的上传接口
    private static final String UPLOAD_URL = "https://freeimage.host/api/1/upload";
    private static final String API_KEY = "6d207e02198a847aa98d0a2a901485a5"; // 官方提供的公开测试 KEY

    public static String uploadImage(File imageFile) throws Exception {
        System.out.println("上传图片到图床");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(UPLOAD_URL);

            // 构建多部分表单
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("key", API_KEY);
            builder.addBinaryBody(
                    "source",
                    imageFile,
                    org.apache.http.entity.ContentType.APPLICATION_OCTET_STREAM,
                    imageFile.getName()
            );

            httpPost.setEntity(builder.build());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);

                // 解析 JSON 响应
                JSONObject json = new JSONObject(responseString);
                if (json.has("image")) {
                    JSONObject imageInfo = json.getJSONObject("image");
                    return imageInfo.getString("url");
                } else {
                    throw new RuntimeException("上传失败: " + json);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            File imageFile = new File("/Users/hzwanggaoping/Downloads/11.jpeg");
            String imageUrl = uploadImage(imageFile);
            System.out.println("图片地址: " + imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}