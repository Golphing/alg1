package other.gpt.ghibli;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 从/Users/hzwanggaoping/picred/o/这个里面看看有哪些图片
 * 判断上面的图片在/Users/hzwanggaoping/picred/d/里有没有
 * 如果没有调用转换的程序去转换
 */
public class BatchGPTTrans {
    private static final Path COOKIE_PATH = Paths.get("cookies_gpt.json");

    public static void main(String[] args) throws InterruptedException {
//        Thread.sleep(TimeUnit.HOURS.toMillis(2));
        try (Playwright playwright = Playwright.create()) {
            /*Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(java.util.Arrays.asList(
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
            }*/

            // 启动轨迹录制（核心配置）
//            context.tracing().start(new Tracing.StartOptions()
//                    .setScreenshots(true)   // 启用截图
//                    .setSnapshots(true)     // 启用 DOM 快照
//                    .setSources(true)       // 记录操作源码（需配置环境变量）
//            );

            //开始一个转换任务
            // 源文件夹路径
            String sourceDirPath = "~/o/";
            // 目标文件夹路径
            String targetDirPath = "~/d/";
            // 创建源文件夹和目标文件夹的File对象
            File sourceDir = new File(sourceDirPath);
            File targetDir = new File(targetDirPath);
            while (true){
                // 检查源文件夹是否存在并且是一个目录
                if (sourceDir.exists() && sourceDir.isDirectory()) {
                    // 遍历源文件夹中的所有文件
                    File[] files = sourceDir.listFiles();

                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile()) { // 判断是否是文件
                                // 构建目标文件路径
                                File targetFile = new File(targetDir, file.getName());
                                // 检查文件在目标文件夹中是否存在
                                if (targetFile.exists()) {
//                                System.out.println("文件 " + file.getName() + " 存在于目标文件夹中。");
                                } else {
                                    System.out.println("文件 " + file.getName() + " 不存在于目标文件夹中。开始处理");
                                    startWork(playwright, file.getAbsolutePath());
                                }
                            }
                        }
                        Thread.sleep(TimeUnit.MINUTES.toMillis(10));
                    } else {
                        System.out.println("源文件夹中没有文件。");
                    }
                } else {
                    System.out.println("源文件夹不存在或不是一个目录。");
                }
            }
            // 停止录制并保存轨迹文件
