package netease.easy;

import java.util.*;

/**
 * 学校的自助午餐提供圆形和方形的三明治，分别用数字 0 和 1 表示。所有学生站在一个队列里，每个学生要么喜欢圆形的要么喜欢方形的。
 * 餐厅里三明治的数量与学生的数量相同。所有三明治都放在一个 栈 里，每一轮：
 *
 * 如果队列最前面的学生 喜欢 栈顶的三明治，那么会 拿走它 并离开队列。
 *
 * 否则，这名学生会 放弃这个三明治 并回到队列的尾部。
 *
 * 这个过程会一直持续到队列里所有学生都不喜欢栈顶的三明治为止。
 *
 *
 */
public class 无法吃午餐的学生数量 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String b = in.nextLine();
            System.out.println(remain(b, a));
        }
    }

    public static int remain(String sand, String stu){
        LinkedList<String> list = new LinkedList<>();
        LinkedList<String> ss = new LinkedList<>();
        for(String s : sand.split(" ")){
            if(!s.isEmpty())
                list.offer(s);
        }
        for(String s : stu.split(" ")){
            if(!s.isEmpty())
                ss.offer(s);
        }
        int count = 0;
        while(!list.isEmpty()){
            String d = ss.poll();
            count++;
            if(d.equals(list.peek())){
                list.pop();
                count = 0;
            }else {
                ss.offer(d);
            }
            if(count >201){
                return list.size();
            }
        }
        return 0;
    }
}
