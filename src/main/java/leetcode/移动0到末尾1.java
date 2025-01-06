package leetcode;

/**
 * 保持非零元素的相对顺序
 *
 * 注意：
 *      1）非零的顺序不能改
 *      2）注意结束条件
 *      3）注意别把 1 0 交换成了 0 1
 */
public class 移动0到末尾1 {
    public void moveZeroes(int[] nums) {
        int zero = 0;
        int nonz = 0;
        int n = nums.length;
        while(zero < n && nonz < n){
            //找到0的，再找到非0的，进行交换，接着找0，接着找非0
            while(zero < n && nums[zero] != 0){
                zero++;
            }
            nonz = zero;
            while(nonz < n && nums[nonz] == 0){
                nonz++;
            }
            if(zero < n && nonz < n && zero < nonz)
                swap(nums, zero, nonz);
            zero++;nonz++;
        }
    }

    public void swap(int[] nums, int i, int j){
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
