package netease.easy;

import java.util.Scanner;

/**
 *
 *给定一个由 4 位数字组成的数组，返回可以设置的符合 24 小时制的最大时间。
 *
 * 24 小时格式为HH:MM ，其中 HH 在 00 到 23 之间，MM 在 00 到 59 之间。最小的 24 小时制时间是 00:00 ，而最大的是 23:59。
 *
 * 以长度为 5 的字符串，按 HH:MM 格式返回答案。如果不能确定有效时间，则返回空字符串。
 *
 * 输入格式:
 * 4个0~9之间的数字，以,分隔。例如4,2,3,1
 *
 *
 *
 */
public class 给定数字组成最大时间 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String ac = in.nextLine();
            String[] a = ac.split(",");
            int[] b = new int[4];
            for(int i=0;i<4;i++){
                b[i]=Integer.valueOf(a[i]);
            }
            System.out.println(largestTimeFromDigits(b));

        }
    }

    public static String largestTimeFromDigits(int[] a) {
        int max=0;
        String val = "";
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a.length;j++){
                for(int k=0;k<a.length;k++){
                    for(int z=0;z<a.length;z++){
                        // System.out.println(i+" "+j+" "+k+" "+z);
                        if(i==j || i==k || j==k || z==i||z==j||z==k){
                            continue;
                        }
                        if(a[i]>2 || a[k] >5){
                            continue;
                        }
                        if(a[i]==2 && a[j]>=4){
                            continue;
                        }
                        String v = ""+a[i]+a[j]+a[k]+a[z] +"";
                        int va = Integer.valueOf(v);
                        // System.out.println(va);
                        if(va > max){
                            max = va;
                            val = ""+a[i]+a[j]+":"+a[k]+a[z] +"";
                        }
                    }
                }
            }
        }
        return val;
    }
}
