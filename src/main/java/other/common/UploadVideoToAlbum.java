package other.common;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.openqa.selenium.*;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadVideoToAlbum {

    public static boolean uploadSingleVideoToAlbum1(Page page, String albumName, String filePath, String intro) {
        try {
            // 导航到作品页面
            page.navigate("https://studio.ximalaya.com/opus");

            // 使用智能等待替代固定等待
            FrameLocator frameLocator = page.frameLocator("iframe").first();

            // 选择专辑
            Locator albumSelector = frameLocator.locator(
                    "xpath=//div[contains(text(), '" + albumName + "')]/../../..//div[3]/div[3]/span"
            );
            albumSelector.click();

            // 等待弹窗出现（更可靠的等待方式）
            frameLocator.locator(".primary").click();

            // 文件上传（Playwright原生支持）
            frameLocator.locator("input[type='file']").setInputFiles(Paths.get(filePath));

            // 处理富文本编辑器iframe
            FrameLocator editorFrame = frameLocator.frameLocator(".ke-edit-iframe");
            Locator introEditor = editorFrame.locator("p[data-flag='normal']");

            // 使用Playwright的输入处理代替JS执行
            introEditor.fill(intro);

            // 触发输入事件（针对现代前端框架）
            introEditor.dispatchEvent("input");

            // 提交表单
            frameLocator.locator("#submit-box button.confirm-publish-btn-new-3F0EvXXa").click();
            Thread.sleep(30000);
            // 等待发布完成（推荐使用条件等待）
//            frameLocator.locator(".publish-success-notification", new Page.WaitForSelectorOptions().setTimeout(13000));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // 确保返回主文档
            page.mainFrame();
        }
    }

    public static boolean uploadSingleVideoToAlbum(WebDriver driver, JavascriptExecutor js, String albumName, String filePath, String intro){
        try {
            driver.get("https://studio.ximalaya.com/opus");
            Thread.sleep(3000L);
            driver.switchTo().frame(0);
            Thread.sleep(3000L);
            String albumSelector = "//div[contains(text(), '"+albumName+"')]/../../..//div[3]/div[3]/span";
            driver.findElement(By.xpath(albumSelector)).click();
            Thread.sleep(8000L);
            driver.findElement(By.cssSelector(".primary")).click();
            Thread.sleep(3000L);
            WebElement hidden_input = driver.findElement(By.cssSelector("input[type='file']"));
            hidden_input.sendKeys(filePath);
            Thread.sleep(15000L);
            driver.switchTo().frame(driver.findElement(By.className("ke-edit-iframe")));
            Thread.sleep(3000L);
            js.executeScript(     "arguments[0].innerText = '"+intro+"';",      driver.findElement(By.cssSelector("p[data-flag='normal']")) );
            Thread.sleep(3000L);
            // 触发input事件（Vue/React等框架需要）
            js.executeScript(
                    "var event = new Event('input', { bubbles: true });" +
                            "arguments[0].dispatchEvent(event);",
                    driver.findElement(By.cssSelector("p[data-flag='normal']"))
            );
            driver.findElement(By.cssSelector("p[data-flag='normal']")).sendKeys(Keys.ENTER);
            driver.switchTo().parentFrame();
            Thread.sleep(3000L);
            driver.findElement(By.cssSelector("#submit-box > div > div.publish-box-container-new-7rfTxbuo > div > button.ant-btn.mg-l-10.publish-box-new-btn-1I1nky3y.confirm-publish-btn-new-3F0EvXXa.ant-btn-primary")).click();
            Thread.sleep(13000L);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
