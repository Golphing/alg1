package netease.easy;

import java.util.Scanner;

/**
 *
 *如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 * 字母和数字都属于字母数字字符。
 * 给你一个字符串 s，如果它是 回文串 ，返回 1；否则，返回 0。
 *
 * 输入格式:
 * 一行包含一个字符串，长度大于0、同时小于等于2000
 *
 * 输出格式:
 * 输入是一行，如果这个字符串是回文，返回 1，否则返回 0。
 *
 *
 * 思路：
 *      先整体的都转成小写
 *      然后通过 字符之间的距离，把非字母的都过滤掉
 *
 * 易忘API：
 *
 *             a = a.toLowerCase();
 *
 */
public class 检测回文字符串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            a = a.toLowerCase();
            System.out.println(isRec(a));
        }
    }

    public static String isRec(String in){
        StringBuilder sb = new StringBuilder();
        for(char c : in.toCharArray()){
            if(c - 'a' >=0 && c-'a' <=25){
                sb.append(c);
            }
        }
        if(sb.toString().equals(sb.reverse().toString())){
            return "1";
        }
        return "0";
    }
}
