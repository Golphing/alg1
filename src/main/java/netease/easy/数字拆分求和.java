package netease.easy;

import java.util.*;

/**
 *
 *对于给定的正整数k，将其表示为一个正整数序列之和，且该序列中相邻元素的差值为等差分布（等差分布从1开始，差值为1，即1,2,3,...,）
 *
 * 注意：请打印出所有可能的序列（打印多个序列时，按照首个数字从小到大依次打印）
 *
 */
public class 数字拆分求和 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            int val = Integer.parseInt(a);
            for(int i=1;i<val;i++){
                List<String> list = new ArrayList<>();
                int delta = 1;
                int prev = i;
                int sum = i;
                list.add(String.valueOf(i));
                while(true){
                    int now = delta++ + prev;
                    prev = now;
                    sum = sum + now;
                    if(sum == val){
                        list.add(String.valueOf(now));
                        System.out.println(String.join(",", list));
                    }else if(sum > val){
                        break;
                    }else {
                        list.add(String.valueOf(now));
                    }
                }
            }
        }
    }
}
