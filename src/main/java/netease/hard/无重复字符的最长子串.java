package netease.hard;

import java.util.*;

/**
 * 题目
 *      给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * 思路
 *      暴力破解，直接从每个字符开始往后匹配，如果有重复的就记录max
 * 易错点：
 *      如果能一直匹配到最后一个，需要注意max = Math.max(max, j+1);
 *
 * 不记得的API
 */
public class 无重复字符的最长子串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            System.out.println(getLong(a));
        }
    }

    public static int getLong(String in){
        if(in == null || in.isEmpty()){
            return 0;
        }
        int max = 0;
        for(int i=0;i<in.length();i++){
            Set<Character> exits = new HashSet<>();
            for(int j=0;i+j<in.length();j++){
                //一直往后找，直到找到一个重复的，记录max
                char c = in.charAt(i+j);
                if(exits.contains(c)){
                    max = Math.max(max, j);
                    break;
                }else {
                    exits.add(c);
                    if((i+j)==(in.length()-1)){
                        max = Math.max(max, j+1);
                    }
                }
            }
        }
        return max;
    }
}
