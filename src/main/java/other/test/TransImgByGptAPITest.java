package other.test;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import other.common.DownloadGptResultImg;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransImgByGptAPITest {

    // API 配置
    private static final String API_KEY = "sk-j3RHjHfwSsshuivoMEjYb6sUT9s0cbe6NBxbL5YjsRjR0xsy";
    private static final String BASE_URL = "https://aihubmax.com/v1";
    private static final String MODEL = "gpt-4o-image";
    private static final String ENDPOINT = BASE_URL + "/chat/completions";

    public static String createImageWithOpenAIClient(String imageUrl, String prompt) throws IOException {
        System.out.println("使用 OpenAI 客户端方法调用 API...");
        System.out.println("图片 URL: " + imageUrl);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(ENDPOINT);

            // 构建请求头
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + API_KEY);

            // 构建 JSON 请求体
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", MODEL);
            requestBody.addProperty("stream", false);

            JsonArray messages = new JsonArray();
            JsonObject message = new JsonObject();
            message.addProperty("role", "user");

            JsonArray content = new JsonArray();

            // 文本部分
            JsonObject textContent = new JsonObject();
            textContent.addProperty("type", "text");
            textContent.addProperty("text", prompt);
            content.add(textContent);

            // 图片部分
            JsonObject imageContent = new JsonObject();
            imageContent.addProperty("type", "image_url");

            JsonObject imageUrlObj = new JsonObject();
            imageUrlObj.addProperty("url", imageUrl);

            imageContent.add("image_url", imageUrlObj);
            content.add(imageContent);

            message.add("content", content);
            messages.add(message);
            requestBody.add("messages", messages);

            // 设置请求体
            httpPost.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new IOException("API请求失败: " + statusCode + " " + response.getStatusLine().getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);
                JsonObject responseJson = new JsonParser().parse(responseString).getAsJsonObject();

                return responseJson.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content").getAsString();
            }
        }
    }

    public static List<String> extractAllDownloadUrls(String input) {
        List<String> urls = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\[点击下载\\]\\((https?://[^\\s]+)\\)").matcher(input);

        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }

    public static void main(String[] args) {
        // 图片 URL
        String imageUrl = "https://iili.io/3O2uBrG.jpg";
        // 提示词
        String prompt = "请将我上传的图片生成吉卜力风格的图片";

        System.out.println("正在使用 " + MODEL + " 模型分析图像...");
        System.out.println("API 基础 URL: " + BASE_URL);
        System.out.println("图片 URL: " + imageUrl);
        long startTime = new Date().getTime();
        try {
            String result = createImageWithOpenAIClient(imageUrl, prompt);
            if(!result.contains("点击下载")){
                System.out.println("生成失败： " + result);
                throw new RuntimeException("失败");
            }
            List<String> url1 = extractAllDownloadUrls(result);
            if(url1 == null || url1.size() == 0){

            }
            String url = url1.get(0);
            DownloadGptResultImg.downloadImage(url, "aa.png");
            long endTime = new Date().getTime();

            // 打印结果
            System.out.println("\n请求耗时: " + (endTime - startTime) / 1000.0 + " 秒");
            System.out.println("\n分析结果:");
            printSeparator();
            System.out.println(result);
            printSeparator();
        } catch (IOException e) {
            System.err.println("处理失败:");
            e.printStackTrace();
        }
    }

    private static void printSeparator() {
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            separator.append("-");
        }
        System.out.println(separator);
    }
}
