package other.selenium;

import com.microsoft.playwright.*;
import other.common.CommonUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public class batchUploadPlayright {
    private static final Path COOKIE_PATH = Paths.get("cookie_xmp.json");

    public static void main(String[] args) throws Exception {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

            BrowserContext context;
            if (COOKIE_PATH.toFile().exists()) {
                context = browser.newContext(
                        new Browser.NewContextOptions()
                                .setStorageStatePath(COOKIE_PATH)
                                .setViewportSize(null));
            } else {
                context = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(null));
            }

            Page page = context.newPage();
            page.navigate("https://studio.ximalaya.com");

            // 保持登录等待（原代码的20秒等待）
//            page.waitForTimeout(20000);


            String intro = "关注后私信，获取资源ddd";

            // 保存cookie（在操作完成后）
            context.storageState(new BrowserContext.StorageStateOptions().setPath(COOKIE_PATH));

            // 批量上传逻辑
            try {
                // 歌曲
//                CommonUtil.batchUploadp(page, "","/Users/hzwanggaoping/Documents/古诗词-一年级上/", "8090", intro, 50);

                // 童话
                CommonUtil.batchUploadp(page, "", "/Users/Maxuemin/Desktop/englishout/上传/爸爸妈妈讲睡前十分钟故事77首333M/妈妈讲的睡前十分钟故事1", "格林童话", intro, 3);
                // 英语1
                CommonUtil.batchUploadp(page, "",  "/Users/Maxuemin/Desktop/englishout/上传/版本1-2的语音", "开口说英文不再是难题", intro, 1);

                // 英语2
//                CommonUtil.batchUploadp(page, "","/Users/Maxuemin/Desktop/englishout/上传/版本2语音", "高频口语生活口语3000句", intro, 5);

            } finally {
                context.close();
                browser.close();
            }
        }
    }
}
