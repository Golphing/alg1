package leetcode;

/**
 * 238
 * https://leetcode.cn/problems/product-of-array-except-self/?envType=study-plan-v2&envId=leetcode-75
 * 思路：
 *      方法一：计算每个元素左边的乘积和右边的乘积，那么这个数的值就出来了
 *      方法二：
 *          计算每个元素左边的乘积，右边的乘积不需要用数据记录，实时算实时用
 *
 */
public class 除自身以外数组的乘积1 {
    public int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        ans[0] = 1;
        for(int i=1;i<nums.length;i++){
            ans[i] = ans[i-1] * nums[i-1];
        }
        int right = 1;
        for(int j=nums.length-1;j>=0;j--){
            ans[j] = ans[j] * right;
            right = right * ans[j];
        }
        return ans;
    }
}
