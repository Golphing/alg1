package netease.hard;

import java.util.Scanner;

/**
 * 给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
 * 现在从这两个数组中选出 k (0 <=k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
 * 求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
 * 输入格式:
 * 输入三个行内容：
 * 第一行是数组nums1，元素内容用逗号分隔；数组最大长度为1000。
 * 第二行是数组nums2，元素内容用逗号分隔；数组最大长度为1000。
 * 第三行是长度k；
 *
 *
 * 思路：
 *      一共要k个数，那么遍历从第一个数组里面取 0 ～ min(k, 第一个数组长度)个数，然后从第二个数组里面取剩下的数
 *      然后看看从每个数组里面取n个数的最大值(这个使用单调栈，只要发现后面的比前面的大，那就删掉前面的用后面的，但是有个最大删除数)
 *      然后合并最大值(不仅要比较当前值，还要比较后面的，如果后面的都一样，那么就哪个长哪个大)
 * 记录易错点
 *              for(int i=0;i<=k && i<=num1.length;i++) 不是        for(int i=0;i<=k,i<=num1.length;i++)
 *              if( (k-i) <= num2.length && k-i >=0)  还需要大于等于0
 * 记录不记得的API
 *
 *
 */
class 拼接最大数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] a = in.nextLine().split(",");
        String[] b = in.nextLine().split(",");
        int[] num1 = trans(a);
        int[] num2 = trans(b);
        int k = in.nextInt();
        int[] res = maxNumber(num1, num2, k);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<res.length;i++){
            sb.append(res[i]);
            if(i != res.length - 1){
                sb.append(",");
            }
        }
        System.out.print(sb.toString());
    }

    public static int[] trans(String[] a){
        int[] r = new int[a.length];
        for(int i=0;i<a.length;i++){
            r[i] = Integer.valueOf(a[i]);
        }
        return r;
    }

    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] res = new int[0];
        // 从 nums1 中选出长 i 的子序列
        for (int i = 0; i <= k && i <= nums1.length; i++) {
            // 从 nums2 中选出长 k - i 的子序列
            if (k - i >= 0 && k - i <= nums2.length) {
                // 合并
                int[] tmp = merge(subMaxNumber(nums1, i), subMaxNumber(nums2, k - i));
                // 取最大值
                if (compare(tmp, 0, res, 0)) {
                    res = tmp;
                }
            }
        }
        return res;
    }

    // 类似于单调递减栈
    // 贪心算法，只要发现后面的比前面的大，那么就把前面的删除掉
    public static int[] subMaxNumber(int[] nums, int len) {
        if(len == 0) return new int[0];
        int[] subNums = new int[len];
        int cur = 0, rem = nums.length - len; // rem 表示还可以删去多少字符
        for (int i = 0; i < nums.length; i++) {
            while (cur > 0 && subNums[cur - 1] < nums[i] && rem > 0) {
                cur--;
                rem--;
            }
            if (cur < len) {//只有小的情况才插入
                subNums[cur++] = nums[i];
            }
        }
        return subNums;
    }

    public static int[] merge(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length + nums2.length];
        int cur = 0, p1 = 0, p2 = 0;
        while (cur < nums1.length + nums2.length) {
            if (compare(nums1, p1, nums2, p2)) { // 不能只比较当前值，如果当前值相等还需要比较后续哪个大
                res[cur++] = nums1[p1++];
            } else {
                res[cur++] = nums2[p2++];
            }
        }
        return res;
    }

    public static boolean compare(int[] nums1, int p1, int[] nums2, int p2) {
        //这里是为了更长的数组后面的数字能够到前面来，机会更多，所以就是优先去长的子序列
        if (p2 >= nums2.length) return true;
        if (p1 >= nums1.length) return false;
        if (nums1[p1] > nums2[p2]) return true;
        if (nums1[p1] < nums2[p2]) return false;
        return compare(nums1, p1 + 1, nums2, p2 + 1);
    }
}