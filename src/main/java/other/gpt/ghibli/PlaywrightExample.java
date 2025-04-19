package other.gpt.ghibli;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PlaywrightExample {
    private static final Path COOKIE_PATH = Paths.get("/Users/hzwanggaoping/trae项目/redb/cookies_gpt.json");

    public static void main(String[] args) {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        System.out.println(width + " " + height);
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList(
                    "--start-maximized",        // 窗口最大化（[[13]](#__13)）
                    "--no-sandbox"             // 解决 Linux 权限问题（[[13]](#__13)）
            )));

            BrowserContext context = null;
            if (COOKIE_PATH.toFile().exists()) {
                context = browser.newContext(
                        new Browser.NewContextOptions()
                                .setStorageStatePath(COOKIE_PATH).setViewportSize(null));
            }else {
                context = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(null));
            }

            // 启动轨迹录制（核心配置）
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)   // 启用截图
                    .setSnapshots(true)     // 启用 DOM 快照
                    .setSources(true)       // 记录操作源码（需配置环境变量）
            );

            final Page page = context.newPage();
            page.setViewportSize(width, height);


            // 打开目标网站
            page.navigate("https://gpt.bestaistore.com/");
            page.evaluate("document.documentElement.requestFullscreen()");

            // 检查是否已登录
            if (!COOKIE_PATH.toFile().exists()) {
                System.out.println("请手动登录，等待120秒...");
                page.waitForTimeout(120_000); // 等待2分钟用于登录
                saveCookies(context, context.cookies());
            }

            // 等待页面加载
            page.waitForLoadState(LoadState.NETWORKIDLE);
            Locator imgLocator1 = page.locator("div.betweenFlex > button");
            // 触发点击（自动等待元素可操作）
            imgLocator1.click(new Locator.ClickOptions()
                    .setForce(true)  // 强制点击（绕过可操作性检查）
                    .setTimeout(15000) // 显式等待元素出现
            );
            Thread.sleep(3000);
            Locator imgLocator = page.locator(".lumaDom.fillImg img.pointer");

            // 触发点击（自动等待元素可操作）
            imgLocator.click(new Locator.ClickOptions()
                    .setForce(true)  // 强制点击（绕过可操作性检查）
                    .setTimeout(15000) // 显式等待元素出现
            );
            Thread.sleep(9000);
            Page newPage = page;
            Locator img2Locator = page.locator(".popBox.relative img.pointer");
            if(img2Locator.count() > 0){
                Page popup = page.waitForPopup(() -> {
                    page.locator(".popBox.relative img.pointer").click();
                });
                newPage = popup;
            }
//            System.out.println(newPage.innerHTML("html"));
            Thread.sleep(5000);
            gptPageOprator(newPage, "/Users/hzwanggaoping/Documents/截图/7465a3e5-a65d-4067-a9e5-b6debeee64b3.jpeg");

            // 停止录制并保存轨迹文件
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("/Users/hzwanggaoping/Desktop/ailearn/screenshot/trace.zip"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gptPageOprator(Page page, String picPath) throws Exception {
        clickElement(page, "svg.pointer-events-none.h-5.w-5");
        /*// 步骤1：获取 SVG 元素的边界框
        Locator svgLocator = page.locator("svg.pointer-events-none.h-5.w-5");
        BoundingBox clipRect = svgLocator.boundingBox(); // 直接获取边界框
        System.out.println("SVG Bounding Box: " + clipRect);

        // 步骤2：获取 HTML 元素的边界框（通常为页面全区域）
        Locator htmlLocator = page.locator("html");
        BoundingBox clipRect1 = htmlLocator.boundingBox();
        System.out.println("HTML Bounding Box: " + clipRect1);

        // 步骤3：计算偏移量（x + 60%宽度，y + 60%高度）
        double targetX = clipRect.x + clipRect.width * 0.6;
        double targetY = clipRect.y + clipRect.height * 0.6;
        System.out.println("Target Click Position: (" + targetX + ", " + targetY + ")");

        // 步骤4：使用 Locator.race 组合定位策略并点击偏移位置
        Locator hl = page.locator("html");
//                .or(page.locator("//html"))
//                .or(page.locator("shadow=#shadow-host >>> html"));
        hl.click(new Locator.ClickOptions()
                        .setPosition(targetX, targetY)
                        .setForce(true)
                );
        Thread.sleep(5000L);*/
        Locator i = page.locator("#radix-\\:rk\\: > div > div:nth-of-type(1) p.text-\\[13px\\]");
        i.click(new Locator.ClickOptions()
                .setPosition(42, 0.5)
                .setForce(true)
        );
        Thread.sleep(5000L);
        //文件上传
        // 定位文件输入元素
        Locator fileInput = page.locator("input[type='file'][multiple]");

        // 上传单个文件
        fileInput.setInputFiles(Paths.get(picPath));
        Thread.sleep(15000L);
        //文字prompt
        // 聚焦输入框并输入内容
        Locator editor = page.locator(".ProseMirror");
        editor.click();
        page.keyboard().type("转成吉卜力风格");
        //点击转
        clickElement(page, ".icon-2xl");
        Thread.sleep(TimeUnit.MINUTES.toMillis(5));

        //下载图片
        if(page.locator("img[alt='已生成图片']").count()>0){
            String url = page.locator("img[alt='已生成图片']").nth(0).getAttribute("src");
            // 创建File对象
            File file = new File(picPath);
            // 获取文件名（包含文件类型）
            String fileName = file.getName();
            RedImageDownloader.downloadImage(url, "/Users/hzwanggaoping/picred/d/", fileName);
        }else if(page.locator("div.flex.max-w-full.flex-col.flex-grow img").count() > 1){
            String url = page.locator("div.flex.max-w-full.flex-col.flex-grow img").nth(1).getAttribute("src");
            // 创建File对象
            File file = new File(picPath);
            // 获取文件名（包含文件类型）
            String fileName = file.getName();
            RedImageDownloader.downloadImage(url, "/Users/hzwanggaoping/picred/d/", fileName);
        }else {
            System.out.println(picPath + "获取下载地址失败，请重新尝试");
        }

    }

    public static void clickElement(Page page, String selector){
        Locator svgLocator = page.locator(selector);
        BoundingBox clipRect = svgLocator.boundingBox(); // 直接获取边界框
        System.out.println("SVG Bounding Box: " + clipRect);

        // 步骤2：获取 HTML 元素的边界框（通常为页面全区域）
        Locator htmlLocator = page.locator("html");
        BoundingBox clipRect1 = htmlLocator.boundingBox();
        System.out.println("HTML Bounding Box: " + clipRect1);

        // 步骤3：计算偏移量（x + 60%宽度，y + 60%高度）
        double targetX = clipRect.x + clipRect.width * 0.6;
        double targetY = clipRect.y + clipRect.height * 0.6;
        System.out.println("Target Click Position: (" + targetX + ", " + targetY + ")");

        // 步骤4：使用 Locator.race 组合定位策略并点击偏移位置
        Locator hl = page.locator("html");
//                .or(page.locator("//html"))
//                .or(page.locator("shadow=#shadow-host >>> html"));
        hl.click(new Locator.ClickOptions()
                .setPosition(targetX, targetY)
                .setForce(true)
        );
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveCookies(BrowserContext context, Iterable<Cookie> cookies) {
        context.storageState(new BrowserContext.StorageStateOptions().setPath(COOKIE_PATH));
        System.out.println("Cookies 已保存至: " + COOKIE_PATH.toAbsolutePath());
    }

}