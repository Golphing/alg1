package netease.easy;

import java.util.*;

/**
 *给定n个物品和一个容量为C的背包，物品i的重量是Wi，其价值为Vi，背包问题是如何选择入背包的物品，使得装入背包的物品的总价值最大。
 *
 * 注意：你可以将物品的一部分装入背包，但不能重复装入。
 *
 *
 * 思路：根据单位价值来排序(使用Comparable接口)，然后扣减
 *
 * 易错：
 *      total最后一个之后需要置为0
 * 不记得的API：
 *  Comparable
 *      compareTo: 正序，返回 当前-传入
 */
public class 背包能装的最大价值 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String[] b = in.nextLine().split(",");
            String[] c = in.nextLine().split(",");
            Item[] items = new Item[b.length];
            for(int i=0;i<b.length;i++){
                items[i] = new Item(Integer.valueOf(b[i]), Integer.valueOf(c[i]));
            }
            Arrays.sort(items);
            int total = Integer.parseInt(a.split(",")[1]);
            double sum = 0;
            for(int i=0;i<b.length && total >0;i++){
                if(total >= items[i].weight){
                    sum += items[i].price;
                    total -= items[i].weight;
                }else{
                    sum += total*(items[i].unitPrice);
                    total=0;
                }
            }
            System.out.println((int)sum);
        }
    }

    static class Item implements Comparable<Item>{
        int weight;
        int price;
        double unitPrice;
        Item(int weight, int price){
            this.weight = weight;
            this.price = price;
            this.unitPrice = (double)price / weight;
        }

        @Override
        public int compareTo(Item other){
            if(other.unitPrice > this.unitPrice){
                return 1;
            }
            return -1;
        }
    }
}
