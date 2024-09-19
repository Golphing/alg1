package netease.easy;

import java.util.Scanner;

/**
 *给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 *
 * 思路：
 *      暴力破解，列举所有情况即可
 *
 *
 */
public class 区间和的个数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(" ");
            int[] arr = new int[a.length];
            for(int i=0;i<a.length;i++){
                arr[i]=Integer.parseInt(a[i]);
            }
            int lower = Integer.parseInt(in.nextLine());
            int upper = Integer.parseInt(in.nextLine());
            System.out.println(getCount(arr, lower, upper));
        }
    }

    public static int getCount(int[] arr, int low, int upper){
        int count = 0;
        for(int i=0;i<arr.length;i++){
            int sum = 0;
            for(int j=0;i+j<arr.length;j++){
                sum += arr[i+j];
                if(sum >= low && sum <= upper){
                    count++;
                }
            }
        }
        return count;
    }
}
