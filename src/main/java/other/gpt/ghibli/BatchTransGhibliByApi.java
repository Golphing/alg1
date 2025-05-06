package other.gpt.ghibli;

import other.common.DownloadGptResultImg;
import other.common.ImageUploaderToCloud;
import other.common.TransImgByGptAPI;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.swing.text.html.parser.DTDConstants.MODEL;

public class BatchTransGhibliByApi {

    static String prompt = "请将我上传的图片生成吉卜力风格的图片";
    static String targetDirPath = "/Users/hzwanggaoping/picred/d/";
    static String sourceDirPath = "/Users/hzwanggaoping/picred/o/";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始前先修改目录和APIkey---targetDirPath");
        System.out.println("开始前先修改目录---targetDirPath");
        System.out.println("开始前先修改目录---targetDirPath");
            // 创建源文件夹和目标文件夹的File对象
            File sourceDir = new File(sourceDirPath);
            File targetDir = new File(targetDirPath);
            while (true){
                // 检查源文件夹是否存在并且是一个目录
                if (sourceDir.exists() && sourceDir.isDirectory()) {
                    // 遍历源文件夹中的所有文件
                    File[] files = sourceDir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile()) { // 判断是否是文件
                                // 构建目标文件路径
                                File targetFile = new File(targetDir, file.getName());
                                // 检查文件在目标文件夹中是否存在
                                if (targetFile.exists()) {
                                    System.out.println("文件 " + file.getName() + " 存在于目标文件夹中。");
                                } else {
                                    System.out.println("文件 " + file.getName() + " 不存在于目标文件夹中。开始处理");
                                    //开始处理
                                    try {
                                        trans(file.getAbsolutePath());
                                        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Thread.sleep(TimeUnit.MINUTES.toMillis(30));
                    } else {
                        System.out.println("源文件夹中没有文件。");
                    }
                } else {
                    System.out.println("源文件夹不存在或不是一个目录。");
                }
            }
    }

    public static void trans(String fileAbsolutePath) throws Exception {
        // 图片 URL
        String imageUrl = ImageUploaderToCloud.uploadImage(new File(fileAbsolutePath));
        System.out.println("图床上传后地址： " + imageUrl);

//        System.out.println("正在使用 " + "" + " 模型分析图像...");
//        System.out.println("图片 URL: " + imageUrl);
        long startTime = new Date().getTime();
        try {
            String url = TransImgByGptAPI.createImageWithOpenAIClient(imageUrl, prompt);
            File file = new File(fileAbsolutePath);
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
