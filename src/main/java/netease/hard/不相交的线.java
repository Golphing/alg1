package netease.hard;

import java.util.Scanner;

/**
 * 题目：
     *  在两条独立的水平线上按给定的顺序写下 nums1 和 nums2 中的整数。
     * 现在，可以绘制一些连接两个数字 nums1[i] 和 nums2[j] 的直线，这些直线需要同时满足满足：
     * nums1[i] == nums2[j]
     * 且绘制的直线不与任何其他连线（非水平线）相交。
     * 请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。
     * 以这种方法绘制线条，并返回可以绘制的最大连线数。
     * 1 <= nums1.length, nums2.length <= 500
     * 1 <= nums1[i], nums2[j] <= 2000
     * 每组输入为两行，表示nums1和nums2两个数组。每行有n+1个数字，数字间用空格分开，第一个数字表示数组个数n，后面跟n个数字；如2 2 3，表示数组有2个元素，元素值为2和3
 *
 * 思路：
 *      二维动态规划， 其实就是找最长公共子序列
 *      dp[i][j],表面第一个里面的前i个和第二个里面的前j个的最长公共子序列
 *      状态转移方程：
 *          s1[i-1] == s2[j-1],则 dp[i][j] = dp[i-1][j-1] + 1,
 *          s1[i-1] != s2[j-1],则 dp[i][j] = max(dp[i-1][j], dp[i][j-1]);
 * 易错点：
 *
 * 不记得的API
 *
 *
 */
public class 不相交的线 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String one = in.nextLine();
            String b = in.nextLine();
            int[] s1 = getArr(one);
            int[] s2 = getArr(b);
            System.out.println(getMax(s1, s2));
        }
    }

    public static int  getMax(int[] s1, int[] s2){
        int[][] dp = new int[s1.length+1][s2.length+1];
        for(int i=1;i<=s1.length;i++){
            for(int j=1;j<=s2.length;j++){
                if(s1[i-1] == s2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[s1.length][s2.length];
    }

    public static int[] getArr(String in){//3 1 4 2
        String[] a = in.split(" ");
        if(a.length <= 1){
            return new int[0];
        }
        int[] ret = new int[Integer.parseInt(a[0])];
        for(int i=0;i<a.length-1;i++){
            ret[i] = Integer.parseInt(a[i+1]);
        }
        return ret;
    }

}
