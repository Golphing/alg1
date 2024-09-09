package netease.easy;

import netease.util.JsonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class 爬取百度歌曲歌词 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入歌曲名：");
        String partialLyrics = scanner.nextLine();
        System.out.print("请输入必须包含的歌词：");
        String mustContainLyric = scanner.nextLine();
        System.out.print("请输入必须加星星的歌词：");
        String destLyric = scanner.nextLine();

        try {
            String searchUrl = "https://www.baidu.com/s?wd=" + partialLyrics + " 歌词";
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

           String content = doc.toString();
            System.out.println(content);
           if(content.contains(mustContainLyric)){
               String ret = 爬取QQ音乐歌词.restoreSentence(content, destLyric);
               if(ret != null && !ret.contains("*")){
                   System.out.println("还原成功 " + ret);
               }else {
                   System.out.println("还原失败: " + destLyric);
               }
           }else{
               System.out.println("还原失败: " + destLyric);
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
