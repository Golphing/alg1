package netease.easy;

import java.util.*;

/**
 *
 * 直接set判断，然后加入list排序输出即可
 *
 *
 */
public class 求两个数组的交集 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(";");
            Set<Integer> set = new HashSet<>();
            for(String s1 : a[0].split(",")){
                set.add(Integer.parseInt(s1));
            }
            Set<Integer> list = new HashSet<>();
            for(String s2 : a[1].split(",")){
                if(set.contains(Integer.parseInt(s2))){
                    list.add(Integer.parseInt(s2));
                }
            }
            List<Integer> aaa = new ArrayList(list);
            Collections.sort(aaa);
            for(int i=0;i<aaa.size();i++){
                System.out.print(aaa.get(i));
                if(i != aaa.size()-1){
                    System.out.print(",");
                }
            }
        }
    }
}
