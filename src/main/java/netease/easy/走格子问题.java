package netease.easy;

import java.util.Scanner;

/**
 *一个机器人位于一个m*n网格的左上角 （起始点在下图中标记为 “Start” ）
 *
 * 机器人每次只能向下或者向右移动一步（每个格子只能走一次）
 *
 * 机器人试图达到网格的右下角（在下图中标记为 “Finish” ）
 *
 *思路：
 *                      dp[i][j] = dp[i-1][j] + dp[i][j-1];
 *                      注意：i==0 或者 j==0 的时候，dp[i][j] = 1;
 *
 *
 */
public class 走格子问题 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(" ");
            int row = Integer.parseInt(a[0]);
            int col = Integer.parseInt(a[1]);


            System.out.println(getWays(row, col));
        }

    }

    public static int getWays(int row, int col){
        int[][] dp = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(i==0){
                    dp[i][j] = 1;
                    continue;
                }else if(j == 0){
                    dp[i][j] = 1;
                    continue;
                }
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[row-1][col-1];
    }
}
