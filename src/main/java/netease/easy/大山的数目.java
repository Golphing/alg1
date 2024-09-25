package netease.easy;

import java.util.Scanner;

/**
 *Drizzle 前往山地统计大山的数目，现在收到这片区域的地图，地图中用0（平地）和1（山峰）绘制而成，请你帮忙计算其中的大山数目。
 *
 * 山总是被平地四面包围着，每一座山只能在水平或垂直方向上连接相邻的山峰而形成。一座山峰四面被平地包围，这个山峰也算一个大山。
 *
 * 另外，你可以假设地图的四面都被平地包围着。
 *
 * 输入格式:
 * 第一行输入M,N分别表示地图的行列，接下来M行每行输入N个数字表示地图。
 *
 *
 *
 *思路：
 *      访问过的记录一下，剩余的采用贪心算法。
 *
 *
 */
public class 大山的数目 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int row = in.nextInt();
            int count = in.nextInt();
            int[][] arr = new int[row][count];
            for(int i=0;i<row;i++){
                for(int j=0;j<count;j++){
                    arr[i][j] = in.nextInt();
                }
            }

            System.out.println(getMountainCount(arr));
        }
    }

    public static int getMountainCount(int[][] arr){
        int count = 0;
        int[][] visited = new int[arr.length][arr[0].length];
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j] == 1 && visited[i][j] == 0){
                    count++;
                    dfs(visited, i, j, arr);
                }
            }
        }
        return count;
    }

    public static void dfs(int[][] visited, int i, int j, int[][] arr){
        if(i<0 || j<0 || i>=visited.length || j>= visited[0].length || visited[i][j] == 1 || arr[i][j] == 0){
            return;
        }
        visited[i][j] = 1;

        dfs(visited, i+1, j, arr);
        dfs(visited, i, j+1, arr);
        dfs(visited, i-1, j, arr);
        dfs(visited, i, j-1, arr);
    }
}
