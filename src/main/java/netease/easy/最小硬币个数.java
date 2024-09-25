package netease.easy;

import java.util.Scanner;

/**
 *
 * 假设现在有一堆硬币，其中有足够个1元硬币、足够个2元硬币和足够个5元硬币。现在需要用这些硬币凑出总价值为n元的钱，求最少需要多少枚硬币？
 *
 *
 */
public class 最小硬币个数 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int a = in.nextInt();
            System.out.println(getMinCoin(a));
        }
    }

    public static int getMinCoin(int sum){
        int count = sum / 5;
        count +=(sum%5)/2;
        count +=(sum%5%2);
        return count;
    }
}
