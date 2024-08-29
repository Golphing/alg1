package netease.easy;

import java.util.Arrays;
import java.util.*;

/**
 * 思路：
 *     1、构建一个并查集的类，里面有个数组 parents[],代表每个元素的父节点，初始化都是自己。数组长度为输入数组里max+1(因为还有数组下标0)
 *          里面实现两个方法，一个是查找元素的父节点，一个是合并两个元素的父节点
 *     2、遍历所有输入的数据，求所有的约数，(截止条件是 i*i <= num,因为再继续下去就和前面的重复了，如8*2,其实就是2*8)；然后把两个数和当前的数union
 *     3、最后从parent里面找所有nums的根节点，找到一个就加1，最后输出最大值即可
 *
 * 记录易错点

 * 记录不记得的API
 */
public class 按公因数计算最大组件大小 {
    public static void main(String[] args) {
        String[] split = new Scanner(System.in).nextLine().split(" ");
        int[] nums = new int[split.length];
        for(int i = 0; i < split.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        System.out.println(getMax(nums));
    }

    public static int getMax(int[] nums){
        int m = Arrays.stream(nums).max().getAsInt();
        UnionFind uf = new UnionFind(m+1);
        for (int num : nums) {
            for(int i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    uf.union(num, i);
                    uf.union(num, num / i);
                }
            }
        }
        int[] counts = new int[m+1];
        int max = 0;
        for(int num: nums) {
            int root = uf.find(num);
            counts[root]+=1;
            max = Math.max(max, counts[root]);
        }
        return max;
    }
}

class UnionFind {
    private int[] p;

    public UnionFind(int size) {
        p = new int[size];
        for(int i = 0; i < size;i++) {
            p[i] = i;
        }
    }

    public int find(int x) {
        if(p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    public void union(int x,int y) {
        int rootX = find(x);
        int rootY = find(y);
        p[rootY] = rootX;
    }
}