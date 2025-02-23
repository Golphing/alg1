package other.selenium;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.*;

public class 版本2上传 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String sourceP = "";
        String destP = "/Users/Maxuemin/Desktop/englishout/上传/版本2语音";
        String albumSelector = "#root > section > div.Home_homeMain__3aDEt > div.false > section.AlbumItem_listItem__g-MZx.AlbumItem_gray__iCzAl > div.AlbumItem_itemRight__fbONX > div:nth-child(3) > span";
//                #root > section > div.Home_homeMain__3aDEt > div.false > section:nth-child(2) > div.AlbumItem_itemRight__fbONX > div:nth-child(3) > span
//                #root > section > div.Home_homeMain__3aDEt > div.false > section.AlbumItem_listItem__g-MZx.AlbumItem_gray__iCzAl > div.AlbumItem_itemRight__fbONX > div:nth-child(3) > span
        String intro = "每日英语口语，如需电子文档和音频文件，请点击关注私信索要~<br>每日英语口语，如需电子文档和音频文件，请点击关注私信索要~<br>" +
                "每日英语口语，如需电子文档和音频文件，请点击关注私信索要~<br>" +
                "每日英语口语，如需电子文档和音频文件，请点击关注私信索要~<br>" +
                "每日英语口语，如需电子文档和音频文件，请点击关注私信索要~<br>" +
                "每日英语口语，如需电子文档和音频文件，请点击关注私信索要~<br>" +
                "";
        batchUpload(sourceP, destP, albumSelector, intro);
    }

    public static void batchUpload(String sourceDirectoryPath, String destFilePath, String albumSelector, String intro) throws IOException, InterruptedException {
        //先拷贝一下文件，保留原始文件目录
        if(sourceDirectoryPath != null && !"".equals(sourceDirectoryPath)){
            copyFiles(sourceDirectoryPath, destFilePath);
        }
        System.setProperty("webdriver.chrome.driver", "/Users/Maxuemin/Downloads/chromedriver-mac-x64/chromedriver");
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://studio.ximalaya.com"); // 确保与loadCookies时使用的域完全一致
        Thread.sleep(20000L);
        driver.manage().window().setSize(new Dimension(1792, 998));
        int errorCount = 0;
        int successCount = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(destFilePath))) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    // 如果是常规文件，打印其绝对路径
                    System.out.println("开始处理文件： "+entry.getFileName());
                    try {
                        boolean ret = UploadVideoToAlbum.uploadSingleVideoToAlbum(driver, js, albumSelector, entry.toFile().getAbsolutePath(), intro);
                        if(!ret){
                            System.out.println("处理失败");
                            errorCount++;
                            if(errorCount>5){
                                System.out.println("失败过多，终止任务");
                                return;
                            }
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("处理失败");
                        errorCount++;
                        if(errorCount>5){
                            System.out.println("失败过多，终止任务");
                            return;
                        }
                        continue;
                    }
                    Files.delete(entry);
                    System.out.println("处理成功");
                    successCount++;
                    if(successCount > 20){
                        System.out.println("成功了20个，终止任务");
                        return;
                    }
                    Thread.sleep(10000L);

                }
            }
        }
    }

    private static void copyFiles(String sourceDir, String targetDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        Path targetPath = Paths.get(targetDir);

        // 创建目标文件夹（如果不存在）
        if (Files.notExists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        // 遍历源文件夹中的所有文件
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(sourcePath)) {
            for (Path file : directoryStream) {
                Path targetFilePath = targetPath.resolve(file.getFileName());
                // 复制文件
                Files.copy(file, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
