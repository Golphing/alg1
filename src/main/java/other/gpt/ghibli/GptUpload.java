package other.gpt.ghibli;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 *
 *
 document.querySelector('div[role="menuitem"]').click()

 *
 *
 *
 */
public class GptUpload {
    public static boolean upload(){
        try {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("dom.webdriver.enabled", false); // 禁用 WebDriver 标志
            options.addPreference("useAutomationExtension", false); // 禁用自动化扩展
            FirefoxDriver driver = new FirefoxDriver(options);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            driver.get("https://gpt.bestaistore.com/");
            Thread.sleep(60000L);

            driver.get("https://gpt.bestaistore.com/");
            WebElement hidden_input = driver.findElement(By.cssSelector("input[type='file']"));
            hidden_input.sendKeys("/Users/hzwanggaoping/Downloads/a.jpg");
            Thread.sleep(15000L);

            driver.switchTo().frame(driver.findElement(By.className("ke-edit-iframe")));
            Thread.sleep(3000L);
//            js.executeScript(     "arguments[0].innerText = '"+intro+"';",      driver.findElement(By.cssSelector("p[data-flag='normal']")) );
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
