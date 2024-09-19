package netease.easy;

import java.util.*;

/**
 * 给你一个字符串 s ，请你去除字符串中重复的字符，使得每个字符只出现一次。需保证返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
 *
 * 输入格式:
 * 输入一个字符串，字符串的长度小于1000。字符内容是ASCII码，ASCII码定义了128个字符，包括控制字符（例如换行符、制表符、退格等）和可显示的字符（包括数字、字母、标点符号和一些特殊字符）。
 *
 * 输出格式:
 * 输出处理后的字符串
 *
 * 思路：
 *      通过数组去重，通过数组计算是否后面还有剩余
 *          如果后面还有就把前面的都弹出去，但是后面没有的就不能弹了。
 *
 * 易错;
 *      if(exists[c]){
 *                 a[c]--;//这里也得减1
 *                 continue;
 *             }
 *
 *
 * 不记得的API：
 *      StringBuilder sb = new StringBuilder();
 *         while(!stack.isEmpty()){
 *             sb.append(stack.pop());
 *         }
 *         String ret = sb.reverse().toString();
 *
 *
 *
 */
public class 去除重复字母 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            System.out.println(getDuplicate(a));
        }
    }

    public static String getDuplicate(String in){
        int[] a = new int[1000];
        for(char c : in.toCharArray()){
            a[c]++;
        }
        boolean[] exists = new boolean[500];
        LinkedList<Character> stack = new LinkedList<>();
        for(char c : in.toCharArray()){
            if(exists[c]){
                a[c]--;
                continue;
            }else {
                while(!stack.isEmpty() && c < stack.peek()){
                    char p = stack.peek();
                    if(a[p] > 0){
                        stack.pop();
                        exists[p]=false;
                    }else {
                        break;
                    }
                }
                a[c]--;
                stack.push(c);
                exists[c]=true;
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }
        String ret = sb.reverse().toString();
        return ret;
    }
}
