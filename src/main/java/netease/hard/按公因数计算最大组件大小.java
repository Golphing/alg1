package netease.hard;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 思路：
 *     1、构建一个并查集的类，里面有个数组 parents[],代表每个元素的父节点，初始化都是自己。数组长度为输入数组里max+1(因为还有数组下标0)
 *          里面实现两个方法，一个是查找元素的父节点，一个是合并两个元素的父节点
 *     2、遍历所有输入的数据，求所有的约数，(截止条件是 i*i <= num,因为再继续下去就和前面的重复了，如8*2,其实就是2*8)；然后把两个数和当前的数union
 *     3、最后从parent里面找所有nums的根节点，找到一个就加1，最后输出最大值即可
 *
 * 记录易错点
 *      int[] counts = new int[max+1];这里需要+1,否则可能会溢出

 * 记录不记得的API
 *          int max = Arrays.stream(nums).max().getAsInt();
 */
public class 按公因数计算最大组件大小 {
    public static void main(String[] args) {
        String[] split = new Scanner(System.in).nextLine().split(" ");
        int[] nums = new int[split.length];
        for(int i = 0; i < split.length; i++) {
            nums[i] = Integer.parseInt(split[i]);
        }
        int max = Arrays.stream(nums).max().getAsInt();
        UnionA ulp = new UnionA(max);
        // System.out.print(max);
        for(int num : nums){
            for(int i=2;i*i<=num;i++){
                if(num % i == 0){
                    ulp.union(num, i);
                    ulp.union(num, num / i);
                }
            }
        }
        int[] counts = new int[max+1];
        int maxV = 0;
        for(int num : nums){
            int p = ulp.find(num);
            counts[p]++;
            if(counts[p] > maxV){
                maxV = counts[p];
            }
        }
        System.out.println(maxV);
    }

    static class UnionA{
        public int[] parents;

        UnionA(int max){
            parents = new int[max+1];
            for(int i=0;i<=max;i++){
                parents[i] =  i;
            }
        }

        public int find(int x){
            if(parents[x] != x){
                parents[x] = find(parents[x]);
            }
            return parents[x];
            // int node = x;
            // while(parents[node] != node){
            //     // System.out.println("node: " + node);
            //     node = parents[node];
            // }
            // parents[x] = node;
            // return node;
        }

        public void union(int x, int y){
            int px = find(x);
            int py = find(y);
            parents[px] = py;
        }
    }
}


