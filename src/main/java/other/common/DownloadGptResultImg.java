package other.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadGptResultImg {
    public static void downloadImage(String imageUrl, String outputPath) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        connection.connect();

        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(outputPath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("图片已经成功保存！！ " + outputPath);
        }
    }

    public static void main(String[] args) {
        try {
            String imageUrl = "https://filesystem.site/cdn/download/20250506/NxqXuCTpsRelfxVaJykghjYGaooNNe.png";
            String outputPath = "downloaded_image.png";
            downloadImage(imageUrl, outputPath);
            System.out.println("图片下载完成，保存路径: " + outputPath);
        } catch (IOException e) {
            System.err.println("下载图片时出错: " + e.getMessage());
        }
    }
}
