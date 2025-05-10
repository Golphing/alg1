package other.gpt.ghibli;

import other.common.DownloadGptResultImg;
import other.common.ImageUploaderToCloud;
import other.common.TransImgByGptAPI;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PicGenerateWithoutPic {


    static String targetDirPath = "/Users/hzwanggaoping/picred/other/";

    public static void main(String[] args) throws Exception {
        System.out.println("开始前先修改目录和APIkey---targetDirPath");
        System.out.println("开始前先修改目录---targetDirPath");
        System.out.println("开始前先修改目录---targetDirPath");
        String prompt = "帮我生成一家三口在海边手牵着手的写实画面，背朝镜头，高清，杰作";
        trans(prompt, "pic_generate1.png");

    }

    public static void trans(String prompt, String name) throws Exception {
        long startTime = new Date().getTime();
        try {
            String url = TransImgByGptAPI.createImageWithOpenAIClient("", prompt, false);
            File file = new File(targetDirPath + name);
            // 获取文件名（包含文件类型）
            String fileName = file.getName();
            DownloadGptResultImg.downloadImage(url, targetDirPath + fileName);
            long endTime = new Date().getTime();

            // 打印结果
            System.out.println("\n请求耗时: " + (endTime - startTime) / 1000.0 + " 秒");
        } catch (IOException e) {
            System.err.println("处理失败:");
            e.printStackTrace();
        }

    }
}
