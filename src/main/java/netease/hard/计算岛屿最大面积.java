package netease.hard;

import java.util.Scanner;

/**
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 *
 * 思路：
 *      1.调用方法计算每个点的岛屿，记录最大值
 *          如果这个点为0，则直接为0，否则返回 每个点的岛屿= 上下左右的值 + 1
 * 记录易错点
 *              in = in.replaceAll("\\[","").replaceAll("\\]","");  需要两个反斜杠
 * 记录不记得的API
 *        int[][] a = new int[3][4]; a.length代表行数，a[0].length代表列数
 *
 */
public class 计算岛屿最大面积 {

    static int max = 0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            int[][] arr = buildArray(a);
            System.out.println(getMax(arr));
        }
    }

    public static int getMax(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                int nodeMax = getNodeMax(arr, i, j);
                if(nodeMax > max){
                    max = nodeMax;
                }
            }
        }
        return max;
    }

    public static int getNodeMax(int[][] arr, int i, int j){
        if(i<0 || i>=arr.length || j<0 || j>= arr[0].length ||arr[i][j] == 0){
            return 0;
        }
        arr[i][j] = 0;//走过的就后面不需要再看了，因为肯定已经联通了
        //上
        int top = getNodeMax(arr, i, j-1);
        //下
        int below = getNodeMax(arr, i, j+1);
        //左
        int left = getNodeMax(arr, i-1, j);
        //右
        int right = getNodeMax(arr, i+1, j);
        return 1 + top + below + left + right;

    }

    public static int[][] buildArray(String in){
        in = in.replaceAll("\\[","").replaceAll("\\]","");
        String[] inLine = in.split(";");
        int[][] arr = new int[inLine.length][inLine[0].split(",").length];
        for(int i=0;i<inLine.length;i++){
            String[] v = inLine[i].split(",");
            for(int j=0;j<v.length;j++){
                arr[i][j] = Integer.parseInt(v[j]);
            }
        }
        return arr;
    }
}
