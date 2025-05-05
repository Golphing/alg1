package other.selenium;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import other.common.CommonUtil;

import java.io.IOException;

/**
 * @author Golphing.W
 * @date 2025/3/15 20:18
 */
public class BatchUploadTask {
    public static void main(String[] args) throws InterruptedException, IOException {
        //https://googlechromelabs.github.io/chrome-for-testing/#stable
        System.setProperty("webdriver.chrome.driver", "/Users/Maxuemin/Downloads/chromedriver-mac/chromedriver");

        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://studio.ximalaya.com"); // 确保与loadCookies时使用的域完全一致
        Thread.sleep(20000L);
        driver.manage().window().setSize(new Dimension(1792, 998));
        String intro = "关注后私信，获取资源，关注后私信，获取资源，关注后私信，获取资源，关注后私信，获取资源" +
                "";


        //歌曲

        CommonUtil.batchUpload(driver, js,"", "/Users/Maxuemin/Desktop/englishout/上传/一人一首", "8090", intro, 5);

        //童话
        CommonUtil.batchUpload(driver, js,"", "/Users/Maxuemin/Desktop/englishout/上传/格林童话405首1.5G-1", "格林童话", intro, 30);


        //英语1
        CommonUtil.batchUpload(driver, js,"", "/Users/Maxuemin/Desktop/englishout/上传/版本1-2的语音", "开口说英文不再是难题", intro, 5);


        //英语2
        CommonUtil.batchUpload(driver, js,"", "/Users/Maxuemin/Desktop/englishout/上传/版本2语音", "高频口语生活口语3000句", intro, 5);

    }
}
