package other.selenium;// Generated by Selenium IDE
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
public class T0220Test {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "/Users/hzwanggaoping/Downloads/chromedriver-mac-x64 2/chromedriver");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void t0220() throws InterruptedException {
    driver.get("https://studio.ximalaya.com"); // 确保与loadCookies时使用的域完全一致
    Thread.sleep(10000L);
    driver.manage().window().setSize(new Dimension(1792, 998));
    driver.get("https://studio.ximalaya.com/opus");
    Thread.sleep(3000L);
    driver.switchTo().frame(0);
    Thread.sleep(3000L);
    driver.findElement(By.cssSelector(".AlbumItem_listItem__g-MZx:nth-child(2) .XButton2_buttonWrap2__Y-nR8:nth-child(3) > span")).click();
    Thread.sleep(3000L);
    driver.findElement(By.cssSelector(".primary")).click();
    Thread.sleep(3000L);
    WebElement hidden_input = driver.findElement(By.cssSelector("input[type='file']"));
    hidden_input.sendKeys("/Users/hzwanggaoping/Documents/音乐.mp3");
    Thread.sleep(3000L);
    driver.switchTo().frame(driver.findElement(By.className("ke-edit-iframe")));
    Thread.sleep(3000L);
    js.executeScript(     "arguments[0].innerText = '动态插入的文本';",      driver.findElement(By.cssSelector("p[data-flag='normal']")) );
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
    WebElement textarea = driver.findElement(By.tagName("textarea"));
    Thread.sleep(3000L);

    driver.findElement(By.cssSelector("#submit-box > div > div.publish-box-container-new-7rfTxbuo > div > button.ant-btn.mg-l-10.publish-box-new-btn-1I1nky3y.confirm-publish-btn-new-3F0EvXXa.ant-btn-primary")).click();
    Thread.sleep(3000L);
  }


  // 序列化Cookie到文件
  public static void saveCookies(WebDriver driver, String filePath) {
    Set<Cookie> cookies = driver.manage().getCookies();
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
      oos.writeObject(cookies);
    } catch (IOException e) {
      throw new RuntimeException("Cookie保存失败", e);
    }
  }

  // 从文件加载Cookie到Driver
  public static void loadCookies(WebDriver driver, String filePath, String targetDomain) {
    driver.get(targetDomain);

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
      @SuppressWarnings("unchecked")
      Set<Cookie> cookies = (Set<Cookie>) ois.readObject();

      Date now = new Date();
      cookies.stream()
              .filter(cookie -> cookie.getExpiry() == null || cookie.getExpiry().after(now))
              .forEach(cookie -> {
                // 创建新的Cookie对象强制匹配当前域
                Cookie adjustedCookie = new Cookie(
                        cookie.getName(),
                        cookie.getValue(),
                        ".ximalaya.com", // 统一使用根域
                        cookie.getPath(),
                        cookie.getExpiry(),
                        cookie.isSecure(),
                        cookie.isHttpOnly()
                );
                try {
                  driver.manage().addCookie(adjustedCookie);
                } catch (InvalidCookieDomainException e) {
                  System.out.println("处理特殊域: " + e.getMessage());
                }
              });

      driver.navigate().refresh();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
