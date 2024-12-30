package netease.medium;

import java.util.Scanner;

/**
 *
 * 题目：
 *      给你一个整数数组 nums 和一个整数 k ，编写一个函数来判断该数组是否含有同时满足下述条件的连续子数组：
 *
 * 子数组大小 至少为 2 ，且
 *
 * 子数组元素总和为 k 的倍数。
 *
 * 如果存在，返回 1 ；否则，返回 0 。
 *
 * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终视为 k 的一个倍数。
 *
 *
 * 暴力破解
 *
 *
 *
 */
public class 连续的子数组和 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(",");
            int[] arr = new int[a.length];
            for(int i=0;i<a.length;i++){
                arr[i] = Integer.parseInt(a[i]);
            }
            int k = in.nextInt();

            System.out.println(exists(arr, k));
        }
    }

    public static int exists(int[] arr, int k){
        for(int i=0;i<arr.length;i++){
            int sum = 0;
            for(int j=i;j<arr.length;j++){
                sum += arr[j];
                if(sum % k == 0 && (j-i )>=1){
                    return 1;
                }
            }
        }
        return 0;
    }
}
