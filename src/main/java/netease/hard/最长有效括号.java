package netease.hard;

import java.util.Scanner;

/**
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 * 思路：
 *      动态规划，我们用dp[i]用来代表以第i位结尾的长度，那么根据i的值来判断：
 *          如果是 (,那么肯定等于0；这个不用管
 *          如果是 ),那么看i-dp[i-1] - 1的情况：(如果dp[i-1]已经形成环，那么就看 i-dp[i-1] - 1的情况)
 *              如果是 （，那么dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]];
 *              如果是 ),那么是0
 *
 *     简化：如果i是（，则为0
 *          如果 i是 ) ,且 i-dp[i-1]-1是 (,那么 dp[i] = dp[i-1] + 2 + max;
 *            max值： i-dp[i-1]-2 >= 0 ? dp[i-dp[i-1]-2] : 0
 * 记录易错点
 *                          dp[i] = dp[i-1] + 2 + ((i-dp[i-1]-2)>=0 ? dp[i-dp[i-1]-2]:0);  后面的要加括号
 *
 * 记录不记得的API
 *
 *
 */
public class 最长有效括号 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            System.out.println(getMax(a));
        }
    }

    public static int getMax(String in){
        int[] dp = new int[in.length()];
        for(int i=1;i<in.length();i++){
            if(in.charAt(i) == '(' || (i-dp[i-1]-1)<0){
                dp[i] = 0;
                continue;
            }
            //是）
            if(in.charAt(i-dp[i-1]-1) == '('){
                try{
                    dp[i] = dp[i-1] + 2 + ((i-dp[i-1]-2)>=0 ? dp[i-dp[i-1]-2]:0);
                }catch (Exception e){
                    System.out.println("错误： " + i) ;
                }
            }else {
                dp[i] = 0;
            }
        }
        int ret =0;
        for(int i=0;i<dp.length;i++){
            if(dp[i] > ret){
                ret = dp[i];
            }
        }
        return ret;
    }
}
