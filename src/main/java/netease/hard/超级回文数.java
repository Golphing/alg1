package netease.hard;

import java.util.*;

/**
 * 题目：
 *      如果一个正整数自身是回文数，而且它也是一个回文数的平方，那么我们称这个数为超级回文数。
 * 现在，给定两个正整数 L 和 R ，请按照从小到大的顺序打印包含在范围 [L, R] 中的所有超级回文数。
 * 注：R包含的数字不超过20位
 * 回文数定义：将该数各个位置的数字反转排列，得到的数和原数一样，例如676，2332，10201。
 *
 *
 * 思路：
 *      先判断遍历的范围，R包含的数字不超过20位，那么他的子不超过10位，那么子如果是回文的话，回文左边就不抄过5位，那么值肯定就小于 100000；所有遍历范围就是 1-100000；
 *      构建回文，可能是奇数可能是偶数， 奇数无重叠，偶数会镜像
 *      然后判断构建的回文数的值是不是回文以及大小是否合适
 *
 *      是否回文数判断：两个指针遍历判断
 *      构建回文：通过字符串来搞也行，不过要加上原字符串
 *
 * 易错点：
 *      构建回文：通过字符串来搞也行，不过要加上原字符串
 *
 * 不记得的API：
 *      Collections.sort(list);
 *
 */
public class 超级回文数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Long l = Long.parseLong(s.split(",")[0]);
        Long r = Long.parseLong(s.split(",")[1]);
        List<Long> list = new ArrayList<>();
        for (int i = 1; i < 100000; i++) {
            func(list, reverse(i, false), l, r);
            func(list, reverse(i, true), l, r);
        }
        Collections.sort(list);
        System.out.println(list.toString());
    }

    public static void func(List<Long> list, long num, Long l, Long r) {
        num = num * num;
        if (judge(String.valueOf(num)) && num >= l && num <= r) {
            list.add(num);
        }
    }

    public static long reverse(long num, boolean isEven) {
        String res = String.valueOf(num);
        StringBuilder sb = new StringBuilder(res);
        if(isEven){
            for(int i=res.length()-1;i>=0;i--){
                sb.append(res.charAt(i));
            }
        }else {
            for(int i=res.length()-2;i>=0;i--){
                sb.append(res.charAt(i));
            }
        }
        return Long.parseLong(sb.toString());
    }

    public static boolean judge(String num) {
        int len = num.length();
        if (len < 2) {
            return true;
        }

        int p = 0, q = len - 1;
        while (p < q) {
            if (num.charAt(p) != num.charAt(q)) {
                return false;
            }
            p++;
            q--;
        }
        return true;
    }
}
