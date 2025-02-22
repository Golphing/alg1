package netease.easy;

import java.util.Scanner;

/**
 *
 *Drizzle 被困到一条充满数字的方块路中，假设这条路由一个非负的整数数组m组成，Drizzle 最开始的位置在下标 start 处，当他位于下标i位置时可以向前或者向后跳跃m[i]步数，已知元素值为0处的位置是出口，且只能通过出口出去，不可能数组越界，也不能折返，如果跳跃的步数超出数组范围，则也定义为失败，请你通过编程计算出Drizzle能否逃出这里。
 *
 * 输入格式:
 * 第一行输入数组m的长度n ；第二行输入数组元素，空格分割开 第三行输入起始下标 start
 *
 * 输出格式:
 * True 或 False。
 *
 *
 * 思路：
 *      递归
 *
 */
public class 跳跃00 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int a = in.nextInt();
            int[] arr = new int[a];
            for(int i=0;i<a;i++){
                arr[i] = in.nextInt();
            }
            int s = in.nextInt();
            int[] visited = new int[arr.length];
            System.out.println(canExit(arr, s, visited));
        }
    }

    public static String canExit(int[] arr, int start, int[] visited){
        if(arr[start] == 0){
            return "True";
        }
        if(visited[start] == 1){
            return "False";
        }
        visited[start] = 1;

        if((start + arr[start]) < arr.length){
            //向右
            if("True".equals(canExit(arr,start + arr[start], visited))){
                return "True";
            }
        }
        if((start - arr[start]) >=0){
            //向左
            if("True".equals(canExit(arr,start - arr[start], visited))){
                return "True";
            }
        }
        return "False";
    }
}
