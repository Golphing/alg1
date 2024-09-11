package netease.hard;

import java.util.*;

/**
 * 题目：
 *      给你一个整数数组 arr 。将 arr 分割成若干 块 ，并将这些块分别进行排序。之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
 *      返回能将数组分成的最多块数？
 *
 * 思路：
 *      找到每个块的最大值，栈里面保存的就是最大值，当后面的值大于栈顶的话那么就push形成新的块，如果小于栈顶，那么就将栈顶pop出来，直到栈顶小于当前值，最后将弹出的值塞入，因为他是最大的。
 *      最后栈的size就是答案
 * 易错点
 *      while(!stack.isEmpty() && stack.peek() > item)  这里是只要大于，就要合并，而不是小于
 *
 *
 * 不记得的API：
 *      stack.size();
 *
 */
public class 最多能完成排序的块 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String b = in.nextLine();
            String[] a = b.split(" ");
            int[] arr = new int[a.length];
            for(int i=0;i<a.length;i++){
                arr[i] = Integer.parseInt(a[i]);
            }
            System.out.println(getNum(arr));
        }
    }

    public static int getNum(int[] arr){
        LinkedList<Integer> stack = new LinkedList<>();
        for(int item : arr){
            if(stack.isEmpty() || item >= stack.peek()){
                stack.push(item);
                continue;
            }
            if(item < stack.peek()){
                int max = stack.pop();
                while(!stack.isEmpty() && stack.peek() > item){
                    stack.pop();
                }
                stack.push(max);
            }
        }
        return stack.size();
    }
}
