package other.selenium;

import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;

public class Mp4ToMp3 {
    //'/Users/hzwanggaoping/Desktop/教程/[特LJAT曼][微信公众号：西西追剧]/02.mp4'

    public static void main(String[] args) {
//        File input = new File("/Users/hzwanggaoping/Desktop/教程/[特LJAT曼][微信公众号：西西追剧]/02.mp4");
        String inputFilePath = "/Users/hzwanggaoping/Desktop/教程/[特LJAT曼][微信公众号：西西追剧]/02.mp4";
//        File output = new File("wgp_output.mp3");
        String outputFilePath = "wgp_output.mp3";
//        String inputFilePath = "input.mp4";
//        String outputFilePath = "output.mp3";

        try {
            // 构建 FFmpeg 命令
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "/usr/local/Cellar/ffmpeg/7.1_4/bin/ffmpeg",
                    "-i", inputFilePath,    // 输入文件
                    "-vn",                  // 禁用视频流
                    "-acodec", "libmp3lame",// 使用 MP3 编码器
                    "-q:a", "2",            // 音频质量（0-9，0 是最高质量）
                    "-y",                   // 覆盖输出文件（可选）
                    outputFilePath
            );

            // 执行命令并等待完成
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("转换成功！");
            } else {
                System.out.println("转换失败，错误码：" + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