//            context.tracing().stop(new Tracing.StopOptions()
//                    .setPath(Paths.get("/Users/hzwanggaoping/Desktop/ailearn/screenshot/trace.zip"))
//            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startWork(Playwright playwright, String filePath) throws InterruptedException {
//        Thread.sleep(TimeUnit.MINUTES.toMillis(5));
        boolean useChrom = true;
        Browser browser = null;
        BrowserContext context = null;
        if(useChrom){
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome"). // 关键配置
                    setHeadless(false).setArgs(java.util.Arrays.asList(
                    "--start-maximized",        // 窗口最大化（[[13]](#__13)）
                    "--no-sandbox"             // 解决 Linux 权限问题（[[13]](#__13)）
            )));

            if (COOKIE_PATH.toFile().exists()) {
                context = browser.newContext(
                        new Browser.NewContextOptions()
                                .setStorageStatePath(COOKIE_PATH).setViewportSize(null));
            }else {
                context = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(null));
            }
        }else {
            // 创建配置 Map
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("marionette.enabled", false);
            prefs.put("remote.enabled", false);
            prefs.put("privacy.resistFingerprinting", false);

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setFirefoxUserPrefs(Collections.unmodifiableMap(prefs));

            browser = playwright.firefox().launch(options);
            context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0")
                            .setViewportSize(1366, 768)
                            .setStorageStatePath(COOKIE_PATH)
            );

            // 注入反检测脚本
            context.addInitScript(
                    "Object.defineProperty(navigator, 'webdriver', { get: () => undefined });"
                            + "window.navigator.plugins = ["
                            + "  { name: 'PDF Viewer', filename: 'internal-pdf-viewer' },"
                            + "  { name: 'Chrome PDF Viewer', filename: 'mhjfbmdgcfjbbpaeojofohoefgiehjai' }"
                            + "];"
            );
        }


        Page newPage = null;
        try {
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();
            final Page page = context.newPage();
            page.setViewportSize(width, height);
            // 打开目标网站
            page.navigate("https://nf.video/");
//            page.evaluate("document.documentElement.requestFullscreen()");
            Thread.sleep(5000);
            clickElement(page, "text='AI镜像专区'");
            Thread.sleep(1000);

            // 检查是否已登录
            if (!COOKIE_PATH.toFile().exists()) {
                System.out.println("请手动登录，等待120秒...");
                page.waitForTimeout(40000); // 等待2分钟用于登录
                saveCookies(context, context.cookies());
            }

            // 等待页面加载
//            page.waitForLoadState(LoadState.NETWORKIDLE);
            Thread.sleep(5000);
            Locator imgLocator1 = page.locator("div.betweenFlex > button");
            // 触发点击（自动等待元素可操作）
            if(imgLocator1.count() > 0){
                imgLocator1.click(new Locator.ClickOptions()
                        .setForce(true)  // 强制点击（绕过可操作性检查）
                        .setTimeout(5000) // 显式等待元素出现
                );
                Thread.sleep(1000);
            }
            while(true){
                Locator nxt = page.locator("xpath=//span[text()='下一个']");
                if(nxt.count() > 0){
                    clickElement(page, "xpath=//span[text()='下一个']");
                    Thread.sleep(1000);
                }else {
                    break;
                }
            }
            Locator iknow = page.locator("span:has-text('知道了')");
            if(iknow.count() > 0){
                clickElement(page, "span:has-text('知道了')");
                Thread.sleep(1000);
            }

            Locator imgLocator = page.locator(".lumaDom.fillImg img.pointer");
            if(imgLocator.count() > 0){
                // 触发点击（自动等待元素可操作）
                imgLocator.click(new Locator.ClickOptions()
                        .setForce(true)  // 强制点击（绕过可操作性检查）
                        .setTimeout(15000) // 显式等待元素出现
                );
                Thread.sleep(4000);
            }
            newPage = page;
            Locator img2Locator = page.locator(".popBox.relative img.pointer");
            if(img2Locator.count() > 0){
                Page popup = page.waitForPopup(() -> {
//                    page.locator(".popBox.relative img.pointer").click();
                    clickElement(page, ".popBox.relative img.pointer");
                });
                System.out.println("页面跳转");
                newPage = popup;
//                newPage.navigate("https://gpt.bestaistore.com/?model=gpt-4o-image-vip");
            }
//            System.out.println(newPage.innerHTML("html"));
            Thread.sleep(15000);
//            page.close();
            gptPageOprator(newPage, filePath);
//            newPage.close();

        } catch (Exception e) {
            e.printStackTrace();
//            Thread.sleep(TimeUnit.MINUTES.toMillis(3));
        }
        Thread.sleep(TimeUnit.SECONDS.toMillis(new Random().nextInt(300)));
        browser.close();

    }

    public static void gptPageOprator(Page page, String picPath) throws Exception {
        boolean useVip = false;
        boolean useImg = true;
        if(useVip){
            //选择模型
            clickElement(page, "main .icon-md.text-token-text-tertiary");
            Thread.sleep(1000);
            //其他模型,鼠标移动到这个位置
            Locator otherModel = page.locator("div.flex.grow.justify-between.gap-2.overflow-hidden:has-text('其他模型')");
            Thread.sleep(1000);
            page.mouse().move(     otherModel.boundingBox().x + otherModel.boundingBox().width / 2,     otherModel.boundingBox().y + otherModel.boundingBox().height / 2 );
            Thread.sleep(1000);
            //鼠标点击   gpt-4o-image-vip
            clickElement(page, "div[class*='flex'][class*='items-center']:has(div:text('gpt-4o-image-vip'))");
            Thread.sleep(15000);
        }else if(useImg){
            //选择模型
            clickElement(page, "main .icon-md.text-token-text-tertiary");
            Thread.sleep(1000);
            //其他模型,鼠标移动到这个位置
            Locator otherModel = page.locator("div.flex.grow.justify-between.gap-2.overflow-hidden:has-text('其他模型')");
            Thread.sleep(1000);
            page.mouse().move(     otherModel.boundingBox().x + otherModel.boundingBox().width / 2,     otherModel.boundingBox().y + otherModel.boundingBox().height / 2 );
            Thread.sleep(1000);
            //鼠标点击   gpt-4o-image-vip
            clickElement(page, "div[class*='flex'][class*='items-center']:has(div:text('gpt-image'))");
            Thread.sleep(15000);
        }
        if(!useImg){
            //点击三个点
            clickElement(page, "svg.pointer-events-none.h-5.w-5");
            Locator i = page.locator("#radix-\\:rk\\: > div > div:nth-of-type(1) p.text-\\[13px\\]");
            i.click(new Locator.ClickOptions()
                    .setPosition(42, 0.5)
                    .setForce(true)
            );
            Thread.sleep(5000L);
        }
        //文件上传
        // 定位文件输入元素
        Locator fileInput = page.locator("input[type='file'][multiple]");

        // 上传单个文件
        fileInput.setInputFiles(Paths.get(picPath));
        Thread.sleep(35000L);
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
            System.out.println("图片链接:" + url);
            // 获取文件名（包含文件类型）
            String fileName = file.getName();
            RedImageDownloader.downloadImage(url, "/Users/hzwanggaoping/picred/d/", fileName);
            for(int j=0; j < page.locator("img[alt='已生成图片']").count();j++){
                System.out.println("普通链接分别为：" + page.locator("img[alt='已生成图片']").nth(j).getAttribute("src"));
            }
        }else if(page.locator("span:has-text('Created with DALL·E')").count() > 0){
            //降智的
            String url = page.locator("div.relative.h-full > img").nth(0).getAttribute("src");
            System.out.println("降智图片链接:" + url);
            // 创建File对象
            File file = new File(picPath);
            // 获取文件名（包含文件类型）
            String fileName = file.getName();
            RedImageDownloader.downloadImage(url, "/Users/hzwanggaoping/picred/d/", fileName);
            for(int j=0; j < page.locator("span:has-text('Created with DALL·E')").count();j++){
                System.out.println("降智的链接分别为：" + page.locator("div.relative.h-full > img").nth(j).getAttribute("src"));
            }
        }else if(page.locator("p>img").count() > 0){
            //vip模型的
            String url = page.locator("p>img").nth(0).getAttribute("src");
            if(page.locator("p img").count() > 1){
                url = page.locator("p>img").nth(1).getAttribute("src");
            }
            System.out.println("vip图片链接:" + url);
            // 创建File对象
            File file = new File(picPath);
            // 获取文件名（包含文件类型）
            String fileName = file.getName();
            Files.write(Paths.get("/Users/hzwanggaoping/picred/d/"+fileName), page.context().request().get(page.locator("p>img").nth(0).getAttribute("src")).body());
//            RedImageDownloader.downloadImage(url, "/Users/hzwanggaoping/picred/d/", fileName);
            for(int j=0; j < page.locator("p img").count();j++){
                System.out.println("vip的链接分别为：" + page.locator("p img").nth(j).getAttribute("src"));
            }
        }else {
            // 截取全屏并保存（路径需存在）
            int v = new Random().nextInt(100000);
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("dowloads/error-"+v+".png"))
                    .setFullPage(true) // 截取完整滚动页面（[[1]](#__1) [[8]](#__8)）
            );
            System.out.println(picPath + "获取下载地址失败，请重新尝试");
            for(int j=0; j < page.locator("div img").count();j++){
                System.out.println("没找到的img链接分别为：" + page.locator("div img").nth(j).getAttribute("src"));
            }
//            // 定位图片元素
//            ElementHandle image = page.querySelector("p>img");
//            image.click(new ElementHandle.ClickOptions().setButton(MouseButton.RIGHT));
//            page.keyboard().press("ArrowDown");
//            page.keyboard().press("ArrowDown");
//            page.keyboard().press("Enter");
//
//            // 等待下载完成
//            Download download = page.waitForDownload(() -> {});
//            download.saveAs(Paths.get("downloads/saved_image.png"));
        }

    }

    public static void clickElement(Page page, String selector){
        Locator svgLocator = page.locator(selector).nth(0);
        BoundingBox clipRect = svgLocator.boundingBox(); // 直接获取边界框
//        System.out.println("SVG Bounding Box: " + clipRect);

        // 步骤2：获取 HTML 元素的边界框（通常为页面全区域）
        Locator htmlLocator = page.locator("html");
        BoundingBox clipRect1 = htmlLocator.boundingBox();
        if(clipRect == null){
            System.out.println("error error, element not found : " + selector);
            return;
        }
//        System.out.println("HTML Bounding Box: " + clipRect1);

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
