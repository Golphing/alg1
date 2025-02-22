package other.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class FirstScript {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/hzwanggaoping/Downloads/chromedriver-mac-x64 2/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("https://studio.ximalaya.com/");
        System.out.println(driver.getTitle());
        Thread.sleep(30000L);
        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        message.getText();
        Thread.sleep(3000000L);

//        driver.quit();
    }
}
