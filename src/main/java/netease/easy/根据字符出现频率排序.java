package netease.easy;

import java.util.*;

/**
 * 思路：
 *          // 构建字符，对象 map
 *             //对象comparable
 *             //sort， collection.sort
 *             //打印
 *
 * 易错点：
 *      次数相同时，按字符升序排列
 * 不记得的API：
 *  containsKey()
 *  map.values() 是collection,  collecton再转成list
 *
 */
public class 根据字符出现频率排序 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            // 字符，对象
            //对象comparable
            //sort
            //打印
            Map<Character, Item> map = new HashMap<>();
            for(char a1 : a.toCharArray()){
                if(map.containsKey(a1)){
                    map.get(a1).count++;
                }else {
                    map.put(a1, new Item(a1));
                }
            }
            List<Item> v = new ArrayList(map.values());
            Collections.sort(v);
            for(Item i : v){
                while(i.count >0){
                    System.out.print(i.a);
                    i.count--;
                }
            }
        }
    }

    static class Item implements Comparable<Item>{
        char a;
        int count;
        Item(char a){
            this.a = a;
            this.count = 1;
        }
        @Override
        public int compareTo(Item item){
            if(item.count != this.count){
                return item.count - this.count;
            }else {
                return this.a - item.a;
            }
        }
    }
}
