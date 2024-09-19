package netease.easy;

import java.util.Scanner;

/**
 * 请编写一个字符串压缩程序，将字符串中连续出席的重复字母进行压缩，并输出压缩后的字符串。
 *
 * 题解：
 *      直接暴力解
 *
 */
public class 压缩字符串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            Character c = null;
            for(char a1 : a.toCharArray()){
                if(c == null){
                    c = a1;
                    System.out.print(a1);
                }else {
                    if(c == a1){
                        continue;
                    }else {
                        c = a1;
                        System.out.print(a1);
                    }
                }
            }
        }
    }
}
