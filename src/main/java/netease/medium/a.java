package netease.medium;

import java.util.*;

class a {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] a = in.nextLine().split(",");
            String[] b = in.nextLine().split(",");
            int[] one = t(a);
            int[] two = t(b);
            int count = Integer.parseInt(in.nextLine());
            String max = "";
            //从第一个里面取n个，能够组成的最大数
            for(int i=0;i<=count && i<=one.length;i++){
                int ktwo = count - i;
                if(ktwo > two.length){
                    continue;
                }
                String maxOne = getMax(one, i);
                String maxTwo = getMax(two, count-i);
                // System.out.println("取" +i+"个， 第一个是：" +maxOne+"，第二个是：" + maxTwo);
                String ret = merge(maxOne, maxTwo);
                max = compare(ret, 0, max, 0) ? ret : max;
            }
            // System.out.println(max);
            String out = String.valueOf(max);
            for(int i=0;i<out.length();i++){
                if(i==out.length()-1){
                    System.out.print(out.charAt(i));
                }else {
                    System.out.print(out.charAt(i) + ",");
                }

            }
        }
    }

    public static boolean compare(String a, int ai, String b, int bi){
        if(a.length() > b.length()){
            return true;
        }
        if(a.length() < b.length()){
            return false;
        }
        if(ai >= a.length()){
            return false;
        }
        if(bi >= b.length()){
            return true;
        }
        if(a.charAt(ai) > b.charAt(bi)){
            return true;
        }
        if(a.charAt(ai) < b.charAt(bi)){
            return false;
        }
        if(a.charAt(ai) == b.charAt(bi)){
            return compareAGtB(a, ai+1, b, bi+1);
        }
        return true;
    }

    public static String merge(String a, String b){
        if(a.isEmpty()){
            return b;
        }
        if(b.isEmpty()){
            return a;
        }
        int aIndex = 0;
        int bIndex = 0;
        StringBuffer sb = new StringBuffer();
        while(true){
            if(aIndex >= a.length() || bIndex >= b.length()){
                break;
            }
            if(compareAGtB(a, aIndex, b, bIndex)){
                sb.append(a.charAt(aIndex));
                aIndex++;
            }else {
                sb.append(b.charAt(bIndex));
                bIndex++;
            }
        }
        //剩余的都贴进去
        while(aIndex<a.length()){
            sb.append(a.charAt(aIndex++));
        }
        while(bIndex<b.length()){
            sb.append(b.charAt(bIndex++));
        }
        return sb.toString();
    }

    public static boolean compareAGtB(String a, int ai, String b, int bi){
        if(ai >= a.length()){
            return false;
        }
        if(bi >= b.length()){
            return true;
        }
        if(a.charAt(ai) > b.charAt(bi)){
            return true;
        }
        if(a.charAt(ai) < b.charAt(bi)){
            return false;
        }
        if(a.charAt(ai) == b.charAt(bi)){
            return compareAGtB(a, ai+1, b, bi+1);
        }
        return true;
    }

    //获取k个的最大数
    public static String getMax(int[] in, int k){
        if(k==0){
            return "";
        }
        int length = in.length;
        int remain = in.length - k;
        LinkedList<Integer> stack = new LinkedList<>();
        for(int i : in){
            if(stack.isEmpty() && k>0){
                stack.push(i);
                k--;
            }else {
                while(true){
                    if(stack.isEmpty() || remain==0){
                        break;
                    }
                    if(stack.peek() < i){
                        stack.pop();
                        remain--;
                        k++;
                    }else {
                        break;
                    }
                }
                if(k>0){
                    stack.push(i);
                    k--;
                }
            }
        }
        if(stack.isEmpty()){
            return "";
        }

        StringBuffer sb = new StringBuffer();
        while(!stack.isEmpty()){
            sb.append(String.valueOf(stack.pop()));
        }
        return sb.reverse().toString();
    }

    public static int[] t(String[] in){
        int[] ret = new int[in.length];
        for(int i=0;i<in.length;i++){
            ret[i] = Integer.parseInt(in[i]);
        }
        return ret;
    }
}
