package leetcode;

/**
 *  643  https://leetcode.cn/problems/maximum-average-subarray-i/description/?envType=study-plan-v2&envId=leetcode-75
 *
 * 滑动窗口来做
 */
public class 子数组最大平均数I {
    public static void main(String[] args) {
        System.out.println(((double)-1/(double)1));
        System.out.println((double)(-1000000l));
    }

    //我的解答
    public double findMaxAverage(int[] nums, int k) {
        int len = nums.length;
        int[] numsK = new int[len];
        for(int i=0;i<len;i++){
            if(i==0){
                numsK[i]=nums[i];
            }else if(i<k){
                numsK[i]=numsK[i-1]+nums[i];
            }else {
                numsK[i] = numsK[i-1] + nums[i] - nums[i-k];
            }
        }
        double ret = (double)(-1000000l);
        for(int i=k-1;i<len;i++){
            double t = (double)numsK[i] / (double)k;
            if(t > ret){
                ret = t;
            }
        }
        return ret;
    }
}
