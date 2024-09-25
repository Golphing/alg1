package netease.easy;

import java.util.*;

/**
 *给定两个字符串 s 和 t ，判断它们是否是同构的。
 * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
 * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
 *
 *
 *
 *
 */
public class 同构字符串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String[] b = a.split(",");
            System.out.println(judge(b[0], b[1]));
        }
    }

    public static String judge(String a, String b){
        if(a== null || b==null || a.length() != b.length()){
            return "0";
        }
        Map<Character, Character> map = new HashMap<>();
        Map<Character, Character> map1 = new HashMap<>();

        for(int i=0;i<a.length();i++){
            char a1 = a.charAt(i);
            char b1 = b.charAt(i);
            if(map.containsKey(a1)){
                char v = map.get(a1);
                if(v != b1){
                    return "0";
                }
            }else {
                map.put(a1, b1);
            }
            if(map1.containsKey(b1)){
                char v = map1.get(b1);
                if(v != a1){
                    return "0";
                }
            }else {
                map1.put(b1, a1);
            }
        }
        return "1";
    }
}
