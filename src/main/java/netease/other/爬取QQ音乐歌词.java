package netease.other;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class 爬取QQ音乐歌词 {

    private static final String URL = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?";
    private static final Map<String, String> headers = new HashMap<>();
    private static final Map<String, String> params = new HashMap<>();

    static {
        headers.put("origin", "https://y.qq.com");
        headers.put("referer", "");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        params.put("ct", "24");
        params.put("qqmusic_ver", "1298");
        params.put("remoteplace", "txt.yqq.lyric");
        params.put("searchid", "103347689276433275");
        params.put("aggr", "0");
        params.put("catZhida", "1");
        params.put("lossless", "0");
        params.put("sem", "1");
        params.put("t", "7");
        params.put("p", "1");
        params.put("n", "5");
        params.put("g_tk_new_20200303", "5381");
        params.put("g_tk", "5381");
        params.put("loginUin", "0");
        params.put("hostUin", "0");
        params.put("format", "json");
        params.put("inCharset", "utf8");
        params.put("outCharset", "utf-8");
        params.put("notice", "0");
        params.put("platform", "yqq.json");
        params.put("needNewCode", "0");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入歌曲名：");
        String singer = scanner.nextLine();
        System.out.print("请输入必须包含的歌词：");
        String mustContainLyric = scanner.nextLine();
        System.out.print("请输入必须加星星的歌词：");
        String destLyric = scanner.nextLine();
        params.put("w", URLEncoder.encode(singer, "UTF-8"));
        scanner.close();

        Map<String, List<String[]>> albumMap = new HashMap<>();

        for (int p = 1; p < 100; p++) {
            params.put("p", String.valueOf(p));
            String jsonResponse = getResponseFromQQMusic(URL, params, headers);
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray listLyric = jsonObject.getJSONObject("data").getJSONObject("lyric").getJSONArray("list");

            if (listLyric.length() == 0) {
                int total = albumMap.values().stream().mapToInt(List::size).sum();
                System.out.println("总共下载" + total + "首歌词！");
                break;
            }

            for (int i = 0; i < listLyric.length(); i++) {
                JSONObject lyric = listLyric.getJSONObject(i);
                String content = lyric.getString("content").replaceAll("\\\\n ", "\n").trim();
                String songname = content.split("\n")[0].trim();
                String albumname = lyric.getString("albumname").trim();

//                if (checkSong(singer, songname, albumname, albumMap)) {
                    albumMap.computeIfAbsent(albumname.isEmpty() ? "其它" : albumname, k -> new ArrayList<>())
                            .add(new String[]{songname, content});
//                    System.out.println(albumname + "\n  " + songname);
//                }
            }
        }

        List<Map.Entry<String, List<String[]>>> albumList = new ArrayList<>(albumMap.entrySet());
        albumList.sort(Comparator.comparingInt(e -> -e.getValue().size()));

//        System.out.println(JsonUtil.toJson(albumList));
        boolean find = false;
        for(Map.Entry<String, List<String[]>> album : albumList){
            if(find){
                break;
            }
            for(String[] song : album.getValue()){
                if (song[1].contains(mustContainLyric)) {
                    System.out.println("找到目标歌词： " + album.getKey() + "\n  " + song[1]);
                    String res = restoreSentence(song[1], destLyric);
                    if(res != null){
                        System.out.println("还原成功： " + res);
                        return;
                    }

                }
            }
        }
        System.out.println("还原失败： " + destLyric);
//        System.out.println(albumList.size());
//        writeToFile(singer + "_歌词.txt", albumList, true);
//        writeToFile(singer + "_歌名.txt", albumList, false);
    }

    private static String getResponseFromQQMusic(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        StringBuilder fullUrl = new StringBuilder(url);
        params.forEach((k, v) -> fullUrl.append(k).append("=").append(v).append("&"));

        HttpURLConnection connection = (HttpURLConnection) new URL(fullUrl.toString()).openConnection();
        headers.forEach(connection::setRequestProperty);

        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    private static boolean checkSong(String singer, String songname, String albumname, Map<String, List<String[]>> albumMap) {
        if (!songname.contains(singer) || !songname.contains("-")) return false;

        if (albumMap.containsKey(albumname)) {
            for (String[] song : albumMap.get(albumname)) {
                if (song[0].equals(songname)) return false;
            }
        }

        if ("周杰伦".equals(singer)) {
            if (albumname.contains("范特西PLUS") || songname.contains("周大侠")) {
                return true;
            }
        }

        String[] songRemove = {"Live", "醇享版", "纯音乐", "暂无歌词"};
        String[] albumRemove = {"Live", "演唱会", "音乐会"};

        for (String sn : songRemove) {
            if (songname.contains(sn)) return false;
        }
        for (String an : albumRemove) {
            if (albumname.contains(an)) return false;
        }
        return true;
    }


    public static String restoreSentence(String text, String encryptedSentence) {
        // 计算加密句子中非 * 的字符数
        int encryptedLength = encryptedSentence.length();
        int nonAsteriskCount = (int) encryptedSentence.chars().filter(c -> c != '*').count();

        // 遍历文本中的所有可能的子串
        for (int i = 0; i <= text.length() - encryptedLength; i++) {
            String candidate = text.substring(i, i + encryptedLength);

            // 检查候选子串是否符合加密句子的要求
            if (matches(candidate, encryptedSentence)) {
                // 返回匹配的原句子
                return candidate;
            }
        }

        // 如果没有找到匹配的句子
        return null;
    }

    private static boolean matches(String candidate, String encryptedSentence) {
        for (int i = 0; i < encryptedSentence.length(); i++) {
            char c1 = candidate.charAt(i);
            char c2 = encryptedSentence.charAt(i);
            if (c2 != '*' && c1 != c2) {
                return false;
            }
        }
        return true;
    }
    @Test
    public static void main11(String[] args) {
        String text1 = "你是我的情人\n" +
                "像玫瑰花一样的女人\n" +
                "用你那火火的嘴唇\n" +
                "让我在午夜里无尽的消魂\n" +
                "你是我的爱人\n" +
                "像百合花一样的清纯\n" +
                "用你那淡淡的体温\n" +
                "抚平我心中那多情的伤痕\n" +
                "我梦中的情人\n" +
                "忘不了甜蜜的香吻\n" +
                "每一个动情的眼神\n" +
                "都让我融化在你无边的温存\n" +
                "你是我的情人\n" +
                "像玫瑰花一样的女人\n" +
                "用你那火火的嘴唇\n" +
                "让我在午夜里无尽的消魂\n" +
                "来来来来来来 \n" +
                "来来来来来来 \n" +
                "来来来来来来";
        String encryptedSentence1 = "你是我的**";

        String result1 = restoreSentence(text1, encryptedSentence1);
        if (result1 != null) {
            System.out.println("还原后的句子1: " + result1);
        } else {
            System.out.println("没有找到匹配的句子1。");
        }
    }
}