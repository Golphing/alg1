package netease.easy;

import java.util.Scanner;

/**
 * 假设你正在爬楼梯。需要 n阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 题解：
 *      f（n） = f(n-1) + f(n-2);
 *
 * 易错：
 *
 * API：
 *
 *
 *
 */
public class 爬阶梯问题 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int a = in.nextInt();
            int[] dp = new int[a+1];
            dp[1] = 1;
            System.out.println(getCount(a));
        }
    }

    public static int getCount(int i){
        if(i<=0){
            return 0;
        }
        if(i==1){
            return 1;
        }
        if(i==2){
            return 2;
        }
        int ret = getCount(i-1) + getCount(i-2);
        return ret;
    }
}
