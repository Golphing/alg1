package other.selenium;

import org.openqa.selenium.*;

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
    public static boolean uploadSingleVideoToAlbum(WebDriver driver, JavascriptExecutor js, String albumSelector, String filePath, String intro){
        try {
            driver.get("https://studio.ximalaya.com/opus");
            Thread.sleep(3000L);
            driver.switchTo().frame(0);
            Thread.sleep(3000L);
//            driver.findElement(By.cssSelector(".AlbumItem_listItem__g-MZx:nth-child(2) .XButton2_buttonWrap2__Y-nR8:nth-child(3) > span")).click();
            driver.findElement(By.cssSelector(albumSelector)).click();
            Thread.sleep(3000L);
            driver.findElement(By.cssSelector(".primary")).click();
            Thread.sleep(3000L);
            WebElement hidden_input = driver.findElement(By.cssSelector("input[type='file']"));
            hidden_input.sendKeys(filePath);
            Thread.sleep(3000L);
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
            Thread.sleep(3000L);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
