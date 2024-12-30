package netease.medium;

import java.util.*;

/**
 * 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
 * 括号只考虑 "(" 和 ")" ，有效的括号是指一系列左括号 "(" 和 ")" 组成；但是如果有一些额外的括号，使得括号不能正确配对，就需要删除。
 * 删除规则：从前往后，且尽可能少的删除多余括号。
 *
 * 输入格式:
 *      输入一个字符串，字符串的长度小于1000。字符串中只包含字母、 "(" 和 ")"
 *
 * 输出格式:
 *   输出处理后的字符串
 *
 * 思路：
 *      一个数组用来记录某个字符是否有效，我们默认所有的左括号都是无效，然后存入栈中。当遇到右括号的时候看看栈内是否有，如果有，则改成有效，如果没有，则右括号也是无效的
 *
 *
 */
public class 删除无效的括号 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String output = doAction(sc.nextLine());
        System.out.println(output);
    }

    private static String doAction(String s) {
        StringBuilder result = new StringBuilder();
        boolean[] invalidIndex = new boolean[s.length()];
        LinkedList<Integer> leftIn = new LinkedList<>();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '('){
                invalidIndex[i] = true;
                leftIn.push(i);
            }else if(c == ')'){
                if(leftIn.isEmpty()){
                    invalidIndex[i]=true;
                }else {
                    int lindex = leftIn.pop();
                    invalidIndex[lindex] = false;
                }
            }
        }
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(!invalidIndex[i]){
                result.append(c);
            }
        }
        return result.toString();
    }
}
