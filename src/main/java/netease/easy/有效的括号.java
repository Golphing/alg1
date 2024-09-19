package netease.easy;

import java.util.*;

/**
 *
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须在之后有相同类型的右括号配对，配对仅能一次
 *
 * 所有类型的括号必须以正确的顺序配对，先有左括号，再有右括号
 *
 * 括号要按顺序配对，不能出现不同类型的括号相互交叠
 *
 * 提示：
 *
 * 1 <= s.length <= 10^4
 *
 * s 仅由括号 '()[]{}' 组成
 *
 * 运行有时间和内存限制
 *
 *
 * 思路：
 *      用栈去匹配，匹配了就弹出来，最后判断栈是否为空
 *
 *
 *
 */
public class 有效的括号 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            System.out.println(match(a));
        }
    }

    public static int match(String in){
        LinkedList<Character> stack = new LinkedList<>();
        for(char c : in.toCharArray()){
            if(c == '}'){
                if(!stack.isEmpty() && stack.peek() == '{'){
                    stack.pop();
                    continue;
                }
            }
            if(c == ']'){
                if(!stack.isEmpty() && stack.peek() == '['){
                    stack.pop();
                    continue;
                }
            }
            if(c == ')'){
                if(!stack.isEmpty() && stack.peek() == '('){
                    stack.pop();
                    continue;
                }
            }
            stack.push(c);
        }
        return stack.isEmpty() ? 1 : 0;
    }
}
