package netease.easy;

import java.util.*;

/**
 *给定一个链表数组，每个链表都已经按升序排列。
 *
 * 请将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 *输入格式:
 * lists = [[1,4,5],[1,3,4],[2,6]]
 *
 * 输出格式:
 * [1, 1, 2, 3, 4, 4, 5, 6]
 *
 *
 * 思路：
 *        很恶心的一道题
 *       直接利用Collections.sort(list)排序，
 *       需要注意切换后的空字符串问题。
 *
 */
public class 合并K个升序链表 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            a = a.replaceAll("\\]","").replaceAll("\\[","");
            String[] b = a.split(",");
            List<Integer> ret = new ArrayList<>();
            for(int i=0;i<b.length;i++){
                if(!b[i].isEmpty()){
                    ret.add(Integer.parseInt(b[i]));
                }
            }
            Collections.sort(ret);
            // r = r.replaceAll(",", ", ");
            System.out.println(ret);
        }
    }
}
