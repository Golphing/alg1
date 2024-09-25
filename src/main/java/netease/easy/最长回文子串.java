package netease.easy;

import java.util.*;

/**
 *给你一个字符串s，找到s中最长的回文子串。
 * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 *
 * 输入格式:
 * 1<=s.length<=1000
 *
 * 输出格式:
 * s中最长的回文子串
 *
 *
 *
 */
public class 最长回文子串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            System.out.println(getMax(a));
        }
    }

    public static String getMax(String in){
        int max=0;
        String maxL = "";
        for(int i=0;i<in.length();i++){
            for(int j=i;j<in.length()+1;j++){
                String sub = in.substring(i, j);
                if(isMatch(sub)){
                    if(sub.length() > max){
                        max = sub.length();
                        maxL = sub;
                    }
                }
            }
        }
        return maxL;
    }

    public static boolean isMatch(String v){
        StringBuilder sb = new StringBuilder(v);
        if(sb.reverse().toString().equals(v)){
            return true;
        }
        return false;
    }
}
