package netease.easy;

import java.util.*;

/**
 * 思路：
 *      一个map记录所有的节点：key是地址，valueNode
 *      构造两个LinkedList，判断哪个长，哪个短
 *      遍历长的，将短的逆向遍历加入
 *      完成后，重新设置一下next的地址
 *      按顺序打印
 * 记录易错点
 *       没有scanner.nextString()
 *       Node(String a, String b , String c){}构造函数没有修饰词
 *       最后需要把0补齐
 *
 * 记录不记得的API
 *
 *     Iterator<Node> iter2 = LinkedList.descendingIterator();这个iterator是反转遍历
 *      LinkedList.getLast() 返回最后一个元素
 *      list1.size()
 *      String.format("%05d %d %05d", address, data, next):
 *
 */
public class 按格式合并两个链表 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] str = in.nextLine().split(" ");
        String firstAddr = str[0];
        String secondAddr = str[1];
        int count = Integer.valueOf(str[2]);
        Map<String, Node> map = new HashMap<>();
        int i=count;
        while (i>0) {
            str = in.nextLine().split(" ");
            Node n = new Node(str[0],str[1],str[2]);
            map.put(n.addr, n);
            i--;
        }
        LinkedList<Node> a = getList(firstAddr, map);
        // System.out.println(a.size());
        LinkedList<Node> longer = null;
        LinkedList<Node> shorter=null;
        LinkedList<Node> b = getList(secondAddr, map);
        // System.out.println(b.size());
        if(a.size() > b.size()){
            longer = a;
            shorter = b;
        }else {
            longer = b;
            shorter = a;
        }
        LinkedList<Node> res = new LinkedList<>();
        Iterator<Node> li = longer.iterator();
        Iterator<Node> si = shorter.descendingIterator();
        while(true){
            if(li.hasNext()){
                res.add(li.next());
            }else{
                break;
            }
            if(li.hasNext()){
                res.add(li.next());
            }else{
                break;
            }
            if(si.hasNext()){
                res.add(si.next());
            }else{
                break;
            }
        }
        while(li.hasNext()){
            res.add(li.next());
        }

        //重新搞一下next地址
        Node prev = null;
        for(Node n : res){
            if(prev != null){
                prev.next = n.addr;
            }
            prev = n;
        }
        prev.next = "-1";
        for(Node n : res){
            System.out.println(n.addr +" " + n.value +" " + n.next);
        }


    }

    public static LinkedList<Node> getList(String addr, Map<String, Node> map){
        LinkedList<Node> longer = new LinkedList<>();
        Node n = map.get(addr);
        longer.add(n);
        while(true){
            if("-1".equals(n.next)){
                break;
            }
            n = map.get(n.next);
            longer.add(n);
        }
        return longer;
    }
}

class Node{
    String addr;
    String value;
    String next;
    Node(String a, String b , String c){
        addr = a;
        value = b;
        next = c;
    }
}
