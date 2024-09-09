package netease.hard;

import java.util.Scanner;

/**
 * 思路：
 *      先找出两个数字所有的与的结果，存在一个数组里面，int[1<<16],值直接+1，代表有多少种组合能够得到这个值
 *      再让一个数字与所有的1<<16按位与，如果结果为0，则将count加起来，就是最终的结果
 *
 * 记录易错点
 *      1<<16
 * 记录不记得的API
 *
 *
 */
public class 按位与为零的三元组 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(",");

            System.out.println(countTriplets(trans(a)));
        }
    }
    public static int[] trans(String[] a){
        int[] r = new int[a.length];
        for(int i=0;i<a.length;i++){
            r[i] = Integer.valueOf(a[i]);
        }
        return r;
    }

    public static int countTriplets(int[] nums){
        int[] a =  new int[1<<16];
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums.length;j++){
                int v = nums[i] & nums[j];
                a[v]++;
            }
        }
        int ret =0;
        for(int i=0;i<nums.length;i++){
            for(int j=0;j< 1<<16;j++){
                if((nums[i] & j) == 0){
                    ret += a[j];
                }
            }
        }
        return ret;
    }
}
