package netease.easy;

import java.util.*;

/**
 * 题目:
 *      给你一个整数数组 nums 和一个整数 k 。请你向 nums 中追加 k 个 未 出现在 nums 中的、互不相同 的 正 整数，并使结果数组的元素和 最小 。
 *          返回追加到 nums 中的 k 个整数之和。
 * 思路：
 *      直接暴力破解，看看数据是不是包含在之前的数组里面，不是的直接加上去
 */
public class 向数组中追加K个整数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String b = in.nextLine();
            String[] a1 = a.split(" ");
            HashSet<String> exists = new HashSet<>();
            for(String aa : a1){
                exists.add(aa);
            }
            int num = Integer.parseInt(b);
            System.out.println(getNum(exists, num));
        }
    }

    public static int getNum(HashSet<String> exists, int num){
        int i=1;
        int sum = 0;
        while(true && num >0){
            if(exists.contains(String.valueOf(i))){
                i++;
                continue;
            }
            sum +=i;
            i++;
            num--;
        }
        return sum;
    }
}
