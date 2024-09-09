package netease.hard;


import java.util.*;

/**
 *  题目：
 *      二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * 路径和 是路径中各节点值的总和。
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 *
 *  思路：
 *      1、先构建一颗二叉数，这个是利用栈来构建的。
 *      2、然后利用深度优先算法，来看最长路径
 *              dfs当前 = (dfs左边贡献，dfs右边贡献)的最大值 + 当前节点值
 *              以及max和 dfs左边贡献 + dfs右边贡献 + 当前节点值
 *  记录易错点
 *      public static int max = Integer.MIN_VALUE;
 *                  if( i<ins.length && !"null".equals(ins[i]) ){  一定要先判断越界的情况
 *  记录不记得的API
 *
 */
public class 二叉树的最大路径和 {
    public static int max = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            max = Integer.MIN_VALUE;
            String a = in.nextLine();
            Node root = buildTree(a);
            dfs(root);
            System.out.println(max);
        }
    }

    public static int dfs(Node node){
        if(node == null){
            return 0;
        }
        int leftGain = Math.max(dfs(node.left),0);
        int rightGain = Math.max(dfs(node.right),0);
        int nodeM = leftGain + rightGain + node.val;
        if(nodeM > max){
            max = nodeM;
        }
        return node.val + Math.max(leftGain, rightGain);
    }

    public static Node buildTree(String input){
        if(input == null || input.length()==0){
            return null;
        }
        String[] ins = input.split(",");
        Node root = new Node(Integer.parseInt(ins[0]));
        Queue<Node> stack = new LinkedList<>();
        stack.offer(root);
        int i=1;
        while(!stack.isEmpty() && i<ins.length){
            Node node = stack.poll();
            if( i<ins.length && !"null".equals(ins[i]) ){
                Node left = new Node(Integer.parseInt(ins[i]));
                node.left = left;
                stack.offer(left);
            }
            i++;
            if( i<ins.length && !"null".equals(ins[i])){
                Node right = new Node(Integer.parseInt(ins[i]));
                node.right = right;
                stack.offer(right);
            }
            i++;
        }
        return root;
    }

    static class Node{
        int val;
        Node left;
        Node right;
        Node(int val){
            this.val = val;
        }
    }
}
