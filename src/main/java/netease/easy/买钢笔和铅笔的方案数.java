package netease.easy;

import java.util.Scanner;

/**
 *假设你有x元钱，一支钢笔a元，一支铅笔b元。你可以花费部分或者全部的钱，购买任意数目的两种笔（注：购买0支也是允许的）。
 *
 * 请返回购买钢笔和铅笔的所有可能的方案有多少种。
 *
 * 其中，x、a、b均为正整数，且小于1e6。
 *
 * 例如x=20，a=10，b=5，则：
 * - 如果你买 0 支钢笔，那么你可以买 0 ，1 ，2 ，3 或者 4 支铅笔。
 *
 * - 如果你买 1 支钢笔，那么你可以买 0 ，1 或者 2 支铅笔。
 *
 * - 如果你买 2 支钢笔，那么你只能买0支铅笔。
 *
 * 思路:
 *      不能暴力破解
 *      注意用long接收
 *
 *
 *
 */
public class 买钢笔和铅笔的方案数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(",");
            long money = Long.parseLong(a[0]);
            long p1 = Long.parseLong(a[1]);
            long p2 = Long.parseLong(a[2]);
            System.out.println(getW(money, p1, p2));
        }
    }

    public static long getW(long money, long p1, long p2){
        long count =0;
        for(int i=0;i*p1<=money;i++){
            long p22 = (money - i*p1)/p2;
            count += p22+1;
        }
        return count;
    }
}
