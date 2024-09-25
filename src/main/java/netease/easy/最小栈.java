package netease.easy;

import java.util.*;

/**
 *
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 实现 MinStack 类:
 *
 * MinStack() 初始化堆栈对象。
 *
 * void push(int val) 将元素val推入堆栈。
 *
 * void pop() 删除堆栈顶部的元素。
 *
 * int top() 获取堆栈顶部的元素。
 *
 * int getMin() 获取堆栈中的最小元素。
 *
 *
 *易错点;
 *          String[] params = b.split(",", -1);
 *
 */
public class 最小栈 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String b = in.nextLine();
            System.out.println(ret(a, b));
        }
    }

    public static String ret(String a, String b){
        LinkedList<String> queue = new LinkedList();
        String[] orders = a.split(",");
        String[] params = b.split(",", -1);
        List<String> ret = new ArrayList();
        for(int i=0;i<orders.length;i++){
            String order = orders[i];
            String param = params[i];
            if("push".equals(order)){
                queue.push(param);
                ret.add("null");
            }
            if("pop".equals(order)){
                queue.pop();
                ret.add("null");
            }
            if("top".equals(order)){
                ret.add(queue.peek());
            }
            if("getMin".equals(order)){
                ret.add(get(queue));
            }
        }
        return String.join(",", ret);
    }

    public static String get(LinkedList<String> queue){
        Iterator<String> ite = queue.iterator();
        Long min = null;
        while(ite.hasNext()){
            String next = ite.next();
            if(min == null){
                min = Long.valueOf(next);
            }else {
                if(Long.valueOf(next) < min){
                    min = Long.valueOf(next);
                }
            }
        }
        return String.valueOf(min);
    }
}
