package netease.hard;

import java.util.*;

/**
 * 给你一个整数数组nums，找到其中最长严格递增子序列的长度。
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 *
 * 思路：
 *      动态规划dp[i]，先找第一个结尾的最长子序列，接着找第二个结尾的最长子序列(这个其实就是看前面所有的子序列 + 1的最大值，前提是他的子序列结尾的值要比i小)
 *      最后遍历一下dp[i],找出最大值即可(也可以在构建中就保存好最大值)
 * 记录易错点
 *
 * 记录不记得的API
 *       Arrays.fill(dp, 1)
 */
public class 最长递增子序列 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(" ");
            int[] b = new int[a.length];
            for(int i=0;i<a.length;i++){
                b[i] = Integer.valueOf(a[i]);
            }
            System.out.println(longestSubSeq(b));
        }
    }

    public static int longestSubSeq(int[] nums){
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int ret = 0;
        for(int i = 0;i<dp.length;i++){
            for(int j=0;j<i;j++){
                if(nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i],dp[j] + 1);
                }
            }
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
}
