package other.common;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author Golphing.W
 * @date 2025/3/15 20:32
 */
public class CommonUtil {
    public static void bat(String sourceDirectoryPath, String destFilePath, String albumSelector, String intro, int num) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/Maxuemin/Downloads/chromedriver-mac-x64/chromedriver");
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://studio.ximalaya.com"); // 确保与loadCookies时使用的域完全一致
        Thread.sleep(20000L);
        driver.manage().window().setSize(new Dimension(1792, 998));
        batchUpload(driver, js, sourceDirectoryPath, destFilePath, albumSelector, intro, num);

    }

    public static void batchUpload(WebDriver driver, JavascriptExecutor js, String sourceDirectoryPath, String destFilePath, String albumSelector, String intro, int num) throws IOException, InterruptedException {

        try {
            //先拷贝一下文件，保留原始文件目录
            if(sourceDirectoryPath != null && !"".equals(sourceDirectoryPath)){
                copyFiles(sourceDirectoryPath, destFilePath);
            }

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
                                if(errorCount>50){
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
                        System.out.println("处理成功："+successCount);
                        successCount++;
                        if(successCount > num){
                            System.out.println("成功了20个，终止任务");
                            return;
                        }
                        Thread.sleep(10000L);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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
