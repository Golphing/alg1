package netease.medium;

import java.util.*;

/**
 *
 * 给你三个正整数 a、b 和 c。
 *
 * 你可以对 a 和 b 的二进制表示进行位翻转操作，返回能够使按位或运算   a OR b == c 成立的最小翻转次数。
 *
 * 「位翻转操作」是指将一个数的二进制表示任何单个位上的 1 变成 0 或者 0 变成 1 。
 *
 *
 * 思路：
 *      按位看，如果c的值是0，那么ab的值是1就需要加1.如果是1，那么ab的值任意一个是1即可
 *
 * 易忘API：
 *  a >> 1
 *  |
 *  &
 *
 *
 */
public class 或运算的最小翻转次数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] arr = Arrays.stream(in.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int a = arr[0], b = arr[1], c = arr[2];
        int ans = 0;
        for(int i=0;i<32;i++){
            int ia = (a >> i) & 1;
            int ib = (b >> i) & 1;
            int ic = (c >> i) & 1;
            if(ic == 0){
                if(ia == 1){
                    ans++;
                }
                if(ib == 1){
                    ans++;
                }
            }
            if(ic == 1){
                if((ia | ib) != 1){
                    // System.out.println("i : " + i + "ia, ib, ic:" + ia + ib + ic);
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }
}
