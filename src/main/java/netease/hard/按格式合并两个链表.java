package netease.hard;

import java.util.*;

/**
 * 给定两个链表L1=a1→a2→⋯→an−1→an 和L2=b1→b2→⋯→bm−1→bm，其中n≥2m。
 * 需要将较短的链表L2反转并合并到较长的链表L1中
 * 使得合并后的链表如下形式：a1→a2→bm→a3→a4→bm−1→…
 * 合并规则：在长链表中每隔两个元素，将短链表中的元素倒序插入。
 *
 * 输入格式:
 * 第一行包含两个链表的第一个节点地址（不确定哪个链表更长），以及两个链表的总节点数N(≤100000)。
 * 节点地址用一个 5 位非负整数表示（可能有前导 0）
 *
 * 输出：
 *  对于每个测试用例，按顺序输出合并后的结果链表。每个结点占一行，按输入的格式输出。
 *
 * 思路：
 *      一个map记录所有的节点：key是地址，value：Node
 *      构造两个LinkedList，判断哪个长，哪个短
 *      遍历长的，将短的逆向遍历加入
 *      完成后，重新设置一下next的地址
 *      按顺序打印
 * 记录易错点
 *       没有scanner.nextString()
 *       Node(String a, String b , String c){}构造函数没有修饰词
 *       需要重新设置next的地址
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

        //重新设置一下next地址
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

    static class Node{
        String addr;
        String value;
        String next;
        Node(String a, String b , String c){
            addr = a;
            value = b;
            next = c;
        }
    }
}


