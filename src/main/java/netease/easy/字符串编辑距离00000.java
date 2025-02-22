package netease.easy;

import java.util.*;

/**
 *给定一个源串和目标串，能够对源串进行如下操作：
 * 在给定位置上插入一个字符
 * 替换任意字符
 * 删除任意字符
 * 写一个程序，返回最小操作数，使得对源串进行这些操作后等于目标串
 *
 * 输入格式:
 * 函数的输入是一行包含两个字符串，word1 和 word2，以空格分割
 * 每个字符串的长度大于0同时小于500，word1 和 word2 由小写英文字母组成
 *
 * 输出格式:
 * 输出两个字符的编辑距离，是一个整数
 *
 *
 *
 * 思路：
 *         dp[m][n]] = min(dp[m-1][n], dp[m][n-1], dp[m-1][n-1]) + 1; 如果m !=n
 *                   = dp[m-1][n-1]; 如果m == n
 *
 *
 *          if (word1.charAt(m - 1) == word2.charAt(n - 1)) {
 *             int result = minDistanceHelper(word1, word2, m - 1, n - 1, memo);
 *             memo.put(key, result);
 *             return result;
 *         }
 *
 *         int insertCost = minDistanceHelper(word1, word2, m, n - 1, memo) + 1;
 *         int deleteCost = minDistanceHelper(word1, word2, m - 1, n, memo) + 1;
 *         int replaceCost = minDistanceHelper(word1, word2, m - 1, n - 1, memo) + 1;
 *
 *          需要一个备忘录
 *
 */
public class 字符串编辑距离00000 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String a = in.nextLine();
            String[] in1 = a.split(" ");
            System.out.println(minDistance(in1[0],in1[1]));
        }
    }



    public static int minDistance(String word1, String word2) {
        Map<String, Integer> memo = new HashMap<>();
        return minDistanceHelper(word1, word2, word1.length(), word2.length(), memo);
    }

    private static int minDistanceHelper(String word1, String word2, int m, int n, Map<String, Integer> memo) {
        String key = m + "," + n;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (m == 0) {
            memo.put(key, n);
            return n;
        }
        if (n == 0) {
            memo.put(key, m);
            return m;
        }

        if (word1.charAt(m - 1) == word2.charAt(n - 1)) {
            int result = minDistanceHelper(word1, word2, m - 1, n - 1, memo);
            memo.put(key, result);
            return result;
        }

        int insertCost = minDistanceHelper(word1, word2, m, n - 1, memo) + 1;
        int deleteCost = minDistanceHelper(word1, word2, m - 1, n, memo) + 1;
        int replaceCost = minDistanceHelper(word1, word2, m - 1, n - 1, memo) + 1;

        int result = Math.min(insertCost, Math.min(deleteCost, replaceCost));
        memo.put(key, result);
        return result;
    }
}
