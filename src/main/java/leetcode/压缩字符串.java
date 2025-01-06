package leetcode;

/**
 * 443
 * 思路：
 *      注意：
 *          只有一个的时候不需要计数
 *          最后一个字母需要记得记录
 *          注意只有一个字符的情况
 */
public class 压缩字符串 {
    public int compress(char[] chars) {
        String temps = "";
        int count=0;
        Character prev = null;
        Character last = null;
        for(char c : chars){
            if(prev == null){
                prev = c;
                last = c;
                count++;
                continue;
            }
            if(prev == c){
                count++;
            }else {
                temps += prev;
                if(count > 1)
                    temps += count;
                prev = c;
                count = 1;
            }
            last = c;
        }
        if(last != null){
            temps += last;
            if(count > 1)
                temps += count;
        }
        System.out.println(temps.toCharArray());
        return temps.toCharArray().length;
    }

    public static void main(String[] args) {
        压缩字符串 solution = new 压缩字符串();
        char[] chars = {'a'};

        int result = solution.compress(chars);
        System.out.println("Compressed length: " + result);
    }

}
