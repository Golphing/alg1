package netease.easy;

import java.util.*;

/**
 * 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
 *
 * 实现 MyQueue 类：
 *
 * void push(int x) 将元素 x 推到队列的末尾
 *
 * int pop() 从队列的开头移除并返回元素
 *
 * int peek() 返回队列开头的元素
 *
 * boolean empty() 如果队列为空，返回 true ；否则，返回 false
 *
 * 注意：返回的的字符串结果需要是小写的格式
 *
 */
public class 用栈实现队列 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextLine()){
            String[] one = in.nextLine().split(",");
            String[] two = in.nextLine().split(",");
            LinkedList<String> list = new LinkedList<>();
            ArrayList<String> ret = new ArrayList<>();
            for(int i=0;i<one.length;i++){
                // System.out.println(one[i]);
                if(one[i].equals("push")){
                    // System.out.print("push");
                    list.offer(two[i]);
                    ret.add("null");
                }
                if(one[i] .equals( "pop")){
                    ret.add(list.poll());
                }
                if(one[i] .equals("peek")){
                    ret.add(list.peek());
                }
                if(one[i] .equals( "empty")){
                    if(list.isEmpty()){
                        ret.add("true");
                    }else {
                        ret.add("false");
                    }
                }
            }
            // System.out.print(ret.size());
            System.out.print(String.join(",", ret));
        }
    }
}
