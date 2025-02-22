package leetcode;

/**
 *  334
 *  思路：
 *      用两个数组来维护一个是元素及其左边的最小值，一个是元素及其右边的最大值
 *      然后遍历数组，如果当前元素大于左边最小值，小于右边最大值，则返回true
 */
public class 递增的三元子序列00 {
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        int[] leftMin = new int[n];
        leftMin[0] = nums[0];
        for (int i = 1; i < n; i++) {
            leftMin[i] = Math.min(leftMin[i - 1], nums[i]);
        }
        int[] rightMax = new int[n];
        rightMax[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
        }
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] > leftMin[i - 1] && nums[i] < rightMax[i + 1]) {
                return true;
            }
        }
        return false;
    }
/*
    作者：力扣官方题解
    链接：https://leetcode.cn/problems/increasing-triplet-subsequence/solutions/1204375/di-zeng-de-san-yuan-zi-xu-lie-by-leetcod-dp2r/
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/

    public static void main(String[] args) {
        递增的三元子序列00 solution = new 递增的三元子序列00();
        int[] nums = {1, 2, 1, 3};
        boolean result = solution.increasingTriplet(nums);
        System.out.println("Result: " + result);
    }

}
