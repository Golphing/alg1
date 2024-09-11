package netease.hard;

import java.util.Scanner;

/**
 * 题目
     *      给你一个字符串 s 。请返回 s 中最长的 超赞子字符串 的长度。
     * 「超赞子字符串」需满足满足下述两个条件：
     * 该字符串是 s 的一个非空子字符串
     * 进行任意次数的字符交换后，该字符串可以变成一个回文字符串
     * 1 <= s.length <= 10^5
     * s 仅由数字组成
     *
     * 输入格式:
     * 输入一行只包含数字的字符串s
     *
     * 输出格式:
     * 输出s中最长的 超赞子字符串 的长度
 * 思路
 *      暴力破解，判断是否是回文字符的方式是通过 数组int[10],代表0-9十个数字。 然后字符串的字符-'0'作为数组下标，得到一个就加1.最后看 int数组里面的值%2取值加起来，只允许一个奇数
 * 易错
 *       for (int i = 0; i < str.length(); i++) {
 *             for(int j=i;j<str.length()+1;j++){
 *       第二层循环需要+1,因为substring区间是左闭右开的
 *
 *       match里面，要注意==0的过滤掉
 * API
 */
public class 最长超赞子字符串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        int ans = 1;
        for (int i = 0; i < str.length(); i++) {
            for(int j=i;j<str.length()+1;j++){
                String a = str.substring(i, j);
                if(match(a)){
                    ans = Math.max(ans, a.length());
                }
            }
        }
        System.out.println(ans);
    }
    private static boolean match(String str) {
        if(str == null || str.length()<=1){
            return true;
        }
        int[] c = new int[10];
        for(int i=0;i<str.length();i++){
            char cha = str.charAt(i);
            c[cha-'0']++;
        }
        boolean ret = false;
        for(int c1 : c){
            if(c1 % 2 != 0 && ret){
                return false;
            }else if(c1 %2 !=0){
                ret = true;
            }
        }
        return true;
    }
}
