package other.gpt.ghibli;

import org.openqa.selenium.Cookie;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedImageDownloader {
//    private static String urii = "https://www.xiaohongshu.com/explore/67ed0c75000000001d01fdee?xsec_token=ABYWBltI3VOwsxMqJ2f9J6UDyV9gxJde2UYOjIh9WvIgU=&xsec_source=pc_search&source=web_explore_feed";
    private static String urii = "https://www.xiaohongshu.com/explore/67ed3038000000001d0191c8?channelType=web_engagement_notification_page&channelTabId=mentions&xsec_token=LBE_3SXesMXJXTluFs0Z2s5gQLMZ5pmUZu8ZHWvpl0uGk=&xsec_source=pc_notice";
//    private static String urii = "https://www.xiaohongshu.com/explore/67ed0c75000000001d01fdee?xsec_token=AB59Aak7vbTKh7gEyn9dwrcyf80BTHEK3zw-Q6KMqycw4=&xsec_source=pc_user";
    private WebDriver driver;

    private static final String COOKIE_FILE = "cookies.json";

    private WebDriverWait wait;

    public static int newA = 0;
    public static int oldA = 0;

    public RedImageDownloader() {
        this.driver = initBrowser();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private WebDriver initBrowser() {
        System.out.println("每次需要换下链接，链接过期识别是爬虫");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.cache.disk.parent_directory", "/path/to/custom/cache");
        profile.setPreference("browser.download.dir", "/path/to/downloads");

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.webdriver.enabled", false); // 禁用 WebDriver 标志
        options.addPreference("useAutomationExtension", false); // 禁用自动化扩展
        FirefoxDriver driver = new FirefoxDriver(options);
        return driver;
    }

    private void handleLoginPopup() {
        try {
            // 登录成功后保存cookies
            Thread.sleep(30000);
//            saveCookies(driver);
//            driver.quit();
            try {
//                loadCookies(driver);
                Thread.sleep(TimeUnit.MINUTES.toMillis(1));
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();         // 忽略异常
        }
    }

    // 保存 Cookies 到 JSON 文件
    private static void saveCookies(WebDriver driver) {
        Set<Cookie> cookies = driver.manage().getCookies();
        try {

            // 将 Cookie 转换为可序列化的 Map 列表
            List<Map<String, Object>> cookieList = new ArrayList<>();
            for (Cookie cookie : cookies) {
                Map<String, Object> cookieMap = new HashMap<>();
                cookieMap.put("name", cookie.getName());
                cookieMap.put("value", cookie.getValue());
                cookieMap.put("domain", cookie.getDomain());
                cookieMap.put("path", cookie.getPath());
                cookieMap.put("expiry", cookie.getExpiry() != null ? cookie.getExpiry().getTime() : null);
                cookieMap.put("secure", cookie.isSecure());
                cookieMap.put("httpOnly", cookie.isHttpOnly());
                // Selenium 4+ 支持 sameSite
//                cookieMap.put("sameSite", cookie.getSameSite());
                cookieList.add(cookieMap);
            }

            // 序列化为 JSON 文件
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("cookies.json"), cookieList);
            System.out.println("Cookies 保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从文件加载 Cookies
    private static void loadCookies(WebDriver driver) {
        try {
            // 必须先访问目标域名以关联 Cookie（关键步骤！）
            driver.get(urii);

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Cookie.class, new SeleniumCookieDeserializer());
            mapper.registerModule(module);

            // 读取 Cookie 数组
            Cookie[] cookies = mapper.readValue(new File(COOKIE_FILE), Cookie[].class);

            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
            System.out.println("Cookies 加载成功！");

            // 刷新页面使 Cookie 生效
            driver.navigate().refresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrollToLoadComments(int scrollTimes) throws InterruptedException {
        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".note-scroller")
        ));
        for(int i=0;i<25;i++){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollTop += 2500;", container);
            Thread.sleep(2000L);
        }
    }

    private List<String> getRecentImages() {
        List<WebElement> comments = driver.findElements(By.xpath("//div[@class='parent-comment']/div[@class='comment-item']//div[@class='comment-picture']//img"));
        List<String> imageUrls = new ArrayList<>();
        
        for (WebElement comment : comments) {
            try {
                // 假设已定位到原始元素：ElementHandle targetElement
                String ancestorXpath = "./ancestor::div[@class='parent-comment'][1]";
                WebElement ancestor = comment.findElement(By.xpath(ancestorXpath));

                String descendantSelector = ".//div[@class='reply-container']";
                List<WebElement> replyContainer = ancestor.findElements(By.xpath(descendantSelector));
                if(replyContainer != null && replyContainer.size() > 0){
                    System.out.println("图片回复过：" + comment.getAttribute("src"));
                    continue;
                }
                imageUrls.add(comment.getAttribute("src"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return imageUrls;
    }

    public static void downloadImage(String url, String saveDir, String filename) throws Exception {
        try {
            // 创建保存路径
            Files.createDirectories(Paths.get(saveDir));
            Thread.sleep(2000);
            Path filePath = Paths.get(saveDir, filename);

            // 检查文件是否已经存在
            if (Files.exists(filePath)) {
                System.out.println("文件已存在：" + filePath.toString());
                oldA++;
                return; // 如果文件已存在，直接返回
            }

            // 下载并保存文件
            try (CloseableHttpClient client = HttpClients.createDefault()) {

                HttpGet request = new HttpGet(url);
                request.addHeader("User-Agent", "Mozilla/5.0");

                try (CloseableHttpResponse response = client.execute(request);
                     InputStream is = response.getEntity().getContent()) {
                    Files.copy(is, filePath);
                    newA++;
                    System.out.println(filename + " 文件已下载并保存至：" + filePath.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String targetUrl) {
        try {
            driver.get(targetUrl);
            driver.manage().window().fullscreen();
            handleLoginPopup();
            driver.get(targetUrl);
            scrollToLoadComments(5);
            
            List<String> imageUrls = getRecentImages();
            System.out.println("找到" + imageUrls.size() + "张符合要求的图片");
            
            for (String url : imageUrls) {
                int queryIndex1 = url.indexOf("?");
                String fn = url;
                if (queryIndex1 != -1) {
                    String baseUrl = url.substring(0, queryIndex1);
                    fn = baseUrl;
                }

                // 生成文件名
                String filename = DigestUtils.md5Hex(fn) + ".jpg";
                int queryIndex = url.indexOf("?");
                if (queryIndex != -1) {
                    String baseUrl = url.substring(0, queryIndex);
                    String query = url.substring(queryIndex + 1);
                    query = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
                    url = baseUrl + "?" + query;
                }
                downloadImage(url, "/Users/Maxuemin/o", filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        String targetUrl = urii;

        new RedImageDownloader().run(targetUrl);
        System.out.println("新加：" + newA + "  old : " + oldA);
    }

    static class SeleniumCookieDeserializer extends StdDeserializer<Cookie> {
        public SeleniumCookieDeserializer() {
            super(Cookie.class);
        }

        @Override
        public Cookie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            String name = node.get("name").asText();
            String value = node.get("value").asText();
            String domain = node.get("domain").asText();
            String path = node.get("path").asText();
            long expiry = node.get("expiry").asLong();
            boolean isSecure = node.get("secure").asBoolean();
            boolean httpOnly = node.get("httpOnly").asBoolean();
//            String sameSite = node.get("sameSite").asText();

            // 构造 Cookie 对象
            Date expiryDate = new Date(expiry);
            Cookie cookie = new Cookie(name, value, domain, path, expiryDate, isSecure, httpOnly, "");
            return cookie;
        }
    }
}