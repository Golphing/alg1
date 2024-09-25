package netease.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *有一堆石头，每块石头的重量都是正整数。
 * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 * 如果 x == y，那么两块石头都会被完全粉碎；
 * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
 *
 *
 *
 * 不记得的API：
 *              PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
 *
 *
 *
 */
public class 求最后一块石头的重量 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            System.out.println(a(a));
        }
    }

    public static Integer a(String c){
        String[] cc = c.split(",");
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b-a);
        for(String ccc : cc){
            q.offer(Integer.valueOf(ccc));
        }
        while(q.size() > 1){
            int a = q.poll();
            int b = q.poll();
            if((a-b) > 0){
                q.offer(a-b);
            }
        }
        if(q.size() == 0){
            return 0;
        }else {
            return q.poll();
        }
    }
}
