package netease.easy;

import java.util.Scanner;

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。
 * 设计一个算法来计算你所能获取的最大利润。返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0
 *
 *
 * 题解：
 *      暴力破解，从任意天买入，任意天卖出
 *
 *
 */
public class 买卖股票的最佳时机 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String[] aq = a.split(",");
            int[] arr = new int[aq.length];
            for(int i=0;i<aq.length;i++){
                arr[i] = Integer.parseInt(aq[i]);
            }
            int ret = 0;
            for(int i=0;i<aq.length;i++){
                for(int j=i+1;j<aq.length;j++){
                    if((arr[j] - arr[i] ) > ret){
                        ret = arr[j] - arr[i];
                    }
                }
            }
            System.out.println(ret);
        }
    }
}
