package netease.easy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Gpt判断是否有敏感词 {

    public static void main(String[] args) {
        while(true){
            Scanner scanner = new Scanner(System.in);
            String url = "https://autobak.zaiwen.top/api/message";
            String userKey = "npmkl0a1xewqi96ceung0j600eo0ot5r"; // 请替换为你的用户密钥
            String ques = "你是内容安全专家，不允许句子中出现 涉政涉黄的词语，帮我分析 输入的句子中是否有类似词语，如果有的话，指出来敏感词,.注意需要结合上下文来判断是否敏感，不要错误分词导致的误判。请基于上面的要求，帮我识别以下句子是否有敏感词： " ;
            System.out.print("请输入要检查的句子：");
            String mustContainLyric = scanner.nextLine();
            ques += mustContainLyric;
            // 创建 JSON 请求体
            String jsonInputString = "{\"message\":[{\"role\":\"user\",\"content\":\""+ques+"\"}],\"user_key\":\"" + userKey + "\",\"mode\":\"gpt-4o\"}";
            System.out.println(jsonInputString);
            try {
                // 创建 URL 对象
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // 设置请求方式为 POST
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");

                // 发送请求体
                con.setDoOutput(true);
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 处理响应
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        System.out.println("响应内容: " + response.toString());
                    }
                } else {
                    System.out.println("请求失败，状态码: " + responseCode);
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"))) {
                        StringBuilder errorResponse = new StringBuilder();
                        String errorLine;
                        while ((errorLine = br.readLine()) != null) {
                            errorResponse.append(errorLine.trim());
                        }
                        System.out.println(errorResponse.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
