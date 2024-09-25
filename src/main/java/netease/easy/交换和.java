package netease.easy;

import java.util.*;

/**
 *
 * 给定两个整数数组，请交换一对数值（每个数组中取一个数值），使得两个数组所有元素的和相等。
 * 返回一个数组，第一个元素是第一个数组中要交换的元素，第二个元素是第二个数组中要交换的元素。
 * 若有多个答案，返回所有满足条件的答案。若无满足条件的数值，不输出。
 *
 *
 *
 */
public class 交换和 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String b = in.nextLine();
            List<Item> ret = ex(a, b);
            for(Item a1 : ret){
                System.out.println(a1.one +" " + a1.two);
            }
        }
    }

    public static List<Item> ex(String a, String b){
        //计算差值
        //遍历且去重
        //排序
        //打印
        String[] a1 = a.split(" ");
        String[] b1 = b.split(" ");
        int[] one = tras(a1);
        int[] two = tras(b1);
        int sum1 = 0;
        for(int aa : one){
            sum1 += aa;
        }
        int sum2=0;
        for(int bb : two){
            sum2 +=bb;
        }
        int gap = sum1-sum2;
        Set<Integer> set = new HashSet<>();
        List<Item> ret = new ArrayList<>();
        for(int i=0;i<one.length;i++){
            for(int j=0;j<two.length;j++){
                if((one[i]-two[j])*2==gap){
                    if(!set.contains(one[i])){
                        set.add(one[i]);
                        ret.add(new Item(one[i], two[j]));
                    }
                }
            }
        }
        Collections.sort(ret);
        return ret;
    }

    public static int[] tras(String[] in){
        int[] out = new int[in.length];
        for(int i=0;i<in.length;i++){
            out[i]=Integer.parseInt(in[i]);
        }
        return out;
    }

    static class Item implements Comparable<Item>{
        int one;
        int two;
        Item(int a, int b){
            one=a;
            two=b;
        }

        @Override
        public int compareTo(Item item){
            if(one == item.one){
                return two-item.two;
            }
            return one-item.one;
        }
    }
}
