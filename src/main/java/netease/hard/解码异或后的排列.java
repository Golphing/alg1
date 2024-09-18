package netease.hard;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 给你一个整数数组 perm ，它是前 n 个正整数（1,2,3,4,5,…,n-1,n 共n个正整数）的排列，且 n 是个奇数 。
 * 它被加密成另一个长度为 n-1 的整数数组 encoded ，满足 encoded[i] = perm[i] XOR perm[i+1]。比方说，如果 perm=[1,3,2] ，那么 encoded=[2,1]。
 * 给你 encoded 数组，请你返回原始数组 perm 。题目保证答案存在且唯一。
 *
 *
 * 思路：
 *      因为是奇数个，所以我们能够基于encoded获取到除第一个数据所有数据异或的结果，然后能够得到其他所有的数据的异或结果。两个再异或就是第一个元素的值，
 *      然后基于第一个数据的值能够得到其他所有的值。
 * 记录易错点
 *      异或是 ^
 *      0和任何数异或都是自己
 *      超时问题：最后结果的输出要stringbuilder来输出
 * 记录不记得的API
 *      StringBuilder r = new StringBuilder();
 *      r.append();
 *
 */
public class 解码异或后的排列 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(",");
            int[] b = new int[a.length];
            for(int i=0;i<a.length;i++){
                b[i] = Integer.valueOf(a[i]);
            }
            //获取原始数组
            int[] ret = getPerm(b);
            StringBuilder r = new StringBuilder();
            for(int i=0;i<ret.length;i++){
                if(i==ret.length-1){
                    r.append(ret[i]);
                }else {
                    r.append(ret[i] + ",");
                }
            }
            System.out.print(r);

        }
    }

    public static int[] getPerm(int[] encoded){
        int a = encoded.length;
        int totalXor = 0;
        for(int i=1;i<=a+1;i++){
            totalXor ^= i;
        }
        int oddXor = 0;
        for(int i=1;i<encoded.length;i=i+2){
            oddXor ^= encoded[i];
        }
        int first = oddXor ^ totalXor;
        int[] ret = new int[a+1];
        ret[0] = first;
        for(int i=1;i<a+1;i++){
            ret[i] = ret[i-1] ^ encoded[i-1];
        }
        return ret;
    }
}
